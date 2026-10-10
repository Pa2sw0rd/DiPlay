package com.hsae.bluetoothsdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.hsae.bluetoothsdk.bean.BluetoothDevice;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IBluetoothManager extends IInterface {
    void acceptConnect(int i) throws RemoteException;

    void acceptPair(String str, boolean z) throws RemoteException;

    void btPowerOff() throws RemoteException;

    void btPowerOn() throws RemoteException;

    void connect(String str, String str2) throws RemoteException;

    void connectA2dp(String str) throws RemoteException;

    void connectHfp(String str) throws RemoteException;

    void disConnect() throws RemoteException;

    void disConnectA2dp() throws RemoteException;

    int getBatteryCharge() throws RemoteException;

    String getConnectDeviceAddress() throws RemoteException;

    String getConnectDeviceName() throws RemoteException;

    int getConnectStatus(int i) throws RemoteException;

    String getDeviceName() throws RemoteException;

    List<BluetoothDevice> getPairedDevices() throws RemoteException;

    int getPowerStatus() throws RemoteException;

    String getSdkVersion() throws RemoteException;

    int getSignalValue() throws RemoteException;

    boolean isParing() throws RemoteException;

    boolean isSearching() throws RemoteException;

    int pairDevice(String str, int i) throws RemoteException;

    boolean registCallback(IBluetoothCallback iBluetoothCallback) throws RemoteException;

    void rejectConnect(int i) throws RemoteException;

    void rejectPair(String str, boolean z) throws RemoteException;

    int removePair(String str) throws RemoteException;

    int search(boolean z) throws RemoteException;

    void setAutoPowerOn(boolean z) throws RemoteException;

    void setBtName(String str) throws RemoteException;

    int stopSearch() throws RemoteException;

    boolean supportInbandRing() throws RemoteException;

    boolean supportMusic() throws RemoteException;

    boolean supportPhone() throws RemoteException;

    void unBindService() throws RemoteException;

    boolean unregistCallback(IBluetoothCallback iBluetoothCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IBluetoothManager {
        private static final String DESCRIPTOR = "com.hsae.bluetoothsdk.IBluetoothManager";
        static final int TRANSACTION_acceptConnect = 25;
        static final int TRANSACTION_acceptPair = 12;
        static final int TRANSACTION_btPowerOff = 6;
        static final int TRANSACTION_btPowerOn = 5;
        static final int TRANSACTION_connect = 16;
        static final int TRANSACTION_connectA2dp = 18;
        static final int TRANSACTION_connectHfp = 17;
        static final int TRANSACTION_disConnect = 20;
        static final int TRANSACTION_disConnectA2dp = 21;
        static final int TRANSACTION_getBatteryCharge = 29;
        static final int TRANSACTION_getConnectDeviceAddress = 23;
        static final int TRANSACTION_getConnectDeviceName = 22;
        static final int TRANSACTION_getConnectStatus = 19;
        static final int TRANSACTION_getDeviceName = 28;
        static final int TRANSACTION_getPairedDevices = 27;
        static final int TRANSACTION_getPowerStatus = 7;
        static final int TRANSACTION_getSdkVersion = 32;
        static final int TRANSACTION_getSignalValue = 30;
        static final int TRANSACTION_isParing = 14;
        static final int TRANSACTION_isSearching = 10;
        static final int TRANSACTION_pairDevice = 11;
        static final int TRANSACTION_registCallback = 1;
        static final int TRANSACTION_rejectConnect = 26;
        static final int TRANSACTION_rejectPair = 13;
        static final int TRANSACTION_removePair = 15;
        static final int TRANSACTION_search = 8;
        static final int TRANSACTION_setAutoPowerOn = 31;
        static final int TRANSACTION_setBtName = 4;
        static final int TRANSACTION_stopSearch = 9;
        static final int TRANSACTION_supportInbandRing = 24;
        static final int TRANSACTION_supportMusic = 33;
        static final int TRANSACTION_supportPhone = 34;
        static final int TRANSACTION_unBindService = 3;
        static final int TRANSACTION_unregistCallback = 2;

        public Stub() {
            attachInterface(this, "com.hsae.bluetoothsdk.IBluetoothManager");
        }

        public static IBluetoothManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.hsae.bluetoothsdk.IBluetoothManager");
            if (iin != null && (iin instanceof IBluetoothManager)) {
                return (IBluetoothManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString("com.hsae.bluetoothsdk.IBluetoothManager");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zRegistCallback = registCallback(IBluetoothCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(zRegistCallback ? 1 : 0);
                    return true;
                case 2:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zUnregistCallback = unregistCallback(IBluetoothCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(zUnregistCallback ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    unBindService();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    setBtName(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    btPowerOn();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    btPowerOff();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int powerStatus = getPowerStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(powerStatus);
                    return true;
                case 8:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int iSearch = search(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSearch);
                    return true;
                case 9:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int iStopSearch = stopSearch();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopSearch);
                    return true;
                case 10:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zIsSearching = isSearching();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsSearching ? 1 : 0);
                    return true;
                case 11:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int iPairDevice = pairDevice(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(iPairDevice);
                    return true;
                case 12:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    acceptPair(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    rejectPair(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zIsParing = isParing();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsParing ? 1 : 0);
                    return true;
                case 15:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int iRemovePair = removePair(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePair);
                    return true;
                case 16:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    connect(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    connectHfp(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    connectA2dp(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int connectStatus = getConnectStatus(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(connectStatus);
                    return true;
                case 20:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    disConnect();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    disConnectA2dp();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    String connectDeviceName = getConnectDeviceName();
                    parcel2.writeNoException();
                    parcel2.writeString(connectDeviceName);
                    return true;
                case 23:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    String connectDeviceAddress = getConnectDeviceAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(connectDeviceAddress);
                    return true;
                case 24:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zSupportInbandRing = supportInbandRing();
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportInbandRing ? 1 : 0);
                    return true;
                case 25:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    acceptConnect(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    rejectConnect(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    List<BluetoothDevice> pairedDevices = getPairedDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pairedDevices);
                    return true;
                case 28:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    String deviceName = getDeviceName();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceName);
                    return true;
                case 29:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int batteryCharge = getBatteryCharge();
                    parcel2.writeNoException();
                    parcel2.writeInt(batteryCharge);
                    return true;
                case 30:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    int signalValue = getSignalValue();
                    parcel2.writeNoException();
                    parcel2.writeInt(signalValue);
                    return true;
                case 31:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    setAutoPowerOn(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    String sdkVersion = getSdkVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(sdkVersion);
                    return true;
                case 33:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zSupportMusic = supportMusic();
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportMusic ? 1 : 0);
                    return true;
                case 34:
                    parcel.enforceInterface("com.hsae.bluetoothsdk.IBluetoothManager");
                    boolean zSupportPhone = supportPhone();
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportPhone ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBluetoothManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.hsae.bluetoothsdk.IBluetoothManager";
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean registCallback(IBluetoothCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean unregistCallback(IBluetoothCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void unBindService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void setBtName(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeString(name);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void btPowerOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void btPowerOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int getPowerStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int search(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int stopSearch() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean isSearching() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int pairDevice(String address, int cod) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeString(address);
                    _data.writeInt(cod);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void acceptPair(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void rejectPair(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean isParing() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int removePair(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeString(address);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void connect(String address, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeString(address);
                    _data.writeString(name);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void connectHfp(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeString(address);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void connectA2dp(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeString(address);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int getConnectStatus(int profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeInt(profile);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void disConnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void disConnectA2dp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public String getConnectDeviceName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public String getConnectDeviceAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean supportInbandRing() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void acceptConnect(int profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeInt(profile);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void rejectConnect(int profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    _data.writeInt(profile);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public List<BluetoothDevice> getPairedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    List<BluetoothDevice> _result = _reply.createTypedArrayList(BluetoothDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public String getDeviceName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int getBatteryCharge() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public int getSignalValue() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public void setAutoPowerOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public String getSdkVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean supportMusic() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothManager
            public boolean supportPhone() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hsae.bluetoothsdk.IBluetoothManager");
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
