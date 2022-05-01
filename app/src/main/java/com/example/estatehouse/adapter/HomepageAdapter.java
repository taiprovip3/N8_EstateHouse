package com.example.estatehouse.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.estatehouse.R;
import com.example.estatehouse.entity.House;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        TextView hpTypeView = view.findViewById(R.id.hp_Type);
        TextView hpSaleView = view.findViewById(R.id.hp_Sale);
        TextView hpTagView = view.findViewById(R.id.hp_Tag);
        TextView hpCostView = view.findViewById(R.id.hp_Cost);

        House h = houses.get(i);
//        String hpImage = h.getImage();
        String hpType = h.getType();
        String hpSale = ""+h.getSale();
        String strTags = "";
        for (String s : h.getTags()){
            strTags += s + ", ";
        }
        String hpCost = "" + h.getCost();
//        Context contextImageView = hpImageView.getContext();
//        int id = contextImageView.getResources().getIdentifier(hpImage, "drawable", contextImageView.getPackageName());
//        hpImageView.setImageResource(id);
        hpTypeView.setText(hpType);
        hpSaleView.setText("-"+hpSale+"%");
        hpTagView.setText(strTags);
        hpCostView.setText("$"+hpCost);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://estatehouse-4ee84.appspot.com");
        StorageReference imageReference = storageReference.child("images/"+h.getImage());
        imageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get()
                                .load(uri)
                                .into(hpImageView);
                    }
                });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
