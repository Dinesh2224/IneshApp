package com.ineshenergy.meterapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
Button Blogin;
TextView tsignup;
TextView tfrgpass;
TextView loginmeternumber;
TextView loginpassword;
    private ProgressDialog dialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        Blogin=findViewById(R.id.blogin);
tsignup=findViewById(R.id.tsignup);
tfrgpass=findViewById(R.id.tfrgpass);
loginmeternumber=findViewById(R.id.loginmeternumber);
loginpassword=findViewById(R.id.loginpassword);

        session = new SessionManager(this);

        if (session.isLoggedIn()) {

            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);

        }

Blogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String mobilenumber =loginmeternumber.getText().toString();
        String password = loginpassword.getText().toString();
        dialog.show();

        registerandotp(mobilenumber,password);


   }
});

tsignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this,SigninActivity.class));
    }
});

tfrgpass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, SigninActivity.class));
    }
});

    }
    private void registerandotp(final String mobilenumber, final String spass) {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlvalidation.user_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("exits");
                            if(error)
                            {


                                dialog.dismiss();
                                JSONObject users_detail=jsonObject.getJSONObject("users_detail");

                                String meter_num=users_detail.getString("meter_num");
                                String mobile_number=users_detail.getString("mobile_number");
                                session.setLogin(true);
                                session.setmobileNumber(mobile_number);
                                Intent newintent=new Intent(LoginActivity.this,HomePage.class);
                                newintent.putExtra("mobile_number",mobile_number);
                                newintent.putExtra("meter_num",meter_num);
                                startActivity(newintent);
                                finish();
                            }
                            else
                            {
                                dialog.dismiss();
                                String message =jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Check your internet Connection",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params= new HashMap<>();
                params.put("smobile_num",mobilenumber);
                params.put("spass",spass);




                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
