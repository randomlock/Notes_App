package com.example.randomlocks.notesapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
        schema = new DatabaseScehma(context);

    }

    //Function To Insert Notes into Database;
    public long insertNote(String title ,String Description,int color,String path)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseScehma.TITLE,title);
        cv.put(DatabaseScehma.DESCRIPTION, Description);
        cv.put(DatabaseScehma.CURRENT_DATE,getDateTime());
        cv.put(DatabaseScehma.COLOR,color);
        cv.put(DatabaseScehma.CURRENT_DATE,System.currentTimeMillis());
        cv.put(DatabaseScehma.FILE_PATH,path);

        SQLiteDatabase db=schema.getWritableDatabase();
        long id=db.insert(DatabaseScehma.TABLE_NAME, null, cv);
        return id;
    }

    public int deleteNote(Long id)               //Deleting the notes
    {

        SQLiteDatabase db = schema.getWritableDatabase();

        int row = db.delete(DatabaseScehma.TABLE_NAME, DatabaseScehma.ID + "=" + id, null);



        return row;
    }



//Function to fetch each notes
public Cursor getAllNote()
{
   SQLiteDatabase db = schema.getWritableDatabase();
    String columns[] = {
      DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};
    Cursor cursor = db.query(DatabaseScehma.TABLE_NAME, columns, null, null, null, null, DatabaseScehma.CURRENT_DATE + " DESC");

        if(cursor!=null)
            cursor.moveToFirst();

    return cursor;

}
//Function to fetch a given note
    public Cursor getGivenNote(Long rowId)
    {
         SQLiteDatabase db = schema.getWritableDatabase();
        String columns[] = {
                DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};
        Cursor cursor= db.query(DatabaseScehma.TABLE_NAME,columns,DatabaseScehma.ID+"="+rowId,null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }

    public Cursor getAllNode(String sequence){

        SQLiteDatabase db = schema.getWritableDatabase();
        String columns[] = {
                DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};

        String where = DatabaseScehma.TITLE+" LIKE ? OR "+DatabaseScehma.DESCRIPTION+" LIKE ?";

        Cursor cursor = db.query(false,DatabaseScehma.TABLE_NAME,columns,where,new String[]{"%"+sequence+"%","%"+sequence+"%"},null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        return cursor;
    }


    //function to update a note

    public boolean updateNote(Long rowId,String title , String description,int color,String path)
    {
        SQLiteDatabase db = schema.getWritableDatabase();
        String columns[] = {
                DatabaseScehma.ID,DatabaseScehma.TITLE,DatabaseScehma.DESCRIPTION,DatabaseScehma.CURRENT_DATE,DatabaseScehma.COLOR,DatabaseScehma.FILE_PATH};
        ContentValues cv = new ContentValues();
        cv.put(DatabaseScehma.TITLE,title);
        cv.put(DatabaseScehma.DESCRIPTION, description);
        cv.put(DatabaseScehma.CURRENT_DATE,System.currentTimeMillis());
        cv.put(DatabaseScehma.COLOR, color);
        cv.put(DatabaseScehma.FILE_PATH,path);

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



    class DatabaseScehma extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Abdullah";
        private static final int DATABASE_VERSION=65;
        private static final String TABLE_NAME="Notes";
        private static final String ID = "_id";
        private  static final String TITLE = "title";
        private  static final String CURRENT_DATE="currentdate";
        private static final String FILE_PATH="path";
        private static final String COLOR="color";
        private  static final String DESCRIPTION = "description";
        private  static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ "( "+ID+" integer primary key autoincrement , "+TITLE+" varchar(50)   , "+DESCRIPTION+" varchar(255) , "+CURRENT_DATE+" int , "+COLOR+" int , "+FILE_PATH+" varchar(255) );";
        private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;





        public DatabaseScehma(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context,"IN oncreate ",Toast.LENGTH_LONG).show();


            } catch (SQLException e) {
                Toast.makeText(context,"IN on create ERROR",Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DELETE_TABLE);
                Toast.makeText(context,"IN on UPGRADE",Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(context,"IN on UPGRADE ERROR",Toast.LENGTH_LONG).show();;
            }
            onCreate(db);

        }
    }















}
