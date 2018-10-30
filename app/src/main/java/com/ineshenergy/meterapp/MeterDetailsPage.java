package com.ineshenergy.meterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

public class MeterDetailsPage extends AppCompatActivity {
Button Binstant;
Button Bconsup;
Button Bbills;
Button Bback;
Button Bpay;
String meternoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_details_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        meternoin=intent.getStringExtra("meterno");


        Binstant=findViewById(R.id.Binstant);
Bconsup=findViewById(R.id.Bconsup);
Bbills=findViewById(R.id.Bbills);
Bback=findViewById(R.id.Bback);
Bpay=findViewById(R.id.Bpay);

Bconsup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MeterDetailsPage.this, Meterdetails.class);
        intent.putExtra("meterno",meternoin);
        startActivity(intent);

    }
});
Binstant.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(MeterDetailsPage.this, Meterdetails.class);
        intent.putExtra("meterno",meternoin);
        startActivity(intent);
    }
});
Bbills.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        new MaterialStyledDialog.Builder(MeterDetailsPage.this)
                .setTitle("Bill Details")
                .setPositiveText("ok")
                .withDialogAnimation(true)
                .setStyle(Style.HEADER_WITH_ICON)
                .setDescription("Bill to be paid")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                //.setStyle(Style.HEADER_WITH_TITLE)
                .show();


    }

});
Bback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MeterDetailsPage.this,HomePage.class));
    }
});
Bpay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        new MaterialStyledDialog.Builder(MeterDetailsPage.this)
                .setTitle("Payment")
                .setPositiveText("OK")
                .withDialogAnimation(true)
                .setStyle(Style.HEADER_WITH_ICON)
                .setDescription("Current Month Payment")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                //.setStyle(Style.HEADER_WITH_TITLE)
                .show();
    }

});


    }
}

