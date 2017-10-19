package com.plog.mobilepassguard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017-10-19.
 */

public class PassListAdater extends BaseAdapter {
    ArrayList<PassLogData> list;
    LayoutInflater inflater;

    PassListAdater(ArrayList<PassLogData> list) {
        this.list = list;
        Log.i("adapter", list.get(0).passTime);
        //this.inflater=inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            //convertView=inflater.inflate(R.layout.activity_pass_list,null);
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_passlog, parent, false);
        }

        TextView passlogName = convertView.findViewById(R.id.passlog_name);
        TextView passlogTime = convertView.findViewById(R.id.passlog_time);
        TextView passlogPlace = convertView.findViewById(R.id.passlog_place);
        final TextView passlogBt = convertView.findViewById(R.id.passlog_bt);
        Log.i("adapter", position + "posit");

        passlogName.setText(list.get(position).passName);
        passlogTime.setText("" + list.get(position).passTime);
        passlogPlace.setText("" + list.get(position).passPlace);
        if (list.get(position).passType == 2) {
            passlogBt.setEnabled(false);
        }
        passlogBt.setOnClickListener(new View.OnClickListener() {
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
                        dialog = new ProgressDialog(parent.getContext());
                        dialog.setTitle("로드중입니다.");
                        dialog.show();
                    }
                    @Override
                    protected Boolean doInBackground(String... params) {
                        sucpoc = MainActivity.client.getJSON("퇴영",list.get(position).passNum+"","","");
                        return false;
                    }
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        dialog.dismiss();
                        if(sucpoc) {
                            passlogBt.setEnabled(false);
                            Toast toast=Toast.makeText(parent.getContext(), "성공적으로 퇴영 처리되었습니다.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            Toast toast=Toast.makeText(parent.getContext(), "실패했습니다 인터넷 연결상태를 확인해주세요", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                new LoadPros().execute();
            }
        });
        return convertView;
    }// --
}

class PassLogData {
    String passName;
    String passTime;
    String passPlace;
    int passType;
    int passNum;

}

