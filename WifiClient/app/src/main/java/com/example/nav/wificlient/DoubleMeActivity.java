package com.example.nav.wificlient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.view.MotionEvent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.LinearLayout;
import android.view.Display;
import android.graphics.Point;

public class DoubleMeActivity extends Activity implements OnClickListener {

    EditText inputValue=null;
    Integer doubledValue =0;
    Button doubleMe;
    String coord;
    String urlAddress = "http://152.23.76.153:8080/BluetoothServer/DoubleMeServlet"; // home
    //String urlAddress = "http://152.23.89.54:8080/BluetoothServer/DoubleMeServlet"; // Sitterson
    double xMax = 0, yMax = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);
        doubleMe = (Button) findViewById(R.id.doubleme);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        xMax = size.x;
        yMax = size.y;

        doubleMe.setOnClickListener(this);
        LinearLayout mLayout = (LinearLayout)findViewById(R.id.mainlayout);
        //mLayout.setOnClickListener(this);
        mLayout.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ||
                        event.getAction() == MotionEvent.ACTION_MOVE){
                    int xC = (int) event.getX();
                    int yC = (int) event.getY();
                    double xFrac = (double)xC / xMax, yFrac = (double)yC / yMax;
                    //Log.wtf("ip", InetAddress.getLocalHost());
                    coord = String.format("%f %f", xFrac, yFrac);
                    new Thread(new Runnable() {
                        public void run() {
                                //Looper.prepare();

                            try {
                                String coordinates = coord;

                                //String coordinates = (String)mHandler.obtainMessage().obj;
                                URL url = new URL(urlAddress);
                                URLConnection connection = url.openConnection();

                                //inputString = URLEncoder.encode(inputString, "UTF-8");

                                //Log.d("inputString", ""+coordinates);

                                connection.setDoOutput(true);
                                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                                out.write(coordinates);
                                out.close();

                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                                in.close();
                            }catch(Exception e)
                            {
                                Log.d("Exception",e.toString());
                            }
                        }

                    }).start();
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.doubleme:

                new Thread(new Runnable() {
                    public void run() {

                        try{
                            URL url = new URL(urlAddress);
                            URLConnection connection = url.openConnection();

                            //inputString = URLEncoder.encode(inputString, "UTF-8");
                            String inputString = "click";
                            //Log.d("inputString", inputString);

                            connection.setDoOutput(true);
                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                            out.write(inputString);
                            out.close();

                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                            in.close();

                        }catch(Exception e)
                        {
                            Log.d("Exception",e.toString());
                        }

                    }
                }).start();

                break;

        }
    }
}