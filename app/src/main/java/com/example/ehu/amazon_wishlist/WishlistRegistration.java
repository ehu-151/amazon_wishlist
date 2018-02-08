package com.example.ehu.amazon_wishlist;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

public class WishlistRegistration extends Fragment {

    private EditText urlEditText;
    private String TAG = "WishlistRegistration";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist_registration, container, false);
    }


    public String getJson(String planUrl) {
        URL reqestUrl = null;
        HttpURLConnection connection = null;
        try {
            reqestUrl =
                    new URL("http://ryouchi.usamimi.info/expandurl/index.php?callback=getOrgURL&url=" + planUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
//            Toast.makeText(this, "不正なURLです。", Toast.LENGTH_SHORT).show();
        }
        try {
            connection = (HttpURLConnection) reqestUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, "接続先が存在しません", Toast.LENGTH_SHORT).show();
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
