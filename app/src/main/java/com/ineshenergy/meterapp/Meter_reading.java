package com.ineshenergy.meterapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Meter_reading implements Parcelable {

    private String meterrefid;
    private String meterno;

    public String getMeterrefid() {
        return meterrefid;
    }

    public void setMeterrefid(String meterrefid) {
        this.meterrefid = meterrefid;
    }

    public String getMeterno() {
        return meterno;
    }

    public void setMeterno(String meterno) {
        this.meterno = meterno;
    }

    public static Creator<Meter_reading> getCREATOR() {
        return CREATOR;
    }

    protected Meter_reading(Parcel in) {
        meterrefid = in.readString();
        meterno = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(meterrefid);
        dest.writeString(meterno);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meter_reading> CREATOR = new Creator<Meter_reading>() {
        @Override
        public Meter_reading createFromParcel(Parcel in) {
            return new Meter_reading(in);
        }

        @Override
        public Meter_reading[] newArray(int size) {
            return new Meter_reading[size];
        }
    };
}
