package moe.lyniko.gemini.led;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import moe.lyniko.gemini.led.databinding.FragmentEditmeBinding;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.listeners.ColorListener;

import java.util.Arrays;

public class FirstFragment extends Fragment {

    private FragmentEditmeBinding binding;
    private Context mContext;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditmeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view1, Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);

        binding.buttonShowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopWindow(view1);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void setViewColors(View anchorView, int color){
        color = color|0xff000000;
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{color, color, color, color};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        Button b = anchorView.findViewById(R.id.button_showColor);
        b.setTextColor((~color)|0xff000000);
        b.setBackgroundTintList(colorStateList);
        FloatingActionButton flo = getActivity().findViewById(R.id.button_poweroff);
        flo.setBackgroundTintList(colorStateList);
        flo.setColorFilter((~color)|0xff000000);
    }
    private void initPopWindow(View anchorView) {
        mContext = anchorView.getContext();
        new ColorPickerDialog.Builder(mContext)
                .setTitle("选择你想要的颜色")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton("确定",
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                Toast.makeText(mContext, "新颜色：#"+envelope.getHexCode(), Toast.LENGTH_SHORT).show();
                                setViewColors(anchorView, envelope.getColor());
                                LedSetter.getInstance().setLightArgb(envelope.getArgb());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .attachAlphaSlideBar(false) // the default value is true.
                .attachBrightnessSlideBar(true)  // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show();
    }
}