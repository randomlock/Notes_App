package com.example.randomlocks.notesapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.randomlocks.notesapp.PictureHelper.ImageViewer;
import com.example.randomlocks.notesapp.adapter.DatabaseAdapter;
import com.example.randomlocks.notesapp.fragments.ColorPickerFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddNote extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
    private static final int CAMERA_REQUEST = 1888;
    private static final String COLOR_KEY = "color_key";
    TextInputLayout edit_title,edit_description;
    EditText Title, Description;
    DatabaseAdapter adapter;
    JSONObject json;
    FloatingActionButton submit;
ArrayList<String> image_paths;
    String color;
    private File imagefile;
    RelativeLayout layout;
    LinearLayout linearLayout;
    Long id;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        image_paths = new ArrayList<>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Title = (EditText) findViewById(R.id.title);
        Description = (EditText) findViewById(R.id.descritpion);
        submit = (FloatingActionButton) findViewById(R.id.save);
        submit.show();
        edit_title = (TextInputLayout) findViewById(R.id.input_title);
        edit_description = (TextInputLayout) findViewById(R.id.input_description);
        linearLayout = (LinearLayout) findViewById(R.id.layout);
        submit.setOnClickListener(this);

        layout = (RelativeLayout) findViewById(R.id.mylayout);
        color = String.valueOf(R.color.white);

        try {
            it = getIntent();
            String title = it.getExtras().getString("TITLE");
            String description = it.getExtras().getString("DESCRIPTION");


            json = new JSONObject(it.getExtras().getString("FILE_PATH"));
            JSONArray jsonArray = json.optJSONArray("FILE_PATH");

            if(jsonArray!=null){

                for(int i=0;i<jsonArray.length();i++){
                    image_paths.add(jsonArray.getString(i));

                }

            }






            layout.setFocusableInTouchMode(true);
            Title.setText(title);
            Description.setText(description);
            color = it.getExtras().getString("COLOR");
            layout.setBackgroundColor(getResources().getColor(Integer.parseInt(color)));



            for (int i = 0; i < image_paths.size(); i++) {


                ImageView imageView = new ImageView(this);
                CardView cardView = new CardView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                cardView.setLayoutParams(params);
                cardView.setCardElevation(8);
                cardView.setRadius(8);
                params.leftMargin = 10;
                imageView.setLayoutParams(params);
                imageView.setTag(image_paths.get(i));
                imageView.setImageBitmap(getThumbnail(getResources(), image_paths.get(i), 100, 100));
                linearLayout.addView(cardView);
                cardView.addView(imageView);
                imageView.setOnClickListener(new ImplementListenerClass());


            }


        } catch (Exception e) {
            Toast.makeText(this, "error1", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.background:

                ColorPickerFragment dialog = new ColorPickerFragment();
                dialog.show(getSupportFragmentManager(), "mydialog");


                return true;

            case R.id.reminder:
                final Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

                dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddNote.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                        timePickerDialog.show(getFragmentManager(), "Timepickerdialog");
                    }
                });
                return true;

            case R.id.camera:

                takePicture();


                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }



    @Override
    public void onClick(View v) {

        long a;
        boolean flag = true;
        boolean b;
        Cursor cursor = null;
        adapter = new DatabaseAdapter(this);

     /*   if(Title.getText().toString().trim().length()==0)
        {
            Title.setText("<UNTITLED>");
            Title.setTypeface(null, Typeface.BOLD_ITALIC);
            Title.setBackgroundColor(Color.BLACK);

        }  */
        if (Description.getText().toString().trim().length() == 0) {
            Description.setText(" ");
        }


        try {
           it = getIntent();
            id = it.getExtras().getLong("ID");

            cursor = adapter.getGivenNote(id);
            cursor.moveToFirst();


            if (cursor.getCount() > 0) {
                Toast.makeText(this, String.valueOf(id), Toast.LENGTH_LONG).show();




                if (Title.getText().toString().trim().length() == 0)
                    adapter.deleteNote(id);
                else {
                    json = new JSONObject();
                    json.put("FILE_PATH",new JSONArray(image_paths));
                //    b = adapter.updateNote(id, Title.getText().toString(), Description.getText().toString(), Integer.parseInt(color), json.toString());    //UPDATING NOTE
                    flag = false;

                    //     color="#FFFFFF";
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "error2", Toast.LENGTH_LONG).show();
        }

        //insert the column
        if (flag) {
            Toast.makeText(this, "Not coming here right for update?", Toast.LENGTH_LONG).show();
          json = new JSONObject();
            try {
                json.put("FILE_PATH",new JSONArray(image_paths));

            } catch (JSONException e) {
                Toast.makeText(this,"no jason",Toast.LENGTH_SHORT).show();
            }

            if (Title.getText().toString().trim().length() != 0)
     //           a = adapter.insertNote(Title.getText().toString(), Description.getText().toString(), Integer.parseInt(color), json.toString());        //INSERTING NOTE

            Toast.makeText(this,json.toString(),Toast.LENGTH_SHORT).show();

            //   color="#FFFFFF";
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);


        }


    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1, int i2) {

    }


    public Bitmap getThumbnail(Resources resources, String filepath, int req_width, int req_height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        options.inSampleSize = CalculateSampleSize(options, req_width, req_height);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);

    }

    public int CalculateSampleSize(BitmapFactory.Options options, int req_width, int req_height) {

        int width = options.outWidth;
        int height = options.outHeight;
        int samplingfactor = 1;

        if (width > req_width && height > req_height) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfWidth / samplingfactor) > req_width && (halfHeight / samplingfactor) > req_height) {
                samplingfactor *= 2;
            }


        }
        return samplingfactor;
    }


    public void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String time = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
        String imagename = "notes" + time;
        imagefile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imagename + ".jpg");


        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagefile));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        }

    } //function


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {

            switch (resultCode) {

                case RESULT_OK:

                    if (imagefile.exists()) {
                        image_paths.add(imagefile.getAbsolutePath());

                        ImageView imageView = new ImageView(this);
                        CardView cardView = new CardView(this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        cardView.setLayoutParams(params);
                        cardView.setCardElevation(8);
                        cardView.setRadius(8);
                        params.leftMargin = 10;
                        imageView.setLayoutParams(params);
                        imageView.setTag(imagefile.getAbsolutePath());
                        imageView.setImageBitmap(getThumbnail(getResources(), imagefile.getAbsolutePath(), 100, 100));
                        linearLayout.addView(cardView);
                        cardView.addView(imageView);


                        imageView.setOnClickListener(new ImplementListenerClass());


                    } else {
                        Toast.makeText(this, "Cannot be saved", Toast.LENGTH_SHORT).show();

                    }

                    break;

                case RESULT_CANCELED:
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;

            }
        }
    }

 /*   @Override
    public void colorPicker(String color) {
        layout.setBackgroundColor(getResources().getColor(Integer.parseInt(color)));
        this.color=color;
    } */

    private class ImplementListenerClass implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String path = (String) v.getTag();
            Intent it = new Intent(AddNote.this,ImageViewer.class);
            it.putExtra("path", path);
            it.putStringArrayListExtra("list",image_paths);
            startActivity(it);

            Toast.makeText(AddNote.this, path, Toast.LENGTH_SHORT).show();
        }
    }
}


