package com.example.farmzaragoza;

public class Pharmacy {
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

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
