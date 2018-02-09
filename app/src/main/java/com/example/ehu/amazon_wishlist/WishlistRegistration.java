package com.example.ehu.amazon_wishlist;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class WishlistRegistration extends Fragment implements View.OnClickListener {
    InputStream in = null; // URL連携した戻り値を取得して保持する用
    private EditText urlEditText;
    private String TAG = "WishlistRegistration";
    private Button submit;
    public EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wishlist_registration, container, false);
        editText = rootView.findViewById(R.id.URLEditText);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "submit");
        switch (view.getId()) {
            case R.id.submit:

                final String url = editText.getText().toString();

                //postする
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //URLのチェック：有効の判断
//                        Log.d(TAG,checkUrl(url));
                        if (sendPost(url)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "POST成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
        }
    }

    private boolean sendPost(String parameter) {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        try {

            URL url = new URL("http://192.168.11.34/hackason/wishlistregist.php");
            con = (HttpURLConnection) url.openConnection();
            // HTTPリクエストコード
            con.setRequestMethod("POST");
            // POSTパラメータ
            String postDataSample = "hoge=" + parameter;
            con.setDoOutput(true);// POSTによるデータ送信を可能にする

            // POSTパラメータを設定（方法１） OutputStreamを利用
            OutputStream out = con.getOutputStream();
            out.write(postDataSample.getBytes());
            out.flush();
            out.close();
            // データを取得
            // えぇ～！！なんかgoogleにたいしてpostするとinputStreamが取れないくさい。。。なので、RESPONSEコードをToast表示
//            Toast.makeText(this, Integer.toString(con.getResponseCode()), Toast.LENGTH_SHORT).show(); // -->405(Method not allowed)であったため、inputStreamが取得出来なかったみたい。
            in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            // InputStreamからbyteデータを取得するための変数
            StringBuffer bufStr = new StringBuffer();
            String temp = null;

            // InputStreamからのデータを文字列として取得する
            while ((temp = br.readLine()) != null) {
                bufStr.append(temp);
            }

            // 結果をテキストビューに設定
//            resultTv.setText(bufStr.toString());
            if (bufStr.toString() == "done1") {
                return true;
            }
            Log.d(TAG, bufStr.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "IOException");
        }
        return false;
    }


    public String checkUrl(String planUrl) {
        URL reqestUrl = null;
        HttpURLConnection connection = null;
        try {
            reqestUrl =
                    new URL("http://ryouchi.usamimi.info/expandurl/index.php?callback=getOrgURL&url=" + planUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "不正なURLです。", Toast.LENGTH_SHORT).show();
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
