package com.example.europroject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.europroject.models.Coin;
import com.example.europroject.models.CoinDAO;
import com.example.europroject.models.Coin_Dao_DB_imp;
import com.example.europroject.models.Collection;
import com.example.europroject.models.CollectionDAO;
import com.example.europroject.models.Collection_DAO_DB_imp;
import com.example.europroject.utils.CollectionAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class FragmentListaCollezioni extends Fragment   {

    private List<Collection>  collectionList =new ArrayList<Collection>();
    private CollectionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    private Collection_DAO_DB_imp dao = new Collection_DAO_DB_imp();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences myShare;
    private CollectionDAO collectionDao;
    private String newNote;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dao.open();
        collectionList.addAll(dao.getAllCollection());
        dao.close();
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_first, container, false);
        mRecycler=  view.findViewById(R.id.listViewColl);
        mRecycler.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(getActivity());
        mAdapter= new CollectionAdapter(collectionList);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        myShare = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        mAdapter.setOnClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent i= new Intent(getActivity(), MainCoin.class);

               // SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                SharedPreferences.Editor editor= myShare.edit();
                editor.putString("idColl",  ""+collectionList.get(pos).getId());
                editor.putString("nomeColl", ""+collectionList.get(pos).getNome());
                editor.apply();

                   // i.putExtra("id_collection",""+collectionList.get(pos).getId());
                   //  i.putExtra("name_coll",""+collectionList.get(pos).getNome());
                startActivity(i);

            }

            @Override
            public void onDeleteClick(int pos) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Conferma eliminazione")
                        .setMessage("Eliminando la collezione eliminerai anche tutte le monente al suo interno, procedere?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                removeItem(pos); //rimuovo anche le coin contenute
                            }})
                        .setNegativeButton("No", null).show();

            }

            @Override
            public void onInfoClick(int pos) {
                showInfo(pos);
            }
        });

        return view;

    }

    private void showInfo(int pos){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Note").
                setMessage(collectionList.get(pos).getNota()).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }). setNeutralButton("Modifica",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //edit dialog
                                AlertDialog.Builder editdialog = new AlertDialog.Builder(getActivity());
                                editdialog.setTitle("Nuova nota");
                                final EditText edit = new EditText(getActivity());
                                edit.setInputType(InputType.TYPE_CLASS_TEXT);
                                editdialog.setView(edit);
                                Log.i("INFO","IL NOME E':   "+collectionList.get(pos).getNome() );

                                editdialog.setPositiveButton("Salva", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Collection editedColl = collectionList.get(pos);
                                        newNote = edit.getText().toString().trim();
                                       // collectionDao = new Collection_DAO_DB_imp();
                                        if (newNote.equals("")) {
                                            newNote = "Nessuna nota inserita";
                                        }
                                        dao.open();
                                        dao.editNote(editedColl,newNote);
                                        collectionList.clear();
                                        //mAdapter.notifyItemChanged(pos);
                                        collectionList.addAll(dao.getAllCollection());

                                        mAdapter.notifyItemChanged(pos);
                                       // getActivity().recreate();
                                        dao.close();
                                    }
                                });

                                editdialog.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }
                                );
                                editdialog.show();

                            }
        }).show();
    }

    private void removeItem(int pos){
        dao.open();
        Collection collection= (Collection) collectionList.get(pos);
        Log.i("INFO","IL NOME E':   "+collection.getNome()+ "ID:  "+collection.getId());

        dao.deleteCollection(collection);

        Coin_Dao_DB_imp coinDao = new Coin_Dao_DB_imp();
        coinDao.open();
        try {
            List<Coin> coinList= new ArrayList<Coin>();
            coinList.addAll(coinDao.getAllCoin(collection.getId()));
            if(coinList.size()!=0) {
                for (int i = 0; i < coinList.size(); i++) {
                    coinDao.deleteCoin(coinList.get(i));
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        finally {
            coinDao.close();
            collectionList.clear();
            collectionList.addAll(dao.getAllCollection());
            dao.close();
          mAdapter.notifyItemRemoved(pos);
          if(collectionList.size()==0){

              FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
              EmptyFragment emptyFragment;
              emptyFragment= new EmptyFragment();
              FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.listViewColl,emptyFragment);
              fragmentTransaction.commit();
          }
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onStart() {
        super.onStart();
    /*    try {
            collectionList.clear();

        }catch (NullPointerException e){

            e.printStackTrace();

        }finally {
            Collection_DAO_DB_imp dao = new Collection_DAO_DB_imp();
            dao.open();
            List<Collection> collectionList = dao.getAllCollection();
            dao.close();
            listViewColl.setAdapter(mAdapter);

        }*/
    }

}