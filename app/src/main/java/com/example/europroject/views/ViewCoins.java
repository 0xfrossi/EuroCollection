package com.example.europroject.views;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.europroject.R;

public class ViewCoins extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coins);

        mTextView = (TextView) findViewById(R.id.text);


    }
}