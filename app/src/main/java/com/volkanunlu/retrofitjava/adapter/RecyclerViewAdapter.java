package com.volkanunlu.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.volkanunlu.retrofitjava.R;
import com.volkanunlu.retrofitjava.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<CryptoModel> cryptoList;
    private String [] colors= {"#DFFF00","#40E0D0","#FFBF00","#FF7F50","#DE3163","#9FE2BF","#CCCCFF","#008080"};
    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) { //constructor metodumuz
        this.cryptoList = cryptoList;
    }


    public class RowHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);


        }

        public void bind(CryptoModel cryptoModel,String[] colors,Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position%8])); // color array 8 tane başa döndürsün diye.
            textName=itemView.findViewById(R.id.textName);
            textPrice=itemView.findViewById(R.id.textPrice);
            textName.setText(cryptoModel.currency);  //veri aktarımı id'sini al verisini ata.
            textPrice.setText(cryptoModel.price);  //veri aktarımı id'sini al verisini ata.
        }

    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //layout ile recycler view bağlama işlemi.
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {  //Görünümleri bağlama, hangi pozisyonda ne gözüksün

        holder.bind(cryptoList.get(position),colors,position);


    }

    @Override
    public int getItemCount() {  //kaç tane row oluşturulacak
        return cryptoList.size();
    }


}
