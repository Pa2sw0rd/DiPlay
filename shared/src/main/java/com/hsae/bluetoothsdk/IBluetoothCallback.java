package com.hsae.bluetoothsdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IBluetoothCallback extends IInterface {
    void onBatterLevelChanged(int i) throws RemoteException;

    void onBtStateChanged(int i, String str) throws RemoteException;

    void onConnectStateChanged(int i, int i2, int i3) throws RemoteException;

    void onDeviceConnectRequest(String str, String str2, int i, int i2) throws RemoteException;

    void onDeviceInquiried(String str, String str2, int i, int i2, boolean z) throws RemoteException;

    void onDeviceNameChanged(String str) throws RemoteException;

    void onDevicePairRequest(String str, String str2, boolean z) throws RemoteException;

    void onPairStateChanged(String str, int i) throws RemoteException;

    void onPowerStateChanged(int i) throws RemoteException;

    void onServiceConnected() throws RemoteException;

    void onServiceDisconnected() throws RemoteException;

    void onSignelLevelChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IBluetoothCallback {
        private static final String DESCRIPTOR = "com.hsae.bluetoothsdk.IBluetoothCallback";
        static final int TRANSACTION_onBatterLevelChanged = 2;
        static final int TRANSACTION_onBtStateChanged = 10;
        static final int TRANSACTION_onConnectStateChanged = 6;
        static final int TRANSACTION_onDeviceConnectRequest = 7;
        static final int TRANSACTION_onDeviceInquiried = 9;
        static final int TRANSACTION_onDeviceNameChanged = 4;
        static final int TRANSACTION_onDevicePairRequest = 8;
        static final int TRANSACTION_onPairStateChanged = 5;
        static final int TRANSACTION_onPowerStateChanged = 1;
        static final int TRANSACTION_onServiceConnected = 11;
        static final int TRANSACTION_onServiceDisconnected = 12;
        static final int TRANSACTION_onSignelLevelChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBluetoothCallback)) {
                return (IBluetoothCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    onPowerStateChanged(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg1 = data.readInt();
                    onBatterLevelChanged(_arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg2 = data.readInt();
                    onSignelLevelChanged(_arg2);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg3 = data.readString();
                    onDeviceNameChanged(_arg3);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg4 = data.readString();
                    int _arg5 = data.readInt();
                    onPairStateChanged(_arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg6 = data.readInt();
                    int _arg7 = data.readInt();
                    int _arg8 = data.readInt();
                    onConnectStateChanged(_arg6, _arg7, _arg8);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg9 = data.readString();
                    String _arg10 = data.readString();
                    int _arg11 = data.readInt();
                    int _arg12 = data.readInt();
                    onDeviceConnectRequest(_arg9, _arg10, _arg11, _arg12);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg13 = data.readString();
                    String _arg14 = data.readString();
                    boolean _arg15 = data.readInt() != 0;
                    onDevicePairRequest(_arg13, _arg14, _arg15);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg16 = data.readString();
                    String _arg17 = data.readString();
                    int _arg18 = data.readInt();
                    int _arg19 = data.readInt();
                    boolean _arg20 = data.readInt() != 0;
                    onDeviceInquiried(_arg16, _arg17, _arg18, _arg19, _arg20);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg21 = data.readInt();
                    String _arg22 = data.readString();
                    onBtStateChanged(_arg21, _arg22);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    onServiceConnected();
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    onServiceDisconnected();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBluetoothCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onPowerStateChanged(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onBatterLevelChanged(int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onSignelLevelChanged(int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onDeviceNameChanged(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onPairStateChanged(String address, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(status);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onConnectStateChanged(int profile, int state, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(profile);
                    _data.writeInt(state);
                    _data.writeInt(reason);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onDeviceConnectRequest(String deviceName, String address, int profile, int index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceName);
                    _data.writeString(address);
                    _data.writeInt(profile);
                    _data.writeInt(index);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onDevicePairRequest(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onDeviceInquiried(String str, String str2, int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onBtStateChanged(int state, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeString(name);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onServiceConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hsae.bluetoothsdk.IBluetoothCallback
            public void onServiceDisconnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
