package com.example.randomlocks.notesapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener , ListView.OnItemClickListener  {
FloatingActionButton ADD_NOTE;
   ArrayList<NoteList>  arrayList;
    DatabaseAdapter dbadapter;
    Cursor cursor;

    NoteAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
     //   setSupportActionBar(myToolbar);
    listView= (ListView) findViewById(R.id.listView);
ADD_NOTE=(FloatingActionButton)findViewById(R.id.button);
        ADD_NOTE.setOnClickListener(this);

arrayList = new ArrayList<>();
       dbadapter = new DatabaseAdapter(this);
        cursor=dbadapter.getAllNote();

        while (cursor.moveToNext())
        {
            arrayList.add(new NoteList(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));

        }

adapter = new NoteAdapter(this,arrayList);



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");

                // Calls toggleSelection method from ListViewAdapter Class
                adapter.toggleSelection(position);

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {


                mode.getMenuInflater().inflate(R.menu.menubardelete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        SparseBooleanArray selected = adapter.getSelectedIds();
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                NoteList selecteditem = (NoteList) adapter.getItem(selected.keyAt(i));

                                // Remove selected items following the ids
                                adapter.remove(selecteditem);
                                int id=selected.keyAt(i);
                                dbadapter.DeleteNote(selecteditem.getPrimary_key());

                            }
                        }

                        mode.finish();
                        return true;

                    case  R.id.selectAll :
                        for ( int i=0; i < adapter.getCount(); i++) {
                            listView.setItemChecked(i, true);
                        }
                        return true;



                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
             adapter.removeSelection();
            }
        });








    }

    @Override
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
    }

    @Override
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


    }



}
