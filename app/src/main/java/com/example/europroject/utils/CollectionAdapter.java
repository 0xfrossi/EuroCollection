package com.example.europroject.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.europroject.R;
import com.example.europroject.models.Collection;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<Collection> mListColl;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int pos);
        void onDeleteClick(int pos);
        void onInfoClick(int pos);
    }

    public void setOnClickListener(OnItemClickListener l){
        mListener=l;
    }
    public static class CollectionViewHolder extends RecyclerView.ViewHolder{
        public TextView itemCollNome;
        public TextView itemCollTipo;
        public ImageButton itemDelete;
        public ImageButton itemInfo;

        public CollectionViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            itemCollNome = itemView.findViewById(R.id.itemNomeColl);
            itemCollTipo= itemView.findViewById(R.id.itemTipoColl);
            itemDelete=itemView.findViewById(R.id.itemDeleteColl);
            itemInfo=itemView.findViewById(R.id.itemNoteColl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAbsoluteAdapterPosition();
                         if(pos!= RecyclerView.NO_POSITION){

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

            itemInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAbsoluteAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            listener.onInfoClick(pos);
                        }
                    }
                }
            });
        }
    }


    public CollectionAdapter(List<Collection> coll){
        mListColl=coll;
    }
    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,parent,false);
        CollectionViewHolder cvh= new CollectionViewHolder(v,mListener);
        return  cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        Collection currentItem= mListColl.get(position);
        holder.itemCollNome.setText(currentItem.getNome());
        holder.itemCollTipo.setText(currentItem.getTipo());

    }




    @Override
    public int getItemCount() {
        return mListColl.size();
    }
}
