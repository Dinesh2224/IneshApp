package com.ineshenergy.meterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {


EditText ETotp1,ETotp2,ETotp3,ETotp4;
Button Bsbresenotp;
String otptext1;
String otptext2;
    String otptext3;
    String otptext4;
    String otptext5;

private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        setContentView(R.layout.activity_otp);


ETotp1=findViewById(R.id.otp1);
ETotp2=findViewById(R.id.otp2);
ETotp3=findViewById(R.id.otp3);
ETotp4=findViewById(R.id.otp4);



Bsbresenotp=findViewById(R.id.sbotp);
        Intent intent = getIntent();
        final String smobile_number = intent.getStringExtra("mobile_number");
        final String otpfrom = intent.getStringExtra("user_otp");



        Bsbresenotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otptext1=ETotp1.getText().toString() +ETotp1;
                String otptext2=ETotp2.getText().toString() +ETotp2;
                String otptext3=ETotp3.getText().toString() +ETotp3;
                String otptext4=ETotp4.getText().toString() +ETotp4;
                String otptext5= otptext1 + otptext2 +otptext3 +otptext4;

                if (!otpfrom.equals(otptext5))
{
    dialog.show();
    registerandotp(smobile_number,otptext5);



}

else
{
    Toast.makeText(OtpActivity.this, "Please enter valid otp", Toast.LENGTH_SHORT).show();

}


            }
        });

    }
    private void registerandotp(final String smobile_number, final String otpnew) {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlvalidation.user_otp_verify,
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

                                String otp=users_detail.getString("meter_num");
                                String mobile_number=users_detail.getString("mobile_number");
                                Intent newintent=new Intent(OtpActivity.this,HomePage.class);
                                newintent.putExtra("mobile_number",mobile_number);
                                newintent.putExtra("user_otp",otp);
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
                params.put("smobile_num",smobile_number);
                params.put("otp",otpnew);




                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


}
