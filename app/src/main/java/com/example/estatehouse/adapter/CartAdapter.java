package com.example.estatehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.estatehouse.R;
import com.example.estatehouse.entity.HouseCart;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private List<HouseCart> houseCarts;
    private Context context;

    public CartAdapter(List<HouseCart> houseCarts, Context context){
        this.houseCarts = houseCarts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return houseCarts.size();
    }

    @Override
    public Object getItem(int i) {
        return houseCarts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.activity_cart_itemview, viewGroup, false);

        ImageView imageView = view.findViewById(R.id.cart_Image);
        TextView costView = view.findViewById(R.id.cart_Cost);
        TextView sellerView = view.findViewById(R.id.cart_Seller);
        TextView bedroomsView = view.findViewById(R.id.cart_Bedrooms);
        TextView bathroomsView = view.findViewById(R.id.cart_Bathrooms);
        TextView livingAreaView = view.findViewById(R.id.cart_Livingarea);

        HouseCart hc = houseCarts.get(i);
        imageView.setImageResource(hc.getImage());
        costView.setText("$"+hc.getPrice()+" (USD)");
        sellerView.setText(hc.getSeller());
        bedroomsView.setText(""+hc.getBedrooms());
        bathroomsView.setText(""+hc.getBathrooms());
        livingAreaView.setText(""+hc.getLivingarea());

        return view;
    }
}
