package com.example.randomlocks.notesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.randomlocks.notesapp.fragments.AddNoteFragment;
import com.example.randomlocks.notesapp.fragments.ConfirmationDialog;
import com.example.randomlocks.notesapp.fragments.NoteGridFragment;
import com.example.randomlocks.notesapp.fragments.NoteListFragment;


public class MainActivity extends AppCompatActivity implements NoteListFragment.CommunicationInterface , AddNoteFragment.AddNoteInterface ,NoteGridFragment.CommunicationInterface {

    private static final String DEFAULT_TITLE = "All Notes" ;
    private static final String PREFERENCE_KEY = "sharedPreferenceKey" ;
    Toolbar toolbar;
   public static int flag = 0;
    public String mtitle;
    int mselectedId;
    public static final String KEY = "NAViGATION_SELECTED_ID"; //FOR SAVING MENU ITEM
    public static final String TITLE = "NAVIGATION_SELECTED_TITLE"; //FOR MENU TOOLBAR TITLE
    DrawerLayout mDrawer;
    NavigationView mNavigationView;
    ActionBarDrawerToggle mActionBarToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  mNavigationView = (NavigationView) findViewById(R.id.navigation_view);


        //   listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);



        if(savedInstanceState==null){
           int layout_id = getFromSharedPreferences(PREFERENCE_KEY,R.layout.fragment_note_list,this);



           Fragment fragment =  layout_id==R.layout.fragment_note_list ? new NoteListFragment() : new NoteGridFragment();
            String tag = layout_id==R.layout.fragment_note_list ? "NOTE_LIST" : "NOTE_GRID";
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout, fragment, tag)
                    .commit();
        }





    }





    public ActionBarDrawerToggle setupDrawerToggle() {
        return  new ActionBarDrawerToggle(this, mDrawer, R.string.drawer_open,  R.string.drawer_close);
    }




    @Override
    public void onClick() {

       flag=0;
        populateDB(null);


    }

//when editing note

    @Override
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {

      flag=1;
        populateDB(id);
    }

    @Override
    public void layoutChange(int layout_id) {


      //  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();



        if(layout_id==R.layout.fragment_note_list){

            replace(new NoteGridFragment(),"NOTE_GRID");

        }


        else
            replace(new NoteListFragment(),"NOTE_LIST");

    }

    private void replace(Fragment fragment,String tag) {


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout,fragment,tag)
                .commit();

    }




    public void populateDB(Long id){

        AddNoteFragment fragment = new AddNoteFragment();
        fragment.fillRowId(id);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.layout, fragment, "ADD_NOTE")
                .commit();

    }

    @Override  //save the notes and go to listview/gridview
    public void replace() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("ADD_NOTE");

        if(fragment!=null && flag==0){

            ConfirmationDialog dialogImageSelect = new ConfirmationDialog();
            dialogImageSelect.show(getSupportFragmentManager(),"dialog");

        }
        else {

            super.onBackPressed();
        }
    }





    public static void saveToSharedPreference(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveToSharedPreference(String key, int value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }




    public static String getFromSharedPreferences(String key,String defaultValue, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defaultValue);
    }

    public static int getFromSharedPreferences(String key,int defaultValue, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key,defaultValue);
    }





    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    protected void onPause() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("NOTE_LIST");
        if(fragment!=null)
            saveToSharedPreference(PREFERENCE_KEY,R.layout.fragment_note_list,this);
        else
            saveToSharedPreference(PREFERENCE_KEY,R.layout.fragment_note_grid,this);

        super.onPause();
    }
}
