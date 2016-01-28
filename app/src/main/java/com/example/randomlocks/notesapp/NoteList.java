package com.example.randomlocks.notesapp;

/**
 * Created by randomlocks on 10/12/2015.
 */
public class NoteList {

   private int primary_key;
   private String Title;
     private  String Description;
  private   String CurrentDate;
    private  String color;
    private String filespath;

    public String getColor() {
        return color;
    }

    public NoteList(int key,String title, String description,String CurrentDate,String color,String filespath) {
        primary_key=key;
        Title = title;
        Description = description;
        this.CurrentDate=CurrentDate;
        this.color=color;
        this.filespath=filespath;


    }

    public String getFilespath() {
        return filespath;
    }



    public int getPrimary_key() {
        return primary_key;
    }

    @Override
    public String toString() {
        return Title;
    }

    public String getTime() {
        return CurrentDate;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }








}
