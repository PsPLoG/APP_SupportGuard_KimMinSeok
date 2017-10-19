package com.plog.mobilepassguard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static HttpClient client = new HttpClient();
    Button passBt,list,inPut;
    TextView name,affiliation,id,cur_acode;
    String idCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 바코드 버튼
        final Button bt = (Button) findViewById(R.id.main_bacode);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.initiateScan();
            }
        });

        passBt = (Button)findViewById(R.id.main_pass);
        list = (Button) findViewById(R.id.main_control);
        inPut = (Button) findViewById(R.id.main_inputcode);

        name = (TextView) findViewById(R.id.main_name);
        affiliation = (TextView) findViewById(R.id.main_acode);
        id = (TextView) findViewById(R.id.main_idcode);
        cur_acode = (TextView) findViewById(R.id.main_cur_acode);

        //------------------------------------------------------------------------------------------
        // 입영한 리스트 출력
        //------------------------------------------------------------------------------------------
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PassList.class);
                intent.putExtra("cur_acode",cur_acode.getText().toString());
                startActivity(intent);
            }
        });

        //------------------------------------------------------------------------------------------
        // 입영 처리
        //------------------------------------------------------------------------------------------
        passBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class LoadPros extends AsyncTask<String ,Integer , Boolean>
                {
                    String pcur_acode, pname;
                    ProgressDialog dialog;
                    boolean sucpoc=false;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = new ProgressDialog(MainActivity.this);
                        dialog.setTitle("로드중입니다.");
                        dialog.show();
                        pcur_acode = cur_acode.getText().toString();
                        pname = name.getText().toString();
                    }
                    @Override
                    protected Boolean doInBackground(String... params) {
                        sucpoc = client.getJSON("입영", idCode, pcur_acode, pname);
                        return false;
                    }
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        dialog.dismiss();
                        if(sucpoc) {
                            passBt.setEnabled(false);
                            Toast toast=Toast.makeText(getApplication(), "성공적으로 입영 처리되었습니다.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            Toast toast=Toast.makeText(getApplication(), "실패했습니다 인터넷 연결상태를 확인해주세요", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                new LoadPros().execute();



//                String url = HttpClient.serverIp+"/pass/?type=입영&idCode="+idCode+
//                                                    "&aCode="+cur_acode.getText().toString()+
//                                                    "&pName="+name.getText().toString();
//
//                new AsyncHttp(MainActivity.this).loadHttp(url);
            }
        });

        //----------------------------------------------------------------------------------------------
        // 부대코드 입력
        //----------------------------------------------------------------------------------------------
        inPut.setOnClickListener(new View.OnClickListener() {
            String inputCode;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("부대코드를 입력해주세요");

                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cur_acode.setText(input.getText().toString());
                        bt.setEnabled(true);
                        list.setEnabled(true);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });
    }
    //----------------------------------------------------------------------------------------------
    // 바코드 인식후 데이터 처리 zxing
    //----------------------------------------------------------------------------------------------
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            idCode = scanningResult.getContents();
            String format = scanningResult.getFormatName();
            if(!(idCode==null)) {
                passBt.setEnabled(true);

                class LoadPros extends AsyncTask<String, Integer, Boolean> {
                    ProgressDialog dialog;
                    boolean sucLogin = false;
                    String[] data;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = new ProgressDialog(MainActivity.this);
                        dialog.setTitle("로드중입니다.");
                        dialog.show();
                    }

                    @Override
                    protected Boolean doInBackground(String... params) {
                        String ret = client.getPassData(idCode);
                        Log.i("d", ret);
                        data = ret.split("\n");
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        dialog.dismiss();
                        if(data.length>1) {
                            id.setText("군번 : " + data[1]);
                            affiliation.setText("소속부대 : " + data[2]);
                            name.setText(data[3]);
                        }else {
                            Toast toast=Toast.makeText(MainActivity.this, "서버와 연결이 실패했습니다.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                new LoadPros().execute();
            }
            else {
                Toast toast=Toast.makeText(getApplication(), "바코드를 다시 인식해 주세요", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }
    //----------------------------------------------------------------------------------------------
}

