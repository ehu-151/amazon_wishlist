package com.example.ehu.amazon_wishlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    public ListView mListView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mListView = (ListView) view.findViewById(R.id.listView);
        //itemをタップした時、編集する
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("link", "yobidasi" + position);
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        Toast.makeText(getContext(), "無効です", Toast.LENGTH_SHORT).show();
                }
            }
        });


        SimpleAdapter adapter = new SimpleAdapter(getContext(), getListData(), R.layout.list,
                new String[]{"no", "name"}, new int[]{R.id.image, R.id.text});
        mListView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    public List<Map<String, String>> getListData() {
        List<Map<String, String>> listData = new ArrayList<Map<String, String>>();

        listData.add(getMapData(new String[][]{{"no", "ポプテピピック vol.1(Blu-ray)"}, {"name", "￥5,999"}}));
        listData.add(getMapData(new String[][]{{"no", "ポプテピピック vol.2(Blu-ray)"}, {"name", "￥5,458"}}));
        listData.add(getMapData(new String[][]{{"no", "ポプテピピック vol.3（Blu-ray）"}, {"name", "￥5,458"}}));

        return listData;
    }

    //    public void  getImage(String url){
//        ImageView imageView = (ImageView)findViewById(R.id.imageView);
//
//        Uri uri = Uri.parse(url);
//        Uri.Builder builder = uri.buildUpon();
//        AsyncTaskHttpRequest task = new AsyncTaskHttpRequest(imageView);
//        task.execute(builder);
//    }
    private Map<String, String> getMapData(String[][] values) {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < values.length; i++) {
            map.put(values[i][0], values[i][1]);
        }

        return map;
    }
}
