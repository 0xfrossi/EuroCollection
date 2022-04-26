package com.example.europroject.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.europroject.R;
import com.example.europroject.models.Collection;

import java.util.List;

public class CollectionListAdapter extends ArrayAdapter<Collection> {


    public CollectionListAdapter(@NonNull Context context,@NonNull List<Collection> collections) {
        super(context, R.layout.item_collection, collections);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
