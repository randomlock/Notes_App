package com.example.randomlocks.notesapp;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.randomlocks.notesapp.adapter.DatabaseAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by randomlocks on 10/12/2015.
 */
public class NoteAdapter extends ArrayAdapter {

    TextView Title,Description,Date_NOW;
Context context;
    String date1,date2,datesplit[];
    Date date1obj,date2obj;


    ArrayList<NoteListModal> arrayList;
    private SparseBooleanArray mSelectedItemsIds;
DatabaseAdapter db;

    public NoteAdapter(Context context,ArrayList<NoteListModal> arrayList) {
        super(context, R.layout.custom_layout_list,arrayList);
        this.context=context;
        mSelectedItemsIds = new SparseBooleanArray();
       // Toast.makeText(context,"in constructor",Toast.LENGTH_LONG).show();
this.arrayList=arrayList;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Toast.makeText(context,"in getView",Toast.LENGTH_LONG).show();
        View v = convertView;
        if(v==null)
        {
            LayoutInflater l=LayoutInflater.from(getContext());
            v = l.inflate(R.layout.custom_layout_list, parent, false);
        }

NoteListModal list = arrayList.get(position);
db=new DatabaseAdapter(context);
        Title= (TextView) v.findViewById(R.id.listviewTitle);
        Title.setText(list.getTitle());
        Date_NOW=(TextView)v.findViewById(R.id.listviewDate);
       date1=list.getTime();
        date2 = db.getDateTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.ENGLISH);

        try {
            date1obj = sdf.parse(date1);
            date2obj = sdf.parse(date2);
        } catch (ParseException e) {
            Toast.makeText(context,"DATE ERROR IN PARSING",Toast.LENGTH_SHORT).show();
        }

        int diffInDays = (int)( (date1obj.getTime() - date2obj.getTime())
                / (1000 * 60 * 60 * 24) );
        Log.d("time_check", String.valueOf(diffInDays));
        datesplit=date1.split(" ");
        if(diffInDays<0)
        Date_NOW.setText(datesplit[0]);
        else
        Date_NOW.setText(datesplit[1]);


        return  v;
    }




    public void remove(NoteListModal object) {
        arrayList.remove(object);
        notifyDataSetChanged();
    }

    public List<NoteListModal> getMyList()
    {
        return arrayList;
    }


    public void  toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    // Remove selection after unchecked
    public void  removeSelection() {
        mSelectedItemsIds = new  SparseBooleanArray();
        notifyDataSetChanged();
    }

    // Item checked on selection
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position,  value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }



    public  SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }


}
