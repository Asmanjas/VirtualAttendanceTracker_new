package com.aptech.asmanjas.virtualattendancetracker;

/**
 * Created by m on 18-Mar-18.
 */

public class Product
{
    private int imageid;
    private String title;
    private int attendance;
    private int total_c;
    private String description;

    public Product(int imageid, String title, int attendance,String description,int total_c) {
        this.imageid = imageid;
        this.title = title;
        this.attendance = attendance;
        this.total_c = total_c;
        this.description = description;

    }
    public String getDescription(){
        return description;}
    public void setDescription(String description){
        this.description = description;}

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getTotal_c() {
        return total_c;
    }

    public void setTotal_c(int total_c) {
        this.total_c = total_c;
    }



}
