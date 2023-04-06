package com.example.trrevpostman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trrevpostman.databinding.ActivityAddDataBinding;
import com.google.android.material.button.MaterialButton;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddDataBinding binding;

    private EditText nameEt, emailEt, monthEt, yearEt;
    private String gender;
    private MaterialButton maleMB, femaleMB, otherMB, updateMB;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        //initializing our views
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        monthEt = findViewById(R.id.monthsEt);
        yearEt = findViewById(R.id.yearEt);


        maleMB = findViewById(R.id.MaleBtn);
        femaleMB = findViewById(R.id.FemaleBtn);
        otherMB = findViewById(R.id.OthersBtn);
        updateMB = findViewById(R.id.UpdateBtn);
        loadingPB = findViewById(R.id.idLoadingPB);

        maleMB.setOnClickListener(this);
        femaleMB.setOnClickListener(this);
        otherMB.setOnClickListener(this);

        updateMB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.UpdateBtn:
                if(nameEt.getText().toString().isEmpty() ){
                    Toast.makeText(AddDataActivity.this, "Please enter name values", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(emailEt.getText().toString().isEmpty()){
                    Toast.makeText(AddDataActivity.this, "Please enter email values", Toast.LENGTH_SHORT).show();
                    return;
                } else if (monthEt.getText().toString().isEmpty()) {
                    Toast.makeText(AddDataActivity.this, "Please enter month values", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(yearEt.getText().toString().isEmpty()){
                    Toast.makeText(AddDataActivity.this, "Please enter year values", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(nameEt.getText().toString(), emailEt.getText().toString(), gender, monthEt.getText().toString(), yearEt.getText().toString());
                break;
            case R.id.MaleBtn:
                maleMB.setBackgroundColor(getColor(R.color.purple_200));
                otherMB.setBackgroundColor(getColor(R.color.yellow));
                femaleMB.setBackgroundColor(getColor(R.color.yellow));
                gender = "M";
                break;
            case R.id.FemaleBtn:
                femaleMB.setBackgroundColor(getColor(R.color.purple_200));
                maleMB.setBackgroundColor(getColor(R.color.yellow));
                otherMB.setBackgroundColor(getColor(R.color.yellow));
                gender = "F";
                break;
            case R.id.OthersBtn:
                otherMB.setBackgroundColor(getColor(R.color.purple_200));
                femaleMB.setBackgroundColor(getColor(R.color.yellow));
                maleMB.setBackgroundColor(getColor(R.color.yellow));
                gender = "O";
                break;
        }
    }

    private void postData(String nameEt, String emailEt, String gender, String monthEt, String yearEt) {
        String url = "http://199.192.26.248:8000/sap/opu/odata/sap/ZCDS_TEST_REGISTER_CDS/ZCDS_TEST_REGISTER";
        loadingPB.setVisibility(View.VISIBLE);

        Map<String, String> params = new HashMap<String, String>();
        Log.d("TAGname", "postData: "+nameEt);
        Log.d("TAGemail", "postData: "+emailEt);
        Log.d("TAGgender", "postData: "+gender);
        Log.d("TAGmonth", "postData: "+monthEt);
        Log.d("TAGyear", "postData: "+yearEt);
        params.put("name", nameEt);
        params.put("email", emailEt);
        params.put("gender", gender);
        params.put("practice_frm_month", monthEt);
        params.put("practice_frm_year", yearEt);

        RequestQueue queue = Volley.newRequestQueue(AddDataActivity.this);
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(AddDataActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onResponse: ");
                        //Resetting form
                        AddDataActivity.this.nameEt.setText("");
                        AddDataActivity.this.emailEt.setText("");
                        AddDataActivity.this.monthEt.setText("");
                        AddDataActivity.this.yearEt.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingPB.setVisibility(View.GONE);
                        error.printStackTrace();
                        Log.d("POST_TAG","a",error);
                        Toast.makeText(AddDataActivity.this, "Fail to get response : " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                headers.put("X-Requested-With","X");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(postRequest);
    }
}