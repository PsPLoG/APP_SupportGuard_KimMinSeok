package com.plog.mobilepass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-16.
 */

class  PassLogAdapter extends BaseAdapter
{
    ArrayList<PassLogData> list;
    LayoutInflater inflater;
    //ProductAdapter(LayoutInflater inflater, ArrayList<ProductData> list)
    PassLogAdapter( ArrayList<PassLogData> list)
    {
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            //convertView=inflater.inflate(R.layout.productlist,null);
            final Context context = parent.getContext();
            if( inflater == null)
            {
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_passlog,parent,false);
        }

        TextView passlogType = convertView.findViewById(R.id.passlog_type);
        TextView passlogTime = convertView.findViewById(R.id.passlog_time);
        TextView passlogPlace = convertView.findViewById(R.id.passlog_place);

        if(list.get(position).passType.equals("2"))
            passlogType.setText("퇴영완료");
        else
            passlogType.setText("입영중");
        passlogTime.setText(""+list.get(position).passTime);
        passlogPlace.setText("부대코드 : "+list.get(position).passPlace);
        return convertView;
    }
}