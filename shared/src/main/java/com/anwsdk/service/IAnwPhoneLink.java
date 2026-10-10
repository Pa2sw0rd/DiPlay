package com.anwsdk.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * AIDL binder interface for AnwSdk Bluetooth service (Nissan HSAE P170 / P660).
 * Implements low-level Bluetooth Socket and SPP transport channels.
 */
public interface IAnwPhoneLink extends IInterface {
    int ANWBT_SocketInit() throws RemoteException;
    int ANWBT_SocketDenit() throws RemoteException;
    int ANWBT_SocketConnect(int nType, String address, byte[] uuid, int channel, long nMTU, long Flag, int[] nIndex) throws RemoteException;
    int ANWBT_SocketDisconnect(int nindex) throws RemoteException;
    int ANWBT_SocketWrite(int nindex, byte[] data, int datasize, int[] nWritten) throws RemoteException;
    void ANWBT_RegistrySocketDataCallback(IAnwSocketDataCallBack cb) throws RemoteException;
    void ANWBT_UnRegistrySocketDataCallback(IAnwSocketDataCallBack cb) throws RemoteException;
    boolean ANWBT_GetSocketInitStatus() throws RemoteException;

    int ANWBT_SPPInit(byte[] b) throws RemoteException;
    int ANWBT_SPPDenit() throws RemoteException;
    int ANWBT_SPPConnect(String address, byte[] b, int[] nIndex) throws RemoteException;
    int ANWBT_SPPDisconnect(int nindex) throws RemoteException;
    int ANWBT_SPPWrite(int nindex, byte[] b, int datasize, int[] nWritten) throws RemoteException;
    boolean ANWBT_GetSPPInitStatus() throws RemoteException;
    void ANWBT_RegistrySPPDataCallback(IAnwSPPDataCallBack cb) throws RemoteException;
    void ANWBT_UnRegistrySPPDataCallback(IAnwSPPDataCallBack cb) throws RemoteException;

    public static abstract class Stub extends Binder implements IAnwPhoneLink {
        public static final String DESCRIPTOR = "com.anwsdk.service.IAnwPhoneLink";
        static final int TRANSACTION_ANWBT_SPPInit = 62;
        static final int TRANSACTION_ANWBT_SPPDenit = 63;
        static final int TRANSACTION_ANWBT_SPPConnect = 64;
        static final int TRANSACTION_ANWBT_SPPDisconnect = 65;
        static final int TRANSACTION_ANWBT_SPPWrite = 66;
        static final int TRANSACTION_ANWBT_GetSPPInitStatus = 67;
        static final int TRANSACTION_ANWBT_RegistrySPPDataCallback = 68;
        static final int TRANSACTION_ANWBT_UnRegistrySPPDataCallback = 69;

        static final int TRANSACTION_ANWBT_SocketInit = 222;
        static final int TRANSACTION_ANWBT_SocketDenit = 224;
        static final int TRANSACTION_ANWBT_SocketConnect = 225;
        static final int TRANSACTION_ANWBT_SocketDisconnect = 226;
        static final int TRANSACTION_ANWBT_SocketWrite = 227;
        static final int TRANSACTION_ANWBT_RegistrySocketDataCallback = 231;
        static final int TRANSACTION_ANWBT_UnRegistrySocketDataCallback = 232;
        static final int TRANSACTION_ANWBT_GetSocketInitStatus = 233;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAnwPhoneLink asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAnwPhoneLink)) {
                return (IAnwPhoneLink) iin;
            }
            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        private static class Proxy implements IAnwPhoneLink {
            private final IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            @Override
            public int ANWBT_SocketInit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_ANWBT_SocketInit, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SocketDenit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_ANWBT_SocketDenit, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SocketConnect(int nType, String address, byte[] uuid, int channel, long nMTU, long Flag, int[] nIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(nType);
                    _data.writeString(address);
                    _data.writeByteArray(uuid);
                    _data.writeInt(channel);
                    _data.writeLong(nMTU);
                    _data.writeLong(Flag);
                    _data.writeInt(nIndex != null ? nIndex.length : -1);
                    mRemote.transact(TRANSACTION_ANWBT_SocketConnect, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (nIndex != null) _reply.readIntArray(nIndex);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SocketDisconnect(int nindex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(nindex);
                    mRemote.transact(TRANSACTION_ANWBT_SocketDisconnect, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SocketWrite(int nindex, byte[] data, int datasize, int[] nWritten) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(nindex);
                    _data.writeByteArray(data);
                    _data.writeInt(datasize);
                    _data.writeInt(nWritten != null ? nWritten.length : -1);
                    mRemote.transact(TRANSACTION_ANWBT_SocketWrite, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (nWritten != null) _reply.readIntArray(nWritten);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void ANWBT_RegistrySocketDataCallback(IAnwSocketDataCallBack cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    mRemote.transact(TRANSACTION_ANWBT_RegistrySocketDataCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void ANWBT_UnRegistrySocketDataCallback(IAnwSocketDataCallBack cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    mRemote.transact(TRANSACTION_ANWBT_UnRegistrySocketDataCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public boolean ANWBT_GetSocketInitStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_ANWBT_GetSocketInitStatus, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt() != 0;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SPPInit(byte[] b) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByteArray(b);
                    mRemote.transact(TRANSACTION_ANWBT_SPPInit, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SPPDenit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_ANWBT_SPPDenit, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SPPConnect(String address, byte[] b, int[] nIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeByteArray(b);
                    _data.writeInt(nIndex != null ? nIndex.length : -1);
                    mRemote.transact(TRANSACTION_ANWBT_SPPConnect, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (nIndex != null) _reply.readIntArray(nIndex);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SPPDisconnect(int nindex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(nindex);
                    mRemote.transact(TRANSACTION_ANWBT_SPPDisconnect, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public int ANWBT_SPPWrite(int nindex, byte[] b, int datasize, int[] nWritten) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(nindex);
                    _data.writeByteArray(b);
                    _data.writeInt(datasize);
                    _data.writeInt(nWritten != null ? nWritten.length : -1);
                    mRemote.transact(TRANSACTION_ANWBT_SPPWrite, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (nWritten != null) _reply.readIntArray(nWritten);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public boolean ANWBT_GetSPPInitStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_ANWBT_GetSPPInitStatus, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt() != 0;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void ANWBT_RegistrySPPDataCallback(IAnwSPPDataCallBack cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    mRemote.transact(TRANSACTION_ANWBT_RegistrySPPDataCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void ANWBT_UnRegistrySPPDataCallback(IAnwSPPDataCallBack cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    mRemote.transact(TRANSACTION_ANWBT_UnRegistrySPPDataCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
