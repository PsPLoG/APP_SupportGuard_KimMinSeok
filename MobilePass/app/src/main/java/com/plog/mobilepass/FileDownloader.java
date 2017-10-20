package com.plog.mobilepass;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;

public class FileDownloader {
    private final Context context;

    public FileDownloader(Context context) {
        this.context = context;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();
    public void downFile(String fileUrl, String fileName) {
        final File filePath = new File(context.getFilesDir().getPath() + "/" + fileName);
            client.get(fileUrl, new FileAsyncHttpResponseHandler(context) {

                @Override
                public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                    Log.i("test", "실패함 ㅠ");
                }

                @Override
                public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, File file) {
                    Log.i("test", "responsePath: " + file.getAbsolutePath());
                    Log.i("test", "originalPath: " + filePath.getAbsolutePath());
                    file.renameTo(filePath);
                }
            });
    }
}