package com.example.europroject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.europroject.models.Coin;

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_coin_detail extends AppCompatActivity {

    private CircleImageView image;
    private  TextView taglio;
    private  TextView paese;
    private  TextView anno;
    private  TextView materiale;
    private  Coin coin;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_coin_detail);
        actionBar= getSupportActionBar();
        actionBar.setTitle("Dettaglio moneta");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#574e66")));
        image = (CircleImageView) findViewById(R.id.imageDetail);
        taglio= (TextView) findViewById(R.id.taglioDetail);
        paese= (TextView) findViewById(R.id.paeseDetail);
        anno= (TextView) findViewById(R.id.annoDetail);
        materiale= (TextView) findViewById(R.id.materialeDetail);

        coin= getIntent().getParcelableExtra("coin");

        image.setImageURI( Uri.parse(coin.getImageUri()));
        taglio.append(""+coin.getTaglio());
        paese.append(""+coin.getPaese());
        anno.append(""+coin.getAnno());
        materiale.append(""+coin.getMateriale());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}