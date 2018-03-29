package com.aptech.asmanjas.virtualattendancetracker;

/**
 * Created by Asmanjas on 18-03-2018.
 */
import java.util.Calendar;

public class MainAlgorithm   {

    String email_time_table,day_time_table,subject_time_table,time_start_time_table,time_end_time_table;
    String email_student_details,first_name_student_details,last_name_student_details,password_student_details,subject_student_details;
    int attendance_student_details,total_classes_student_details;
    String email_GPS;
    float longitude_gps,lattitude_gps;


    Calendar currentTime = Calendar.getInstance();
int day_of_week = currentTime.get(Calendar.DAY_OF_WEEK);
int current_hour = currentTime.get(Calendar.HOUR_OF_DAY);
int current_minute = currentTime.get(Calendar.MINUTE);


int start_time = Integer.parseInt(time_start_time_table);//code is not converted into hour and minute

//int

}
