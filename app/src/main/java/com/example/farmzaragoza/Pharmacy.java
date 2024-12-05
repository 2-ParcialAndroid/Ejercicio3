package com.example.farmzaragoza;

import android.os.Parcel;
import android.os.Parcelable;

public class Pharmacy implements Parcelable {
    private String name;
    private String phone;
    private double latitude;
    private double longitude;

    public Pharmacy() {}

    public Pharmacy(String name, String phone, double latitude, double longitude) {
        this.name = name;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Pharmacy(Parcel in) {
        name = in.readString();
        phone = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Pharmacy> CREATOR = new Creator<Pharmacy>() {
        @Override
        public Pharmacy createFromParcel(Parcel in) {
            return new Pharmacy(in);
        }

        @Override
        public Pharmacy[] newArray(int size) {
            return new Pharmacy[size];
        }
    };

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}