package com.plog.mobilepassguard;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PassList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        class LoadPros extends AsyncTask<String ,Integer , Boolean>
        {
            ProgressDialog dialog;
            boolean sucLogin=false;
            String ret;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(PassList.this);
                dialog.setTitle("로드중입니다.");
                dialog.show();
            }
            @Override
            protected Boolean doInBackground(String... params) {
                String cur_acode = getIntent().getStringExtra("cur_acode");
                ret = MainActivity.client.getPassLog(cur_acode);
                Log.i("d",ret);
                return false;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();
                ArrayList<PassLogData> arrayList= new ArrayList<PassLogData>();
                try {

                    //{"num":10,"IdCode":2,"passTime":"2017-10-18T17:14:39.000Z","Passtype":"1","aCode":"153"}
                    JSONArray jArr = new JSONArray(ret);

                    for (int i = 0; i < jArr.length(); ++i) {
                        JSONObject jObj = jArr.getJSONObject(i);
                        PassLogData logdata = new PassLogData();
                        Log.i("load", jObj.getString("passTime"));
                        Log.i("load", jObj.getInt("Passtype")+"");
                        Log.i("load", jObj.getString("aCode"));
                        logdata.passTime = jObj.getString("passTime");
                        logdata.passType = jObj.getInt("Passtype");
                        logdata.passPlace = jObj.getString("aCode");
                        logdata.passName = jObj.getString("pName");
                        logdata.passNum = jObj.getInt("num");

                        Log.i("load", logdata.passTime);
                        arrayList.add(logdata);
                    }
                    ListView listview = (ListView)findViewById(R.id.passlist);
                    PassListAdater adater = new PassListAdater(arrayList);
                    listview.setAdapter(adater);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        new LoadPros().execute();

    }
}