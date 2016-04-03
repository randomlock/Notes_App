package com.example.randomlocks.notesapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.randomlocks.notesapp.NoteListModal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by randomlocks on 10/10/2015.
 */
public class DatabaseAdapter {

Context context;
    DatabaseScehma schema;


    //Constructor
    public DatabaseAdapter(Context context) {
        this.context=context;
        schema = DatabaseScehma.getInstance(context);

    }

    //Function To Insert Notes into Database;
    public long insertNote(NoteListModal modal)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseScehma.TITLE,modal.getTitle());
        cv.put(DatabaseScehma.DESCRIPTION, modal.getDescription());
        cv.put(DatabaseScehma.COLOR,modal.getColor());
        cv.put(DatabaseScehma.FILE_PATH,modal.getFilespath());
        cv.put(DatabaseScehma.CURRENT_DATE,System.currentTimeMillis());
        cv.put(DatabaseScehma.LAST_EDITED,0);

        SQLiteDatabase db=schema.getWritableDatabase();
        return db.insert(DatabaseScehma.TABLE_NAME, null, cv);

    }

    public int deleteNote(Long id)               //Deleting the notes
    {

        SQLiteDatabase db = schema.getWritableDatabase();

        return db.delete(DatabaseScehma.TABLE_NAME, DatabaseScehma.ID + "=" + id, null);
    }



//Function to fetch each notes
public Cursor getAllNote(int sortBy)
{
   SQLiteDatabase db = schema.getWritableDatabase();

    String sortByColumn;
    String columns[] = {
      DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.LAST_EDITED,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};

    if(sortBy==0)
        sortByColumn = DatabaseScehma.TITLE+ " COLLATE NOCASE";
    else if(sortBy==1)
        sortByColumn = DatabaseScehma.CURRENT_DATE+ " DESC ";
    else
    sortByColumn = DatabaseScehma.LAST_EDITED+ " DESC ";


    Cursor cursor = db.query(DatabaseScehma.TABLE_NAME, columns, null, null, null, null,sortByColumn);

        if(cursor!=null)
            cursor.moveToFirst();

    return cursor;

}
//Function to fetch a given note
    public Cursor getGivenNote(Long rowId)
    {
         SQLiteDatabase db = schema.getWritableDatabase();
        String columns[] = {
                DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.LAST_EDITED,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};
        Cursor cursor= db.query(DatabaseScehma.TABLE_NAME,columns,DatabaseScehma.ID+"="+rowId,null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }

    public Cursor getAllNode(String sequence){

        SQLiteDatabase db = schema.getWritableDatabase();
        String columns[] = {
                DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.LAST_EDITED,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};

        String where = DatabaseScehma.TITLE+" LIKE ? OR "+DatabaseScehma.DESCRIPTION+" LIKE ?";

        Cursor cursor = db.query(false,DatabaseScehma.TABLE_NAME,columns,where,new String[]{"%"+sequence+"%","%"+sequence+"%"},null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        return cursor;
    }


    //function to update a note

    public boolean updateNote(Long rowId,NoteListModal modal)
    {
        SQLiteDatabase db = schema.getWritableDatabase();
        String columns[] = {
                DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.LAST_EDITED,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};
        ContentValues cv = new ContentValues();
        cv.put(DatabaseScehma.TITLE,modal.getTitle());
        cv.put(DatabaseScehma.DESCRIPTION, modal.getDescription());
        cv.put(DatabaseScehma.LAST_EDITED,System.currentTimeMillis());
        cv.put(DatabaseScehma.COLOR, modal.getLastEdited());
        cv.put(DatabaseScehma.FILE_PATH,modal.getFilespath());

        return db.update(DatabaseScehma.TABLE_NAME,cv,DatabaseScehma.ID+"="+rowId,null )>0;


    }






    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd HH:mm", Locale.getDefault());

        Date date = new Date();
        return dateFormat.format(date);
    }


    /*  public String getColor(int id)
    {
        SQLiteDatabase db = schema.getWritableDatabase();
        String column[] = {DatabaseScehma.COLOR};

       return db.query(DatabaseScehma.TABLE_NAME,column,DatabaseScehma.ID+"="+id,null,null,null,null).getString(4);

    } */



   static class DatabaseScehma extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Abdullah";
        private static final int DATABASE_VERSION=68;
        private static final String TABLE_NAME="Notes";
        private static final String ID = "_id";
        private  static final String TITLE = "title";
        private  static final String CURRENT_DATE="currentdate";
        public static final String LAST_EDITED = "editeddate";
        private static final String FILE_PATH="path";
        private static final String COLOR="color";
        private  static final String DESCRIPTION = "description";
        private  static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ "( "+ID+" integer primary key autoincrement , "+TITLE+" varchar(50)   , "+DESCRIPTION+" varchar(255) , "+CURRENT_DATE+" int , "+LAST_EDITED+" int , "+COLOR+" int , "+FILE_PATH+" varchar(255) );";
        private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        private static DatabaseScehma sInstance = null;




        public static synchronized DatabaseScehma getInstance(Context context) {
            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            // See this article for more information: http://bit.ly/6LRzfx
            if (sInstance == null) {
                sInstance = new DatabaseScehma(context.getApplicationContext());
            }
            return sInstance;
        }


        private DatabaseScehma(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            try {
                db.execSQL(CREATE_TABLE);



            } catch (SQLException e) {

            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DELETE_TABLE);

            } catch (SQLException e) {

            }
            onCreate(db);

        }
    }















}
