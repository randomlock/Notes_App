package com.example.randomlocks.notesapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by randomlocks on 10/20/2015.
 */
public class DialogBackground extends DialogFragment implements View.OnClickListener {

    public interface DialogListener{
          public void colorPicker(String color);
    }

    ImageButton pink,orange,blue,lightgreen,purple;
    String color;

    DialogListener dialogListener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            dialogListener = (DialogListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString()+"must implement the dialog");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notelistlayout,null);

        //TextView text = new TextView(getActivity());
        //text.setText("Change Background");
        //text.setGravity(Gravity.CENTER);
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

                //AddNote.layout.setBackgroundColor(getResources().getColor(R.color.purple));
               Toast.makeText(v.getContext(),String.valueOf(color=String.valueOf(R.color.purple)),Toast.LENGTH_SHORT).show();

              //  AddNote.color="#ff8836ff";
                dismiss();
                break;


            case R.id.pink :

              //  AddNote.layout.setBackgroundColor(getResources().getColor(R.color.pink));
                color=String.valueOf(R.color.pink);
               // AddNote.color="#ffff71e3";
                dismiss();
                break;


            case R.id.orange :

              //  AddNote.layout.setBackgroundColor(getResources().getColor(R.color.orange));
                color=String.valueOf(R.color.orange);
              //  AddNote.color="#ffff6152";
                dismiss();
                break;


            case R.id.lightgreen :

            //    AddNote.layout.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                color=String.valueOf(R.color.lightgreen);
             //   AddNote.color="#d282ffa9";
                dismiss();
                break;


            case R.id.blue :

           //     AddNote.layout.setBackgroundColor(getResources().getColor(R.color.blue));
                color=String.valueOf(R.color.blue);
             //   AddNote.color="#d241a8ff";
                dismiss();
                break;

            default:
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();



        }
dialogListener.colorPicker(color);
    }


}
