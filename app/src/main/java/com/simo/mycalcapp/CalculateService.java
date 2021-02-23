package com.simo.mycalcapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CalculateService extends Service {
    public CalculateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return  new CalcService.Stub() {
            @Override
            public int add(int value1, int value2) throws RemoteException {
                return value1 + value2;
            }

            @Override
            public int subtract(int value1, int value2) throws RemoteException {
                return value1 - value2;
            }

            @Override
            public int multiply(int value1, int value2) throws RemoteException {
                return value1*value2;
            }

            @Override
            public int divide(int value1, int value2) throws RemoteException {
                return value1 / value2;
            }
        };

    }
}