package com.example.trrevpostman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trrevpostman.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "Get Tag";
    private ActivityMainBinding binding;

    private  RecyclerView dList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<DataModal> doctorList;
    private RecyclerView.Adapter adapter;

//    JSONObject jsonObject = null;

    private String url = "http://199.192.26.248:8000/sap/opu/odata/sap/ZCDS_TEST_REGISTER_CDS/ZCDS_TEST_REGISTER";

    public MaterialButton addDoctorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dList = findViewById(R.id.doctorlistRV);

        doctorList = new ArrayList<>();
        adapter = new DoctorRVAdapter(getApplicationContext(), doctorList);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(dList.getContext(),linearLayoutManager.getOrientation());

        dList.setHasFixedSize(true);
        dList.setLayoutManager(linearLayoutManager);
        dList.addItemDecoration(dividerItemDecoration);
        dList.setAdapter(adapter);


        getData();

        addDoctorBtn = findViewById(R.id.addDoctorFAB);
        addDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivity(intent);
            }
        });



    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("response", "onResponse: "+response.toString());
//                try {
//                    JSONObject  jsonRootObject = new JSONObject(response.toString());
//                    JSONArray jsonArray = jsonRootObject.optJSONArray("results");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray array = jsonObject.getJSONArray("d");


                        for (int j = 0; j < array.length(); j++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject objects = array.getJSONObject(i);


                            DataModal dataModal = new DataModal();
                            dataModal.setId(objects.getString("doctors_id").toString());
                            dataModal.setName(objects.getString("name").toString());
                            dataModal.setEmail(objects.getString("email").toString());
                            dataModal.setGender(objects.getString("gender").toString());
                            dataModal.setMonth(objects.getString("practice_frm_month").toString());
                            dataModal.setYear((objects.getString("practice_frm_year")).toString());

                            doctorList.add(dataModal);
                        }

                } catch(JSONException e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept","application/json");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        requestQueue.add(jsonArrayRequest);
    }



}