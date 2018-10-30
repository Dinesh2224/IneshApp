package com.ineshenergy.meterapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
ListView LVmeter;
EditText ETsearchtext;
ImageButton Bsear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LVmeter=findViewById(R.id.meterdetail_list);
ETsearchtext=findViewById(R.id.searchtext_meter);

Bsear=findViewById(R.id.Bsearchimage);
        Intent intent = getIntent();
        final String smobile_number = intent.getStringExtra("mobile_number");
Bsear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String newQuery=ETsearchtext.getText().toString();
        new kilomi().execute(GlobalUrlvalidation.meterreading_query+"?querypass="+newQuery+"?mobileNumber="+smobile_number);

    }
});


        new kilomi().execute(GlobalUrlvalidation.meterreading+"?mobileNumber="+smobile_number);
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomi extends AsyncTask<String, String, List<Meter_reading>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Meter_reading> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Meter_reading> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Meter_reading catego = gson.fromJson(finalObject.toString(), Meter_reading.class);
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Meter_reading> movieMode) {
            super.onPostExecute(movieMode);
            if ((movieMode != null) && (movieMode.size()>0)) {
                MovieAdap imageslider_adapter = new MovieAdap(getApplicationContext(), R.layout.meterlistingview, movieMode);
                LVmeter.setAdapter(imageslider_adapter);
                imageslider_adapter.notifyDataSetChanged();
                LVmeter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Meter_reading categorieslist = movieMode.get(position);
                        Intent intent = new Intent(HomePage.this, MeterDetailsPage.class);
                        intent.putExtra("meterno",categorieslist.getMeterno());
                        startActivity(intent);
                    }
                });

            }
            else
            {
                Toast.makeText(getApplicationContext(), "No Meter Reading is Available Now", Toast.LENGTH_SHORT).show();

            }


        }
    }
    public class MovieAdap extends ArrayAdapter {
        private List<Meter_reading> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        MovieAdap(Context context, int resource, List<Meter_reading> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MovieAdap.ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new MovieAdap.ViewHolder();

                holder.meterno = (TextView) convertView.findViewById(R.id.systrtc);
                convertView.setTag(holder);
            }//ino
            else {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            final Meter_reading ccitacc = movieModelList.get(position);

            holder.meterno.setText("Meter No:"+ccitacc.getMeterno());
            return convertView;
        }

        class ViewHolder {
            public TextView meterno;



        }
    }



}
