package com.example.ehu.amazon_wishlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class WishlistRegistration extends AppCompatActivity {

    private EditText urlEditText;
    private String TAG = "WishlistRegistration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_registration);

        urlEditText = findViewById(R.id.URLEditText);
        String json = getJson(urlEditText.getText().toString());
        Log.d(TAG, json);

    }

    public String getJson(String planUrl) {
        URL reqestUrl = null;
        HttpURLConnection connection = null;
        try {
            reqestUrl =
                    new URL("http://ryouchi.usamimi.info/expandurl/index.php?callback=getOrgURL&url=" + planUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "不正なURLです。", Toast.LENGTH_SHORT).show();
        }
        try {
            connection = (HttpURLConnection) reqestUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "接続先が存在しません", Toast.LENGTH_SHORT).show();
        }
        //接続タイムアウトを設定する。
        connection.setConnectTimeout(100000);
        //レスポンスデータ読み取りタイムアウトを設定する。
        connection.setReadTimeout(100000);
        //ヘッダーにUser-Agentを設定する。
        connection.setRequestProperty("User-Agent", "Android");
        //ヘッダーにAccept-Languageを設定する。
        connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
        //ヘッダーにContent-Typeを設定する
        connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
        //HTTPのメソッドをPOSTに設定する。
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.d(TAG, "methodが指定できません");
        }
        //リクエストのボディ送信を許可する
        connection.setDoOutput(true);
        //レスポンスのボディ受信を許可する
        connection.setDoInput(true);

        OutputStream outputStream = null;
        try {
            connection.connect();
            outputStream = connection.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toString();
    }

}
