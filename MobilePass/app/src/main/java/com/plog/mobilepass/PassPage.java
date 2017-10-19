package com.plog.mobilepass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Administrator on 2017-10-17.
 */

public class PassPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passmain);

        // 로그인 액티비에서 db값을 받아온다
        Intent tn=this.getIntent();
        String[] str = tn.getStringArrayExtra("passdata");
        String[] rankText = {"면회객","군무원","일반인","군무원","일반인","하사","중사","상사","원사","준위",
                "소위","중위","대위","소령","중령","대령","준장","소장","중장","대장","원수"};
        final String IdCode       = str[1];
        String ID           = str[2];
        String Rank         = str[3];
        String aType        = str[4];
        String affiliation  = str[5];
        String aCode        = str[6];
        String pName        = str[7];
        String ImgSrc       = str[8];
        Log.i("d",ImgSrc);

        //----------------------------------------------------------------------------------------
        // 파일 다운로드
        FileDownloader fd = new FileDownloader(getApplicationContext());
        try {
            fd.downFile("http://10.53.128.141:5010/image/"+ImgSrc, ImgSrc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView image = (ImageView)findViewById(R.id.imageView);
        String img_path = getApplicationContext().getFilesDir().getPath() + "/" + ImgSrc;
        File img_load_path = new File(img_path);
        if(img_load_path.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(img_path);
            image.setImageBitmap(bitmap);
        }
        //----------------------------------------------------------------------------------------

        String packName = this.getPackageName(); // 패키지명

        // 소속 이미지 변경
        ImageView aTypeView = (ImageView) findViewById(R.id.imageView2);
        if("육군".equals(aType))
            aTypeView.setImageResource(getResources().getIdentifier("@drawable/ar", "drawable", packName));
        else if("공군".equals(aType))
            aTypeView.setImageResource(getResources().getIdentifier("@drawable/af", "drawable", packName));
        else if("해군".equals(aType))
            aTypeView.setImageResource(getResources().getIdentifier("@drawable/na", "drawable", packName));

        // 계급 이미지 변경
        ImageView rankView = (ImageView) findViewById(R.id.imageView3);
        rankView.setImageResource(getResources().getIdentifier("@drawable/a"+Rank, "drawable", packName));


        TextView id = (TextView)findViewById(R.id.passmain_id);
        TextView name = (TextView)findViewById(R.id.passmain_name);

        Button passlog = (Button)findViewById(R.id.passmain_passlog);
        passlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassPage.this,PassLogActivity.class);
                intent.putExtra("idcode",IdCode);
                startActivity(intent);
            }
        });

        String temp="err";
        if(ID.length()>3)
            temp = ID.substring(0,2)+"-"+ID.substring(2,ID.length());
        id.setText(temp);
        name.setText(rankText[Integer.valueOf(Rank)] + " " + pName);

        TextView bacode = (TextView)findViewById(R.id.bacode);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/numtob.TTF");
        bacode.setTypeface(typeFace);
        bacode.setText("*"+IdCode+"*");
    }
}
