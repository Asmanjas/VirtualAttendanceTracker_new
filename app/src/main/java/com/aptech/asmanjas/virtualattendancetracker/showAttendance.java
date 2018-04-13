package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class showAttendance extends AppCompatActivity {

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private Gridviewadapter gridviewadapter;
    private List<Product> productList;
    private int currentViewMode=0;
    TextView tester;

    static final int VIEW_MODE_LISTVIEW=0;
    static final int VIEW_MODE_GRIDVIEW=1;

    //TextView student_name,roll_number,theory_batch,lab_batch,attendance_percent_tx;
    String roll_number_x;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String abc = "  ";
    float attendance_percent;

    public String[] data,datax;
    public int[] idata,idata2,idata1,data1,data2,x1,x2;
    //int[] data1 = new int[6];
   // int[] data2 = new int[6];
    int total_attendance_sum =0;
    int total_classes_sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

Module_student ak = new Module_student();
//data1 = ak.getdata1(data1[0]);
//data2 = ak.getdata2(data2);
       // int x = ak.getdata1();

/*int sum = 0;
for(int i = 0 ;i<2;i++)
{
    sum = sum + data1[i];
}*/
        //my code
        tester = (TextView)findViewById(R.id.textView_fortesting);

//SharedPreferences settings = getSharedPreferences(login_student.user_roll_number, Context.MODE_PRIVATE);
       // String name = settings.getString("roll_number",roll_number_x);

       Intent intent2 = getIntent();
       roll_number_x = intent2.getStringExtra(Module_student.student_roll_number);
       x1= intent2.getIntArrayExtra("array1");
       x2 = intent2.getIntArrayExtra("array2");
       int sum=0;
       for(int i=0;i<2;i++)
       {
           sum = sum + x1[i];
       }

        //tester.setText(String.valueOf(x1[0]));
        //roll_number_x = String.valueOf(10);

        String url2 = "http://10.50.33.206/VirtualAttendanceTracker/AccessStudentDetailsForAttendancePercent.php?Roll_Number=";

        //downloadJSON(roll_number_holder, url + roll_number_x);
        downloadJSON(roll_number_x, url2 + roll_number_x);


        stubList = (ViewStub) findViewById(R.id.stublist);
        stubGrid = (ViewStub) findViewById(R.id.stubgrid);

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.listView);
        gridView = (GridView) findViewById(R.id.gridview);


        getProductList();
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }
    private void downloadJSON(final String roll_number_x,final String url2) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView2(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("roll_number",params[0]);
                    abc =httpParse.postRequest(hashMap, url2);
                    URL url = new URL(url2);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        //boolean x = json.toLowerCase().contains(email.toLowerCase());
                        //if(x) {
                        sb.append(json + "\n");
                        //}
                    }
                    return abc;
                    //return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute(roll_number_x,url2);
    }
    private void loadIntoListView2(String json2) throws JSONException {
        JSONArray jsonArray = new JSONArray(json2);
        data1 = new int[jsonArray.length()];
        data2 = new int[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            data1[i] = obj.getInt("subject_attendace");
            data2[i]=obj.getInt("Total_classes");
            // + " " + obj.getInt("attendance") + " " + obj.getInt("total_attendance"); //+ " "+ (float)obj.getInt("attendance")/obj.getInt("total_attendance");
            //int x =  obj.getInt("attendance")/obj.getInt("total_attendance");
            //float p = Float.parseFloat(x);
            //stocks[i] = " " + p;
        }
        for(int i=0;i<jsonArray.length();i++)
        {
            total_attendance_sum = total_attendance_sum + data1[i];
            total_classes_sum = total_classes_sum + data2[i];
        }

        attendance_percent = (float)((total_attendance_sum *100)/total_classes_sum);
       // attendance_percent_tx.setText(String.valueOf(attendance_percent + " %"));


            /*String lb_batch = obj.getString("Lab_Batch");


            String th_batch = obj.getString("Theory_Batch");
            String name = obj.getString("First_Name") + " " + obj.getString("Last_Name");
            student_name.setText(name);
            theory_batch.setText(th_batch);
            lab_batch.setText(lb_batch);*/



      /*  for(int i=0;i<jsonArray.length();i++)
        {
            idata1[i] = Integer.parseInt(data1[i]);
            idata2[i] = Integer.parseInt(data2[i]);
        }
        for(int i=0;i<jsonArray.length();i++){

            total_attendance_sum = total_attendance_sum + idata1[i];
            total_classes_sum = total_classes_sum + idata2[i];
        }
        attendance_percent = (float)((total_attendance_sum/total_classes_sum)*100);*/
        //attendance_percent_tx.setText(String.valueOf(attendance_percent) + "  asmanjas Kalundia");
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        //listView.setAdapter(arrayAdapter);
        // tx.setText(stocks[1]);


   }

    private void switchView() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);

        } else {
            stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }


    private void setAdapters() {
        if(VIEW_MODE_LISTVIEW==currentViewMode){
            listViewAdapter=new ListViewAdapter(this, R.layout.listitem,productList);
            listView.setAdapter(listViewAdapter);
        }
        else
        {
            gridviewadapter=new Gridviewadapter(this, R.layout.gridlist,productList);
            gridView.setAdapter(gridviewadapter);
        }
    }



    public List<Product> getProductList() {
        productList=new ArrayList<>();
        productList.add(new Product(R.drawable.dsa,"Data Structure",x1[0] ,"classes out of ",x2[0]));
        productList.add(new Product(R.drawable.dbms,"Database Management System",x1[1]," classes out of" ,x2[1]));
        productList.add(new Product(R.drawable.maths,"Numerical Mathematics",x1[2]," classes out of ",x2[2]));
        productList.add(new Product(R.drawable.me,"Manegarial Economics",x1[3]," classes out of", x2[3]));
        productList.add(new Product(R.drawable.lab,"DSA Lab",x1[4]," classes out of", x2[4]));
        productList.add(new Product(R.drawable.dbmslab,"DBMS Lab",x1[5]," classes out of ",x2[5]));



        return productList;
    }


    AdapterView.OnItemClickListener onItemClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(getApplicationContext(),productList.get(position).getTitle()+"-"+ productList.get(position).getDescription(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itemmenu1:
                if(VIEW_MODE_LISTVIEW==currentViewMode)
                {
                    currentViewMode=VIEW_MODE_GRIDVIEW;
                }
                else
                {
                    currentViewMode=VIEW_MODE_LISTVIEW;
                }

                switchView();
                SharedPreferences sharedPreferences=getSharedPreferences("ViewMode",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("currentViewMode",currentViewMode);
                editor.commit();
                break;
        }

        return true;
    }


}
