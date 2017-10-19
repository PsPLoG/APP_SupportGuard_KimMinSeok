package com.plog.mobilepassguard;
/**
 * Created by Administrator on 2017-10-17.
 */


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class HttpClient {
    static String  serverIp="http://10.53.128.141:5010";
    HttpURLConnection conn;
    // --------------------------------------------------------------------------------------
    // 로그인 처리
    // 로그인 성공 시 true 실패시 false 반환
    // --------------------------------------------------------------------------------------

    public boolean getJSON(String type ,String idCode, String aCode, String pName) {

        try {
            URL url;
                url = new URL(serverIp+"/pass/?type="+URLEncoder.encode(type)+"&idCode="+idCode+"&aCode="+aCode+"&pName="+ URLEncoder.encode(pName));
            Log.i("?",url.toString());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10*1000);
            conn.setReadTimeout(10*1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Kepp-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.connect();
            int status = conn.getResponseCode();

            switch(status){
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine())!=null){
                        if(line.contains("Created")) {
                            br.close();
                            return true;
                        }
                        sb.append(line + "\n");
                    }
                    br.close();
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", "NETWORK ERROR:" + e);
        }
        return false;
    }

    public String getPassData(String idcode) {

        try {
            URL url = new URL(serverIp+"/loadgdata/?idcode="+idcode);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10*1000);
            conn.setReadTimeout(10*1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Kepp-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.connect();
            int status = conn.getResponseCode();
            Log.i("getPassData", "ProxyResponseCode:"+ status);

            switch(status){
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = br.readLine())!=null)
                        sb.append(line + "\n");

                    br.close();
                    return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", "NETWORK ERROR:" + e);
        }
        return "false";
    }
    public String getPassLog(String acode) {

        try {
            URL url = new URL(serverIp+"/passlog/?aCode="+acode);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10*1000);
            conn.setReadTimeout(10*1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Kepp-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.connect();

            int status = conn.getResponseCode();
            Log.i("getPassData", "ProxyResponseCode:"+ status);

            switch(status){
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine())!=null){
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", "NETWORK ERROR:" + e);
        }
        return "false";
    }
}


class AsyncHttp {
    private final Context context;

    public AsyncHttp(Context context) {
        this.context = context;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();
    public void loadHttp(String Url) {
        RequestParams params;
        String ret;
        Log.i("load",Url);
        //----------------------------------------------------------
        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String test = null;
                try {
                    test = new String(bytes, "UTF-8");
                    Log.i("client", test);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

            }
        });//---------------------------------------------------------
    }
}
