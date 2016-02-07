package com.example.randomlocks.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageViewer extends AppCompatActivity {

    String str;
    int position=0;
    ArrayList<String> list;
    Toolbar toolbar;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        toolbar = (Toolbar) findViewById(R.id.imagetoolbar);
        viewPager = (ViewPager) findViewById(R.id.imagepager);
        setSupportActionBar(toolbar);
        Intent it = getIntent();


        str= it.getStringExtra("path");
        list = it.getStringArrayListExtra("list");
        position = findPosition();
        viewPager.setAdapter(new ImagePagerAdapter(this,list));
        viewPager.setCurrentItem(position);




    }

    private int findPosition() {

        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(str)){
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






}


class ImagePagerAdapter extends PagerAdapter{

   private Context context;
   private ArrayList<String> list;
    ImageView imageView;
    PhotoViewAttacher mattacher;

    AddNote addNote=new AddNote();

    public ImagePagerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;

    }



    @Override
    public int getCount() {
        return list.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(FrameLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view =  inflater.inflate(R.layout.viewpager_image, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageShow);
        imageView.setImageBitmap(addNote.getThumbnail(context.getResources(),list.get(position),800,800));
        mattacher = new PhotoViewAttacher(imageView);
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }
}