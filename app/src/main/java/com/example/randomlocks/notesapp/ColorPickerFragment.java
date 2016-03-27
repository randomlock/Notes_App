package com.example.randomlocks.notesapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by randomlocks on 10/20/2015.
 */
public class ColorPickerFragment extends DialogFragment implements View.OnClickListener {

    public interface DialogListener{
          public void colorPicker(int color);
    }

    ImageButton pink,orange,blue,lightgreen,purple;
    int color;

    DialogListener dialogListener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            dialogListener = (DialogListener) getTargetFragment();
        } catch (Exception e) {
            throw new ClassCastException(activity.toString()+"must implement the dialog");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color_picker,null);


        getDialog().setTitle("Change Background");

        purple = (ImageButton) v.findViewById(R.id.purple);

        pink = (ImageButton) v.findViewById(R.id.pink);
        orange = (ImageButton) v.findViewById(R.id.orange);
        blue = (ImageButton) v.findViewById(R.id.blue);
        lightgreen = (ImageButton) v.findViewById(R.id.lightgreen);

        purple.setOnClickListener(this);
        pink.setOnClickListener(this);
        orange.setOnClickListener(this);
        blue.setOnClickListener(this);
        lightgreen.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.purple :


                color= R.color.purple;


                dismiss();
                break;


            case R.id.pink :


                color=R.color.pink;

                dismiss();
                break;


            case R.id.orange :


                color=(R.color.orange);

                dismiss();
                break;


            case R.id.lightgreen :


                color=(R.color.lightgreen);

                dismiss();
                break;


            case R.id.blue :


                color=(R.color.blue);

                dismiss();
                break;

            default:
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();



        }


        dialogListener.colorPicker(color);
    }




}
