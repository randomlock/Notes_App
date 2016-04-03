package com.example.randomlocks.notesapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.randomlocks.notesapp.R;

/**
 * Created by randomlocks on 10/20/2015.
 *
 * Remove all extra 9 variable and minimize the code
 *
 */
public class ColorPickerFragment extends DialogFragment implements View.OnClickListener {

    private static final String COLOR_KEY = "colorKey";
    FloatingActionButton color1,color2,color3,color4,color5,color6,color7,color8,color9,store_previous=null;
    int color,prev_id,color_id;



    public interface DialogListener{
          public void colorPicker(int color);

    }





    public static final ColorPickerFragment newInstance(int color_id) {

        Bundle args = new Bundle();
        args.putInt(COLOR_KEY,color_id);
        ColorPickerFragment fragment = new ColorPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }






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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         color_id = getArguments().getInt(COLOR_KEY, R.color.color1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color_picker, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));








        color1 = (FloatingActionButton) v.findViewById(R.id.color1); //white defaukt


        color2 = (FloatingActionButton) v.findViewById(R.id.color2);
        color3 = (FloatingActionButton) v.findViewById(R.id.color3);
        color4 = (FloatingActionButton) v.findViewById(R.id.color4);
        color5 = (FloatingActionButton) v.findViewById(R.id.color5);
        color6 = (FloatingActionButton) v.findViewById(R.id.color6);
        color7 = (FloatingActionButton) v.findViewById(R.id.color7);
        color8 = (FloatingActionButton) v.findViewById(R.id.color8);
        color9 = (FloatingActionButton) v.findViewById(R.id.color9);
      prev_id = findPreviousId(color_id);
        store_previous = (FloatingActionButton)v.findViewById(prev_id);
        store_previous.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_done_black_24dp));



        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);
        color5.setOnClickListener(this);
        color6.setOnClickListener(this);
        color7.setOnClickListener(this);
        color8.setOnClickListener(this);
        color9.setOnClickListener(this);


        return v;
    }

    private int findPreviousId(int color_id) {

        if(color_id==R.color.color1)
            return R.id.color1;

       else if(color_id==R.color.color2)
            return R.id.color2;

        else if(color_id==R.color.color3)
            return R.id.color3;

        else if(color_id==R.color.color4)
            return R.id.color4;

        else if(color_id==R.color.color5)
            return R.id.color5;

        else if(color_id==R.color.color6)
            return R.id.color6;

        else if(color_id==R.color.color7)
            return R.id.color7;

        else if(color_id==R.color.color8)
            return R.id.color8;

        else
            return R.id.color9;



    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.color1 :


                color= R.color.color1;



                break;


            case R.id.color2 :


                color=R.color.color2;


                break;


            case R.id.color3 :


                color=(R.color.color3);


                break;





            case R.id.color4 :


                color=(R.color.color4);


                break;


            case R.id.color5 :

                color = R.color.color5;




                break;

            case R.id.color6 :

                color = R.color.color6;

                break;

            case R.id.color7 :

                color = R.color.color7;

                break;

            case R.id.color8 :

                color = R.color.color8;

                break;

            case R.id.color9 :

                color = R.color.color9;

                break;


            default:
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();



        }


        dismiss();  //dismiss the color fragment
        dialogListener.colorPicker(color);
    }




}
