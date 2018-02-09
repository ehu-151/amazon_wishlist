package com.example.ehu.amazon_wishlist;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by kaikoro on 2018/02/09.
 */

public class ProductListAdapter extends SimpleAdapter {
    public ProductListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }


}
