package com.example.randomlocks.notesapp;

/**
 * Created by randomlocks on 10/12/2015.
 */
public class NoteListModal {


   private String Title;
     private  String Description;
  private   long CurrentDate;
    private  int color;
    private String filespath;
    private long lastEdited;

    public NoteListModal(String title, String description, int color, String filespath) {
        Title = title;
        Description = description;
        this.color=color;
        this.filespath=filespath;



    }

    public String getFilespath() {
        return filespath;
    }

    public long getLastEdited() {
        return lastEdited;
    }

    public long getCurrentDate() {
        return CurrentDate;
    }

    public void setLastEdited(long lastEdited) {
        this.lastEdited = lastEdited;
    }

    public int getColor() {
        return color;
    }





    @Override
    public String toString() {
        return Title;
    }

    public long getTime() {
        return CurrentDate;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }








}
