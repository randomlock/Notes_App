package com.example.randomlocks.notesapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.randomlocks.notesapp.fragments.AddNoteFragment;
import com.example.randomlocks.notesapp.fragments.ConfirmationDialog;
import com.example.randomlocks.notesapp.fragments.NoteGridFragment;
import com.example.randomlocks.notesapp.fragments.NoteListFragment;


public class MainActivity extends AppCompatActivity implements NoteListFragment.CommunicationInterface , AddNoteFragment.AddNoteInterface ,NoteGridFragment.CommunicationInterface {

    private static final String DEFAULT_TITLE = "All Notes" ;
    int layout = R.layout.fragment_note_list ;
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


        Fragment fragment =getSupportFragmentManager().findFragmentByTag("NOTE_LIST");

        if(fragment==null)
            fragment = getSupportFragmentManager().findFragmentByTag("NOTE_GRID");



if(fragment==null){
    NoteListFragment noteListFragment = new NoteListFragment();
    getSupportFragmentManager().beginTransaction()
            .add(R.id.layout, noteListFragment, "NOTE_LIST")
            .addToBackStack(null)
            .commit();
}

     /*   mActionBarToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(mActionBarToggle);

        mNavigationView.setNavigationItemSelectedListener(this);

        mselectedId = savedInstanceState == null ? R.id.all_Notes : savedInstanceState.getInt(KEY);
        mtitle = savedInstanceState == null ? DEFAULT_TITLE : savedInstanceState.getString(TITLE);
        selectDrawerItem(mselectedId, mtitle); */


    }





    public ActionBarDrawerToggle setupDrawerToggle() {
        return  new ActionBarDrawerToggle(this, mDrawer, R.string.drawer_open,  R.string.drawer_close);
    }



    /*  @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager manager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(new ComponentName(getApplicationContext(),MainActivity.class)));
        //  searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint("Search Notes");



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
    } */


    //when add note

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



      /*  if(layout_id==R.layout.fragment_note_list){
            layout = R.layout.fragment_note_grid;
            replace(new NoteGridFragment(),"NOTE_GRID");

        }


        else
            replace(new NoteListFragment(),"NOTE_LIST"); */

    }

    private void replace(Fragment fragment,String tag) {


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout,fragment,tag)
                .commit();

    }

/*    @Override
    public void onClick(View v) {


        Intent it = new Intent(this,AddNote.class);
        startActivity(it);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
dbadapter = new DatabaseAdapter(this);

       String Title = parent.getItemAtPosition(position).toString();

        Cursor cursor =dbadapter.getGivenNote(arrayList.get(position).getPrimary_key());
        cursor.moveToFirst();
        int ID = cursor.getInt(0);  //returns id
        String Description = cursor.getString(2);
        String color = cursor.getString(4);

        //Toast.makeText(this,Title,Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,cursor.getString(2),Toast.LENGTH_SHORT).show();
   //     AddNote.Title.setText(Title);
     //   AddNote.Description.setText(Description);
        Intent it = new Intent(this,AddNote.class);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",Title);
        bundle.putString("DESCRIPTION", Description);
        bundle.putInt("ID", ID);
        bundle.putString("COLOR", color);
        bundle.putString("FILE_PATH",cursor.getString(5));
        it.putExtras(bundle);
        startActivity(it);


    } */


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
        Fragment fragment ;

        if(layout == R.layout.fragment_note_list){
            fragment = new NoteListFragment();

        }
        else
        fragment = new NoteListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout,fragment)
                .commit();
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


    private void selectDrawerItem(int mselectedId, String title) {

        switch (mselectedId){

            case R.id.all_Notes :
                FragmentTransactionHelper("replace",new NoteListFragment(),"GamesHome");
                break;

            case R.id.starred_notes :
                Toast.makeText(this,"starred notes",Toast.LENGTH_SHORT).show();
                FragmentTransactionHelper("replace",new NoteListFragment(),"GamesList");
                break;

            case R.id.Trash :
                Toast.makeText(this,"trash notes",Toast.LENGTH_SHORT).show();
                FragmentTransactionHelper("replace", new NoteListFragment(), "GamesNews");
                break;

            case R.id.private_notes :
                Toast.makeText(this,"Private Notes",Toast.LENGTH_SHORT).show();
                FragmentTransactionHelper("replace", new NoteListFragment(), "GamesNews");
                break;



            default:
                Toast.makeText(this,"default",Toast.LENGTH_SHORT).show();

        }

        setTitle(title);
        mDrawer.closeDrawer(GravityCompat.START);



    }



    private void FragmentTransactionHelper(String operation,Fragment fragment,String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if(operation.equals("replace")){
            fragmentManager.popBackStack();
            ft.add(R.id.layout, fragment, tag);
            ft.addToBackStack(null);

        }

        else if(operation.equals("add")){
            ft.add(R.id.layout,fragment,tag);
            ft.addToBackStack(null);

        }

        else if(operation.equals("remove")){

            //TODO


        }

        ft.commit();

    }








    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY, mselectedId);
        outState.putString(TITLE, mtitle);
    }








}
