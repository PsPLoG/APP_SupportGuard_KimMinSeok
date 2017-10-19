package com.plog.mobilepass;

/**
 * Created by Administrator on 2017-10-17.
 */
import android.util.Log;

import com.loopj.android.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {
    String serverIp = "http://10.53.128.141:5010";
    // --------------------------------------------------------------------------------------
    // 로그인 처리
    // 로그인 성공 시 true 실패시 false 반환
    // --------------------------------------------------------------------------------------
    public String getJSON(String id, String pass) {

        try {
            URL url = new URL(serverIp+"/loaddata/?id="+id+"&pass="+pass);
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
            Log.i("test", "ProxyResponseCode:"+ status);

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


    // --------------------------------------------------------------------------------------
    // 로그인후
    // db 사용자 정보를 로딩
    // --------------------------------------------------------------------------------------
    public String getPassData(String id) {

        try {
            URL url = new URL(serverIp+"/passdata/?id="+id);
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
                        Log.i("getPassData", "ProxyResponseCode:"+ line);
                        sb.append(line);
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
    // ---------------------------------------------------------------------------------------------
    // 출입 기록으 불러옴
    // ---------------------------------------------------------------------------------------------
    public String getPassLog(String acode) {

        try {
            URL url = new URL(serverIp+"/passlogperson/?idCode="+acode);
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