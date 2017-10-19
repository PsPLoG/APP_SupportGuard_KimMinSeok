package com.plog.mobilepass;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    final static HttpClient client = new HttpClient();;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBt =(Button) findViewById(R.id.email_sign_in_button);

        loginBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id =(TextView) findViewById(R.id.id);
                TextView pass =(TextView) findViewById(R.id.password);

                final String inputId = id.getText().toString();
                final String inputPass = pass.getText().toString();
                class LoginPros extends AsyncTask<String ,Integer , Boolean>
                {
                    ProgressDialog dialog;
                    boolean sucLogin=false;
                    String[] data;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setTitle("로그인중입니다.");
                        dialog.show();
                    }
                    @Override
                    protected Boolean doInBackground(String... params) {
                        String ret = client.getJSON(inputId, inputPass);
                        data = ret.split("\n");
                        if((data[0].trim()).equals("true")) {
                            sucLogin=true;
                            Log.i("login", "로그인 성공");
                        } else {
                            Log.i("login", "로그인 실패");
                        }
                        return false;
                    }
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        dialog.dismiss();
                        if(sucLogin){
                            Intent intent = new Intent(LoginActivity.this,PassPage.class);
                            intent.putExtra("passdata",data);
                            startActivity(intent);
                            finish();
                            sucLogin= false;
                        } else {
                            Toast toast=Toast.makeText(getApplication(), "인터넷 상태나 ID,PASS를확인해주세요", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                new LoginPros().execute();


            }
        });
    }
}

