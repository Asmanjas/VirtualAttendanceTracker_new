package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class showAttendanceG extends AppCompatActivity {

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


    String email_x;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String abc = "";
    float attendance_percent;

    public String[] data,datax,x3;
    public int[] idata,idata2,idata1,data1,data2,x1,x2;
    int total_attendance_sum =0;
    int total_classes_sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_attendance_g);

        mainModuleG ak = new mainModuleG();

        tester = (TextView)findViewById(R.id.textView_fortesting);



        Intent intent2 = getIntent();
        //email_x = intent2.getStringExtra(mainModuleG.user_email_id);
        x1= intent2.getIntArrayExtra("array_one");
        x2 = intent2.getIntArrayExtra("array_two");
        x3 = intent2.getStringArrayExtra("array_three");

        String url = "http://10.50.33.206/VirtualAttendanceTracker/G/AccessStudentDetailsForAttendancePercentG.php?email=";

        studentDetailsForAttendancePercent(email_x, url + email_x);
        stubList = (ViewStub) findViewById(R.id.stublist_g);
        stubGrid = (ViewStub) findViewById(R.id.stubgrid_g);
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
    private void studentDetailsForAttendancePercent(final String email_x,final String url2) {

        class studentDetailsForAttendancePercent extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView2(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
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
        studentDetailsForAttendancePercent getJSON = new studentDetailsForAttendancePercent();
        getJSON.execute(email_x,url2);
    }
    private void loadIntoListView2(String json2) throws JSONException {
        JSONArray jsonArray = new JSONArray(json2);
        data1 = new int[jsonArray.length()];
        data2 = new int[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            data1[i] = obj.getInt("subject_attendace");
            data2[i]=obj.getInt("Total_classes");
        }
        for(int i=0;i<jsonArray.length();i++)
        {
            total_attendance_sum = total_attendance_sum + data1[i];
            total_classes_sum = total_classes_sum + data2[i];
        }

        attendance_percent = (float)((total_attendance_sum *100)/total_classes_sum);


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
            listViewAdapter=new ListViewAdapter(this, R.layout.listitem_g,productList);
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
        productList.add(new Product(R.drawable.ebook,x3[0],x1[0] ,"classes out of ",x2[0]));
        productList.add(new Product(R.drawable.ebook,x3[1],x1[1]," classes out of" ,x2[1]));
        productList.add(new Product(R.drawable.ebook,x3[2],x1[2]," classes out of ",x2[2]));
        productList.add(new Product(R.drawable.ebook,x3[3],x1[3]," classes out of", x2[3]));
        productList.add(new Product(R.drawable.ebook,x3[4],x1[4]," classes out of", x2[4]));
        productList.add(new Product(R.drawable.ebook,x3[5],x1[5]," classes out of ",x2[5]));



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
