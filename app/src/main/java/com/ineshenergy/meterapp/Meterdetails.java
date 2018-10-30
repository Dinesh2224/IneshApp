package com.ineshenergy.meterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Meterdetails extends AppCompatActivity {
String meternoin;

EditText TVsysrtc,TVmetrtc,TVmetrv,TVmetyv,TVmetbv,TVmetkw,TVmetkwh,TVmeterno,TVmetrc,TVmetyc,TVmetbc,TVmetkvah,TVmetinstant,TVmetfreq,TVmetmdkw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_instantdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        meternoin=intent.getStringExtra("meterno");
        getmymeter(meternoin);


        TVsysrtc=findViewById(R.id.systrtc);
        TVmetrtc=findViewById(R.id.metrtc);
        TVmetrv=findViewById(R.id.metrv);
        TVmetyv=findViewById(R.id.metyv);
        TVmetbv=findViewById(R.id.metbv);
        TVmetkw=findViewById(R.id.metkw);
        TVmetkwh=findViewById(R.id.metkwh);
        TVmeterno=findViewById(R.id.meterno);
        TVmetrc=findViewById(R.id.metrc);
        TVmetyc=findViewById(R.id.metyc);
        TVmetbc=findViewById(R.id.metbc);
        TVmetkvah=findViewById(R.id.metkvah);
        TVmetinstant=findViewById(R.id.metinstant);
        TVmetfreq=findViewById(R.id.metfreq);
        TVmetmdkw=findViewById(R.id.metmdkw);

}
    public void getmymeter(final String meterno) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalUrlvalidation.meterreading_details, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("exits");

                    if (abc)
                    {
                        JSONObject users = jObj.getJSONObject("meterdetails");
                        String systrtc = users.getString("systemrtc");
                        String metrtc = users.getString("meterrtc");
                        String metrv = users.getString("rv");
                        String metyv = users.getString("yv");
                        String metbv = users.getString("bv");
                        String metkw = users.getString("kw");
                        String metkwh = users.getString("kwh");
                        String meterno = users.getString("meterno");
                        String metrc = users.getString("rc");
                        String metyc = users.getString("yc");
                        String metbc = users.getString("bc");
                        String metkvah = users.getString("kvah");
                        String metinstant = users.getString("instant");
                        String metfreq = users.getString("freq");
                        String metmdkw = users.getString("mdkw");




TVsysrtc.setText("SystemRTC : " + systrtc);
TVmetrtc.setText("MeterRTC : " + metrtc);
TVmetrv.setText("VoltageR : " + metrv);
TVmetyv.setText("VoltageY : " + metyv);
TVmetbv.setText("VoltageV : " + metbv);
TVmetkw.setText("ImpKwh : " + metkw);
TVmetkwh.setText("ExpKwh : " + metkwh);
TVmeterno.setText("Meter NO : " + meterno);
TVmetrc.setText("CurrentR : " + metrc);
TVmetyc.setText("CurrentY : " + metyc);
TVmetbc.setText("CurrentB : " + metbc);
TVmetkvah.setText("KVAH : " +metkvah);
TVmetinstant.setText("InstantPF :" +metinstant);
TVmetfreq.setText("Frequency : " +metfreq);
TVmetmdkw.setText("Frequency : " +metmdkw);

   }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No details Found!", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            { }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("meterno", meterno);

                return params;
            }
            
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
