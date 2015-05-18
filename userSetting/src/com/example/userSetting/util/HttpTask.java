package com.example.userSetting.util;

import android.util.Log;
import com.example.userSetting.main.DumpMessage;
import com.example.userSetting.user.IMsgBack;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * create time 2015/1/15
 * Created by Administrator on 2015/1/17.
 */
public class HttpTask extends AbstractTask {
    private String urlString;
    private IMsgBack con;
    private int msgType;

    public HttpTask(String urlString,int msgType, IMsgBack con) {
        this.urlString = urlString;
        this.con = con;
        this.msgType = msgType;
    }

    @Override
    public void doInBackground() {
        Long nowTime = System.currentTimeMillis();
        URL url = null;
        try {
            url = new URL(urlString);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            try{
                request.setURI(url.toURI());
                HttpResponse response ;

                try{
                    response = client.execute(request);
                    assert response != null;
                    InputStreamReader ins;
                    ins = new InputStreamReader(response.getEntity().getContent(), "utf-8");
                    BufferedReader br = new BufferedReader(ins);
                    String line;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    Log.i("HttpTask----->",stringBuffer.toString());
                    DumpMessage.getInstance().dispatch(new AppMessage(stringBuffer.toString(),msgType));

                    Log.i("httpTask wasteTime ---->",System.currentTimeMillis()-nowTime+"");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }catch (URISyntaxException e){
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




    }
}
