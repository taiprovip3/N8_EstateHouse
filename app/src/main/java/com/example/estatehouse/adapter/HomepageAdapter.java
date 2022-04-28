package com.example.estatehouse.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.estatehouse.R;
import com.example.estatehouse.entity.House;

import java.util.List;
import java.util.StringJoiner;

public class HomepageAdapter extends BaseAdapter {

    private List<House> houses;
    private Context context;

    public HomepageAdapter(List<House> houses, Context context){
        this.houses = houses;
        this.context = context;
    }
    @Override
    public int getCount() {
        return houses.size();
    }

    @Override
    public Object getItem(int i) {
        return houses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if( view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_homepage_itemview, viewGroup, false);
        }

        ImageView hpImageView = view.findViewById(R.id.hp_Image);
        TextView hpSaleView = view.findViewById(R.id.hp_Sale);
        TextView hpTagView = view.findViewById(R.id.hp_Tag);
        TextView hpCostView = view.findViewById(R.id.hp_Cost);

        House h = houses.get(i);
        int hpImage = h.getImage();
        String hpSale = ""+h.getSale();
//        StringJoiner sj = new StringJoiner(", ", "", "");
//        for (String tag : h.getTags()) {
//            sj.add(tag);
//        }
        String hpCost = ""+h.getCost();
        hpImageView.setImageResource(hpImage);
        hpSaleView.setText("-"+hpSale+"%");
        hpCostView.setText("$"+hpCost+" (USD)");

        return view;
    }
}
