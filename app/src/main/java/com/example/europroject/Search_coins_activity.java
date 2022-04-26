package com.example.europroject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.europroject.models.Coin;
import com.example.europroject.models.Coin_Dao_DB_imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Search_coins_activity extends AppCompatActivity {

    // Creo  la lista delle monete in base al tipo di ricerca
    private  Spinner spinner;
    private Intent mainCoin;
    private String type;
    private long idColl=-1;
    private Coin_Dao_DB_imp dao= new Coin_Dao_DB_imp();
    private List<Coin> coinList= new ArrayList<Coin>();
    private String[] listaOpzioni;
    private FragmentManager fragmentManager;
    private RecyclerViewCoin fragmentListCoins;
    private EmptyFragment emptyFragment;
    private String t_type;
    private String STATE_ROTATION;
    private String statoRotazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coins);
        mainCoin= getIntent();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Collezione");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#574e66")));
        fragmentManager= getSupportFragmentManager();


        try {
            idColl = Long.parseLong(mainCoin.getStringExtra("id_coll2"));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //*********
        t_type = new String();
        type=new String();
        t_type=mainCoin.getStringExtra("type");
        type= t_type;

        if (savedInstanceState != null) {

            statoRotazione = savedInstanceState.getString(STATE_ROTATION);
        } else {

            statoRotazione = "NO";
        }

        if(statoRotazione!="ESEGUITO") {

            if (!type.equals("tutte")) {
                launchDialogSearch();
            } else {
                dao.open();
                coinList = dao.getAllCoin(idColl);
                dao.close();
                setFragment();
            }
        }
    }
    //Icona search nel menu, se siamo in "tutte" non viene visualizzato
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_coins,menu);

        mainCoin= getIntent();
        type=mainCoin.getStringExtra("type");
        boolean r;
        if(type.equals("tutte")) r= false;
        else r=true;
        return r;
    }
    //Lancio lo spinner di riceca quando premo l'icona
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.searchItem){
            launchDialogSearch();
            return true;
        }else {
            return false;
        }
    }






    public void setFragment(){
        if(coinList.size() != 0){
            fragmentListCoins= new RecyclerViewCoin();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayoutForCoins,fragmentListCoins,"LIST_FRAG");
            ArrayList<Coin> arrayListCoin= new ArrayList<Coin>();

            if(!arrayListCoin.isEmpty()){
                arrayListCoin.clear();
            }
            arrayListCoin.addAll(coinList);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("arraylistCoin", arrayListCoin);
            fragmentListCoins.setArguments(bundle);
            fragmentTransaction.commit();
        }else{
            emptyFragment= new EmptyFragment();
            Fragment fragmentList = fragmentManager.findFragmentByTag("LIST_FRAG");
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            if(fragmentList==null){
                fragmentTransaction.add(R.id.frameLayoutForCoins,emptyFragment);
            }else {
                fragmentTransaction.replace(R.id.frameLayoutForCoins, emptyFragment);
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    public void launchDialogSearch(){

        //*********** Riempio la lista opzioni dello spinner in base al tipo di ricerca che voglio fare ***************

        type= mainCoin.getStringExtra("type");
        switch (type){
            case "perPaese":
                listaOpzioni=getResources().getStringArray(R.array.spinner_paesi);
                break;
            case "perAnno":
                listaOpzioni=getResources().getStringArray(R.array.spinner_anno);
                break;
            case "perTaglio":
                listaOpzioni=getResources().getStringArray(R.array.spinner_taglio);
                break;
            default:
                break;
        }

        //********************************* INITIAL POPUP *******************************************

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Search_coins_activity.this);
        View mView= getLayoutInflater().inflate(R.layout.dialog_spinner,null);
        mBuilder.setTitle("Scegli: ");


        spinner = (Spinner) mView.findViewById(R.id.spinnerSearchCoins);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaOpzioni);
        spinner.setAdapter(spinnerArrayAdapter);

        //******* Riempio l'arrraylist coinlist con i risultati della query eseguita **********

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spinner.getSelectedItem().toString();
                dao.open();
                switch (type){
                    case "perPaese":
                        coinList=dao.getCoinsForCountry(spinner.getSelectedItem().toString(),idColl);
                        break;
                    case "perAnno":
                        coinList=dao.getCoinsForYear(Integer.parseInt(spinner.getSelectedItem().toString()),idColl);
                        break;
                    case "perTaglio":
                        coinList=dao.getCoinsForPiece(Float.parseFloat(spinner.getSelectedItem().toString()),idColl);
                        break;
                    default:
                        break;
                }
                dao.close();
                setFragment();
            }
        });


        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog=mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.lightgrey);
        dialog.show();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_ROTATION, "ESEGUITO");
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

}