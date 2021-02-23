package com.simo.mycalcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CALC_AIDL";
    CalcService calculateService;
    CalcServiceConnetion calcServiceConnetion;

    Button addBtn,subBtn,divBtn,mulBtn;
    TextView resultView;
    EditText value1Text,value2Text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
        UISetup();
    }

    private void UISetup(){
        addBtn = (Button) findViewById(R.id.addbutton);
        subBtn = (Button) findViewById(R.id.subtractBtn);
        divBtn = (Button) findViewById(R.id.divBtn);
        mulBtn = (Button) findViewById(R.id.mulBtn);

        resultView = (TextView)findViewById(R.id.resultView);
        value1Text = (EditText)findViewById(R.id.Value1Text);
        value2Text = (EditText)findViewById(R.id.Value2Text);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value1,value2,result =  -1;
                value1 = Integer.parseInt(value1Text.getText().toString());
                value2 = Integer.parseInt(value2Text.getText().toString());

                try {
                    result = calculateService.add(value1, value2);
                } catch (RemoteException e) {
                    Log.d(TAG, "onClick failed with: " + e);
                    e.printStackTrace();
                }
                resultView.setText(new Integer(result).toString());
            }


        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value1,value2,result =  -1;
                value1 = Integer.parseInt(value1Text.getText().toString());
                value2 = Integer.parseInt(value2Text.getText().toString());

                try {
                    result = calculateService.subtract(value1, value2);
                } catch (RemoteException e) {
                    Log.d(TAG, "onClick failed with: " + e);
                    e.printStackTrace();
                }
                resultView.setText(new Integer(result).toString());

            }
        });
        divBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value1,value2,result =  -1;
                value1 = Integer.parseInt(value1Text.getText().toString());
                value2 = Integer.parseInt(value2Text.getText().toString());

                try {
                    result = calculateService.divide(value1, value2);
                } catch (RemoteException e) {
                    Log.d(TAG, "onClick failed with: " + e);
                    e.printStackTrace();
                }
                resultView.setText(new Integer(result).toString());

            }
        });
        mulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value1,value2,result =  -1;
                value1 = Integer.parseInt(value1Text.getText().toString());
                value2 = Integer.parseInt(value2Text.getText().toString());

                try {
                    result = calculateService.multiply(value1, value2);
                } catch (RemoteException e) {
                    Log.d(TAG, "onClick failed with: " + e);
                    e.printStackTrace();
                }
                resultView.setText(new Integer(result).toString());

            }
        });
    }



    /**
     * This class represents the actual service connection. It casts the bound
     * stub implementation of the service to the AIDL interface.
     */
    class CalcServiceConnetion implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            calculateService = CalcService.Stub.asInterface((IBinder)service);
            Log.d(TAG, "onServiceConnected() connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            calculateService = null;
            Log.d(TAG, "onServiceDisconnected() disconnected");
        }
    }


    /** Binds our activity to the Calaculateservice. */
    private void initService() {
        calcServiceConnetion = new CalcServiceConnetion();
        Intent i = new Intent();
        i.setClassName("com.simo.mycalcapp", com.simo.mycalcapp.CalculateService.class.getName());
        boolean  con  = bindService(i, calcServiceConnetion, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "initService() bound with " + con);
    }

    /** Unbinds our activity from the Calaculateservice. */
    private void releaseService() {
        unbindService(calcServiceConnetion);
        calcServiceConnetion = null;
        Log.d(TAG, "releaseService() unbound.");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
}