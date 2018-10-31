package com.ineshenergy.meterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SigninActivity extends AppCompatActivity {
TextView TVsignin;
Button Bsbsignup;
EditText ETmobile_num,ETmeternum,ETpass,ETrepass;
String Smobile_num,Smeternum,Spass,Srepass;
    private ProgressDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        session = new SessionManager(this);
TVsignin=findViewById(R.id.tsignin);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        Bsbsignup=findViewById(R.id.sbsignup);

        ETmobile_num=findViewById(R.id.mobilenumber);
                ETmeternum=findViewById(R.id.meternumber);
                ETpass=findViewById(R.id.password);
                ETrepass=findViewById(R.id.repassword);


      TVsignin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(SigninActivity.this,LoginActivity.class));
              finish();
          }
      });

      Bsbsignup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Smobile_num=ETmobile_num.getText().toString();
              Smeternum=ETmeternum.getText().toString();
              Spass=ETpass.getText().toString();
              Srepass=ETrepass.getText().toString();
              if (!Spass.equals(Srepass))
              {
                  Toast.makeText(SigninActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
              }
              else
              {
                  dialog.show();

                  registerandotp(Smobile_num,Smeternum,Spass);

              }
//              startActivity(new Intent(SigninActivity.this,OtpActivity.class));
//              finish();
          }
      });
    }

    private void registerandotp(final String smobile_num, final String smeternum, final String spass) {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlvalidation.user_signin,
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
                                String otp=users_detail.getString("user_otp");
                                String mobile_number=users_detail.getString("mobile_number");
                                session.setLogin(true);
                                session.setmobileNumber(mobile_number);
                                Intent newintent=new Intent(SigninActivity.this,OtpActivity.class);
                                newintent.putExtra("mobile_number",mobile_number);
                                newintent.putExtra("user_otp",otp);
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
                params.put("smobile_num",smobile_num);
                params.put("smeternum",smeternum);
                params.put("spass",spass);



                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
