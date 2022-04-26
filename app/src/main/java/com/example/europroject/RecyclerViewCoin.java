package com.example.europroject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.europroject.models.Coin;
import com.example.europroject.models.Coin_Dao_DB_imp;
import com.example.europroject.models.Collection;
import com.example.europroject.models.Collection_DAO_DB_imp;
import com.example.europroject.utils.CoinAdapter;
import com.example.europroject.utils.CollectionAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewCoin extends Fragment {

    private List<Coin> coinList = new ArrayList<Coin>();
    private CoinAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    private Coin_Dao_DB_imp dao = new Coin_Dao_DB_imp();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dao = new Coin_Dao_DB_imp();
        coinList.addAll(getArguments().getParcelableArrayList("arraylistCoin"));

      //  Log.i("Test","Il paese della prima coin è: ****** "+coinList.get(0).getPaese()+"\n Il materiale è: ******* "+coinList.get(0).getMateriale());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view_coin, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recyclerCoin);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CoinAdapter(coinList);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

        // Inflate the layout for this fragment

        //Avvio activity per visulizzare i dettagli
        mAdapter.setOnClickListener(new CoinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent i = new Intent(getActivity(), Show_coin_detail.class);
                i.putExtra("coin",  coinList.get(pos));
                startActivity(i);

         //       Toast.makeText(getActivity(), "Selected position: " + (pos + 1), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int pos) {
              //  Toast.makeText(getActivity(), "Selected position: " + (pos + 1), Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(getActivity())
                        .setTitle("Conferma eliminazione")
                        .setMessage("Sei sicuro di voler eliminare la moneta selezionata?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                removeItem(pos);
                            }})
                        .setNegativeButton("No", null).show();


            }

        });

        return view;
    }


         private void removeItem(int pos) {
             dao.open();
             Coin c = (Coin) coinList.get(pos);
             // Path p1 = Paths.get(coin.getImageUri());
             Uri myUri= Uri.parse(c.getImageUri());

             File fdelete = new File(myUri.getPath());
             if(fdelete.exists()) {
                 if(fdelete.delete()){
                     Log.i("Test","E' stato eliminato il file con percorso: "+c.getImageUri());
                 } else{ Log.i("Error","FIle non eliminato, di percorso: " +c.getImageUri());  }
             } else{ Log.i("Error","FIle non trovato, di percorso: " +c.getImageUri());  }



             dao.deleteCoin(c);
             dao.close();
             coinList.remove(c);

             //  coinList.clear();
             mAdapter.notifyItemRemoved(pos);
            if(coinList.isEmpty()){
                getActivity().finish();
                Toast.makeText(getActivity(), "Nessun elemento da visualizzare" , Toast.LENGTH_SHORT).show();
            }

       /*          if(coinList.size()==1){
                     dao.deleteCoin(c);
                 //    dao.close();
                     coinList.remove(c);
                     coinList.clear();
                     mAdapter.notifyItemRemoved(pos);
                     FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                     EmptyFragment emptyFragment;
                     emptyFragment= new EmptyFragment();
                     FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.recyclerCoin,emptyFragment);
                     fragmentTransaction.commit();
             }*/
         }


}