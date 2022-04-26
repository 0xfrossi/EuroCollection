package com.example.europroject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.europroject.MainActivity;
import com.example.europroject.R;
import com.example.europroject.models.Collection;
import com.example.europroject.models.Collection_DAO_DB_imp;

public class AddCollection extends AppCompatActivity {

 private Spinner spinnner, tipo;
 private Collection_DAO_DB_imp collectionDao;
 private EditText nome, note;
 private String notaInserita;
 private  ActionBar actionBar;
 private  Button save;
 private static final String KEY_NAME_COLL="Key name";
 private static final String KEY_TYPE_COLL_POSITION="Key item";
 private static final String KEY_NOTE_COLL="Key item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        //***** Sistemo action bar ********
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Crea una nuova collezione");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#574e66")));

        //****** Inizializzo gli elementi ********
        spinnner= findViewById(R.id.spinnerTipoCollezione);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner_db_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnner.setAdapter(adapter);
        nome= (EditText) findViewById(R.id.editNomeCollezione);
        note= (EditText) findViewById(R.id.editTextNote);
        tipo= (Spinner) findViewById(R.id.spinnerTipoCollezione);
        save = (Button) findViewById(R.id.buttonCreaCollezione);


        //************** Schiaccio il pulsante che mi porta a creare e salvare la collezione nel db ****************

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  tipo.getSelectedItem().toString();
               collectionDao= new Collection_DAO_DB_imp();
               if (note.getText().toString().equals("")){
                    notaInserita="Nessuna nota inserita";
               }else {
                    notaInserita=note.getText().toString().trim();
               }

                //*********** Verifico che i campi da compilare siano stati compilati ***************************************

                int duration = Toast.LENGTH_SHORT;
                if(tipo.getSelectedItem().toString().equals("-") || nome.getText().toString().trim().equals("")){

                    Toast toast = Toast.makeText(getApplicationContext(), "Completa tutti i campi", duration);
                    toast.show();

                }else{
                    collectionDao.open();
                    collectionDao.insertCollection(new Collection(nome.getText().toString().trim(), tipo.getSelectedItem().toString().trim(), notaInserita));
                    collectionDao.close();

                    Toast toast = Toast.makeText(getApplicationContext() , "Collezione creata con successo!", duration);
                    toast.show();
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

    }

}