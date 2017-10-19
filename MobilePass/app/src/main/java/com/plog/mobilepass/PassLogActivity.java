package com.plog.mobilepass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-18.
 */

public class PassLogActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passlog);

        class LoadPros extends AsyncTask<String ,Integer , Boolean>
        {
            ProgressDialog dialog;
            boolean sucLogin=false;
            String ret;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(PassLogActivity.this);
                dialog.setTitle("로드중입니다.");
                dialog.show();
            }
            @Override
            protected Boolean doInBackground(String... params) {
                String idCode = getIntent().getStringExtra("idcode");
                ret = LoginActivity.client.getPassLog(idCode);
                Log.i("d",ret);
                return false;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();
                ArrayList<PassLogData> arrayList= new ArrayList<PassLogData>();
                                    Log.i("log",ret);
                JSONArray jArr = null;
                try {
                    jArr = new JSONArray(ret);
                    for (int i = 0; i < jArr.length(); ++i) {
                            JSONObject jObj = jArr.getJSONObject(i);
                            PassLogData logdata = new PassLogData();
                            Log.i("load", jObj.getString("passTime"));
                            Log.i("load", jObj.getInt("Passtype")+"");
                            Log.i("load", jObj.getString("aCode"));
                            logdata.passTime = jObj.getString("passTime");
                            logdata.passType = jObj.getString("Passtype");
                            logdata.passPlace = jObj.getString("aCode");

                            Log.i("load", logdata.passTime);
                            arrayList.add(logdata);
                        }
                    ListView listview = (ListView)findViewById(R.id.listview1);
                    PassLogAdapter adater = new PassLogAdapter(arrayList);
                    listview.setAdapter(adater);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        new LoadPros().execute();





    }
}
