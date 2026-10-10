package com.hsae.bluetoothsdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothDevice implements Parcelable {
    public static final Parcelable.Creator<BluetoothDevice> CREATOR = new Parcelable.Creator<BluetoothDevice>() { // from class: com.hsae.bluetoothsdk.bean.BluetoothDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothDevice createFromParcel(Parcel in) {
            return new BluetoothDevice(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothDevice[] newArray(int size) {
            return new BluetoothDevice[size];
        }
    };
    public static final int STATUS_CONNECTED = 5;
    public static final int STATUS_CONNECTING = 4;
    public static final int STATUS_DISCONNECTED = 2;
    public static final int STATUS_DISCONNECTING = 6;
    public static final int STATUS_PAIRED = 2;
    public static final int STATUS_PAIRING = 1;
    public static final int STATUS_UNPAIRED = 0;
    public static final int STATUS_UNPAIRING = 3;
    private int COD;
    private int RSSI;
    private String mac;
    private String name;
    private int status;

    public BluetoothDevice() {
        this.name = "";
        this.mac = "";
        this.COD = 0;
        this.RSSI = 0;
        this.status = 0;
    }

    public BluetoothDevice(String name, String mac, int COD, int RSSI, int status) {
        this.name = name;
        this.mac = mac;
        this.COD = COD;
        this.RSSI = RSSI;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCOD() {
        return this.COD;
    }

    public void setCOD(int cOD) {
        this.COD = cOD;
    }

    public int getRSSI() {
        return this.RSSI;
    }

    public void setRSSI(int rSSI) {
        this.RSSI = rSSI;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mac);
        dest.writeInt(this.COD);
        dest.writeInt(this.RSSI);
        dest.writeInt(this.status);
    }

    public void readFromParcel(Parcel in) {
        this.name = in.readString();
        this.mac = in.readString();
        this.COD = in.readInt();
        this.RSSI = in.readInt();
        this.status = in.readInt();
    }

    public BluetoothDevice(Parcel in) {
        readFromParcel(in);
    }
}
