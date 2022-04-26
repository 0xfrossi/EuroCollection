package com.example.europroject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.europroject.models.Collection;
import com.example.europroject.models.Collection_DAO_DB_imp;
import com.example.europroject.views.AddCollection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nome,  note;
    Spinner tipo;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        actionBar.setTitle("Euro Collection");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#574e66")));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddCollection.class);
                view.getContext().startActivity(intent);
            }
        });

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentListaCollezioni fragmentListView;
        EmptyFragment emptyFragment;
        Collection_DAO_DB_imp dao= new Collection_DAO_DB_imp();
        dao.open();
        List<Collection> collectionList= dao.getAllCollection();
        dao.close();
        // COntrolla se esistono ancora collezioni, se nn esistono il fragment viene rimpiazzato con l'empty fragment
       if(collectionList.size() != 0){


            fragmentListView= new FragmentListaCollezioni();

            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayout,fragmentListView);
            fragmentTransaction.commit();
      }else{
            emptyFragment= new EmptyFragment();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayout,emptyFragment);
            fragmentTransaction.commit();

        }
    }


}