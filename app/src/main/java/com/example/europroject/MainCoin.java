package com.example.europroject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.europroject.models.Coin_Dao_DB_imp;
import com.example.europroject.views.AddCoin;
import com.example.europroject.views.ViewCoins;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainCoin extends AppCompatActivity {

    private ActionBar actionBar;
    private Intent intentPrev;
    private String readIdColl,readNomeColl;
    private String nomeColl=null;
    private String idColl=null;
    private TextView display;
    private AppCompatButton tutte, perAnno,perPaese, perTaglio;
  //private String nomeColl = getIntent().getStringExtra("name_coll");
    private final static String KEY_ID_COLL="Key id";
    private final static String KEY_NOME_COLL="Key nome";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_coin);

        actionBar= getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#574e66")));
        actionBar.setTitle("Collezione");

        //Salvo gli extra in 2 variabili per mantenerli salvati quando l'activity verrà richiamata
        idColl = readIdColl;
        nomeColl = readNomeColl;

         // read
         prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         idColl = prefs.getString("idColl","");//"" is the default value.
         nomeColl = prefs.getString("nomeColl",""); //"" is the default value.


        display=(TextView) findViewById(R.id.textViewManicoin);
        display.setText(nomeColl+"\n N° di monete: "+""+getCoinInCollection(Long.parseLong(idColl)));
        FloatingActionButton toAddCoin= (FloatingActionButton) findViewById(R.id.toaddcoin);

        tutte= findViewById(R.id.buttonAllCoin);
        perAnno= findViewById(R.id.buttonPerAnno);
        perPaese= findViewById(R.id.buttonPerPaese);
        perTaglio=findViewById(R.id.buttonPerTaglio);

        //******* filtri onclick per cercare le varie monete *******

        perPaese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(), Search_coins_activity.class);
                i.putExtra("id_coll2", ""+idColl);
                i.putExtra("type", "perPaese");
                startActivity(i);
            }
        });

        perTaglio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(), Search_coins_activity.class);
                i.putExtra("id_coll2", ""+idColl);
                i.putExtra("type", "perTaglio");
                startActivity(i);
            }
        });

        perAnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(), Search_coins_activity.class);
                i.putExtra("id_coll2", ""+idColl);
                i.putExtra("type", "perAnno");
                startActivity(i);
            }
        });

        tutte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(), Search_coins_activity.class);
                i.putExtra("id_coll2", ""+idColl);
                i.putExtra("type", "tutte");
                startActivity(i);
            }
        });
        //*********************************** ********************************//

        //Aggiungo una nuova moneta
        toAddCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCoin= new Intent(v.getContext(), AddCoin.class);
                toCoin.putExtra("id_coll2", ""+idColl);
                startActivity(toCoin);

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private int getCoinInCollection(long id){
        int count=0;
        Coin_Dao_DB_imp dao= new Coin_Dao_DB_imp();
        dao.open();
        count= dao.getCount(id);
        dao.close();
        return count;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
      //  outState.putString(KEY_ID_COLL,idColl);
     //   outState.putString(KEY_NOME_COLL,nomeColl);
        super.onSaveInstanceState(outState);
    }


    //**** Per aggiornare in numero di monete se cancellate nell'activity successiva ***********
    @Override
    public void onRestart()
    {
        super.onRestart();
        display=(TextView) findViewById(R.id.textViewManicoin);
        display.setText(nomeColl+"\n N° di monete: "+""+getCoinInCollection(Long.parseLong(idColl)));

    }


    // this event will enable the back
    // function to the button on press
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