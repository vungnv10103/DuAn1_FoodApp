package com.fpoly.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;

import java.util.List;

public class GridProductHiddenAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    ImageView imgZoomIn;
    static RecommendDAO recommendDAO;
    private List<ItemRecommend> list;
    private List<ItemRecommend> listOld;
    ItemRecommend item;

    public GridProductHiddenAdapter(Context context, int layout, List<ItemRecommend> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        logoViewHolder logoViewHolder = null;
        if(logoViewHolder == null){
            logoViewHolder = new logoViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            convertView.setTag(logoViewHolder);
        }else {
            logoViewHolder = (GridProductHiddenAdapter.logoViewHolder) convertView.getTag();
        }
        return convertView;
    }
    public static class logoViewHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
