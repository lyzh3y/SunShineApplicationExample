package com.sunshine.launcher.sunshineapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static  final String TAG = MainActivity.class.getSimpleName();
    private GoogleApiClient client;
    private Button btn1;
    private TextView textView01, textView02;
    String tmDevice = "";
    String  tmSerial = "";
    String androidId = "";
    String  deviceId = "";
    String android_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        textView01 = (TextView) findViewById(R.id.text01);
        textView02 = (TextView) findViewById(R.id.text02);
        android_id = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        tmDevice  = "" + tm.getDeviceId();
        Log.d(TAG, "android_id 为： " + android_id);
        Log.d(TAG, "tmDevice 为 ：  " + tmDevice );
        btn1.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                //引入api--KEY  8b234ce9a49a5efba8475cae1d56ca7b307dd2e4
        //android_id  = Settings.Secure.getString(Context .getContentResolver(), Settings.Secure.ANDROID_ID);


                tmDevice  = "" + tm.getDeviceId();
                Log.d(TAG, "tmDevice 为 ： "  + tmDevice);
                tmSerial    = ""   + tm.getSimSerialNumber();
                Log.d(TAG, "tmSerial 为 ： "  + tmSerial);
                androidId   = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                Log.d(TAG, "androidId");
                UUID  deviceUuid = new  UUID(androidId.hashCode(), ((long)tmDevice.hashCode()<< 32)       | tmSerial.hashCode());

                deviceId = deviceUuid.toString();
                Log.d(TAG, "deviceId 为 ： "  + deviceId);

//                new Thread(new Runnable() {
//                    String str = null;
//
//                    @Override
//                    public void run() {
//                        try {
//                            str = getResponseResult("0","D","2016/05/05", "CA", "1501", "PEK", "SHA", "1");
//                            textView01.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textView01.setText(str);
//                                }
//                            });
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("Details", "&&&&&&&&&&&&&& : " + str);
//                    }
//                }).start();
//
//                new Thread(new Runnable() {
//                    String st = null;
//
//                    @Override
//                    public void run() {
//                        try {
//                            st = getResponseResultForList("2016/05/04 10:10:00", "2016/05/05 10:10:00", "100", "0", "PEK", "1501", null, "0", null, "0", "-1", "30");
//                            textView02.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textView02.setText(st);
//                                }
//                            });
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//
//                        Log.d("SearchForList   ", "***************** :" + st);
//                    }
//                }).start();

                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String getResponseResult(String onlyFgos,String fltType,String flightDate, String airCompany, String flightNum, String strPort, String endPort, String reqFlag) throws UnsupportedEncodingException, HttpException, IOException, JSONException, URISyntaxException {
        String response = "";
        PostMethod method = null;
        HttpClient httpclient = new HttpClient();
        String url = "http://172.22.98.62:8080/flightdetails/search";
        try {
            method = new PostMethod(url);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flightDate", flightDate);

            jsonObject.put("onlyFgos", onlyFgos);
            jsonObject.put("airCompany", airCompany);
            jsonObject.put("flightNum", flightNum);
            jsonObject.put("fltType", fltType);
            jsonObject.put("strPort", strPort);
            jsonObject.put("endPort", endPort);
            jsonObject.put("reqFlag", reqFlag);
            String transJson = jsonObject.toString();
            RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
            method.setRequestEntity(se);
            httpclient.executeMethod(method);
            response = method.getResponseBodyAsString();
            System.out.println(response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


        return response;

    }

    private String getResponseResultForList(String strTime, String endTime, String ctrFlag, String portFlag, String fltStation, String flightNum, String[] stopNum, String deputeFlight, String[] gateNum, String offset, String page, String limit) throws IOException, JSONException, URISyntaxException {
        String response = "";
        PostMethod method = null;
        HttpClient httpclient = new HttpClient();
        String url = "http://172.22.98.62:8080/newterminal/search";
        try {
            method = new PostMethod(url);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("strTime", strTime);
            jsonObject.put("endTime", endTime);
            jsonObject.put("ctrFlag", ctrFlag);
            jsonObject.put("portFlag", portFlag);
            jsonObject.put("fltStation", fltStation);
            jsonObject.put("flightNum", flightNum);
            jsonObject.put("stopNum", stopNum);
            jsonObject.put("deputeFlight", deputeFlight);
            jsonObject.put("gateNum", gateNum);
            jsonObject.put("offset", offset);
            jsonObject.put("page", page);
            jsonObject.put("limit", limit);

            String transJson = jsonObject.toString();
            RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
            method.setRequestEntity(se);
            httpclient.executeMethod(method);
            response = method.getResponseBodyAsString();
            System.out.println(response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


        return response;

    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sunshine.launcher.sunshineapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sunshine.launcher.sunshineapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
