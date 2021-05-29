package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList  namelist;
    ListView listview;
    Button btngetall,btndelete;
    EditText edID;
    String url = "https://60b0f6391f26610017fff8f4.mockapi.io/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listView);
        namelist = new ArrayList();
        btngetall = findViewById(R.id.btnGetAll);
        btndelete = findViewById(R.id.btndelete);
        edID = findViewById(R.id.edID);

        String id = edID.getText().toString();


        String getAll = url + "person";
        String delete = url + "person/" +id;
        btngetall.setOnClickListener(view->{
            GetData(getAll);
        });

        btndelete.setOnClickListener(view->{
            DeleteApi(delete);
            GetData(getAll);
        });



    }
    private void GetData(String url){
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
                try {
                    for (int i =0;i<response.length();i++){
                        JSONObject obj = (JSONObject)response.get(i);
                        namelist.add(obj.getString("LastName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listview.setAdapter(new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,namelist));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error make by API server!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void DeleteApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + 37, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}