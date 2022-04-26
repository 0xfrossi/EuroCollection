package com.example.europroject.utils;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.europroject.R;
import com.example.europroject.models.Coin;
import com.example.europroject.models.Collection;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoinAdapter  extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder>{

    private List<Coin> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int pos);
        void onDeleteClick(int pos);

    }
    public void setOnClickListener(OnItemClickListener l){
        mListener=l;
    }

    public CoinAdapter(List<Coin> c){
        mList=c;
    }
    public static class CoinViewHolder extends RecyclerView.ViewHolder {
        public TextView coinTaglio;
        public TextView coinPaese;
        public TextView coinAnno;
        public TextView coinMateriale;
        public ImageButton itemDelete;
        public CircleImageView itemImage;


        public CoinViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            coinTaglio = itemView.findViewById(R.id.taglioCoinItem);
         //   coinPaese = itemView.findViewById(R.id.paeseCoinItem);
          //  coinAnno = itemView.findViewById(R.id.annoCoinItem);
          //  coinMateriale = itemView.findViewById(R.id.materialeCoinItem);
            itemDelete = itemView.findViewById(R.id.coinDeleteItem);
            itemImage= itemView.findViewById(R.id.imageCoinItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {

                            listener.onItemClick(pos);
                        }
                    }
                }
            });

            itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAbsoluteAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });


        }

    }


    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin,parent,false);
        CoinViewHolder cvh= new CoinViewHolder(v, mListener);
        return  cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        Coin itemSelected= mList.get(position);
    //    holder.coinAnno.append(""+itemSelected.getAnno());
        holder.coinTaglio.append(""+itemSelected.getTaglio());
     //   holder.coinPaese.append(itemSelected.getPaese());
    //    holder.coinMateriale.append(itemSelected.getMateriale());
        holder.itemImage.setImageURI( Uri.parse(itemSelected.getImageUri()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
