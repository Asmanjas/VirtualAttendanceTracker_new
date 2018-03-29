package com.aptech.asmanjas.virtualattendancetracker;

import java.util.Calendar;


/**
 * Created by Asmanjas on 19-03-2018.
 */

public class time_table_G {


    String email,subject;
    Calendar rightnow = Calendar.getInstance();
    int dayOfWeek = rightnow.get(Calendar.DAY_OF_WEEK);
    int day;
String time_start,time_end;
   /*  x1,x2,x3,x4,x5,x6,x7;
    boolean x1 = day.equals("sunday");
    boolean x2 = day.equals("monday");
    boolean x3 = day.equals("tuesday");
    boolean x4 = day.equals("wednesday");*/






    public time_table_G(String email,int dayOfWeek,String subject,String time_start,String time_end) {
        this.email =email;
        this.dayOfWeek = dayOfWeek;
        this.subject = subject;
        this.time_start = time_start;
        this.time_end = time_end;
    }
    public String getEmail(){

        return email;

    }
    public void setEmail(){
        this.email = email;
    }

    public int getDayOfWeek(){
        return dayOfWeek;

    }
    public void SetDayofWeek(){
        this.dayOfWeek= dayOfWeek;
    }

    public String getSubject(){
        return subject;
    }
    public void setSubject(){
        this.dayOfWeek = dayOfWeek;

    }

  //  public String
}
