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
import com.android.volley.toolbox.JsonObjectRequest;
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("d").getJSONArray("results");

                    for (int i  = 0 ; i < jsonArray.length() ; i++){


                        DataModal dataModal = new DataModal();
                        dataModal.setId(jsonArray.getJSONObject(i).getString("doctors_id"));
                        dataModal.setName(jsonArray.getJSONObject(i).getString("name"));
                        dataModal.setEmail(jsonArray.getJSONObject(i).getString("email"));
                        dataModal.setGender(jsonArray.getJSONObject(i).getString("gender"));
                        dataModal.setMonth(jsonArray.getJSONObject(i).getString("practice_frm_month"));
                        dataModal.setYear(jsonArray.getJSONObject(i).getString("practice_frm_year"));

                        Log.d("genderD", "onResponse: "+dataModal.getGender());
                        Log.d("genderD", "onResponse: "+dataModal.getName());
                        Log.d("genderD", "onResponse: "+dataModal.getEmail());

                        doctorList.add(dataModal);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        requestQueue.add(jsonObjectRequest);
    }



}