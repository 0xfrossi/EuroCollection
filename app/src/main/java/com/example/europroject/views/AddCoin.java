package com.example.europroject.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.europroject.MainActivity;
import com.example.europroject.MainCoin;
import com.example.europroject.R;
import com.example.europroject.models.Coin;
import com.example.europroject.models.Coin_Dao_DB_imp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class  AddCoin extends AppCompatActivity {

    private final static String KEY_MATERIALE="Key materiale";
    private final static String KEY_ANNO="Key anno";
    private final static String KEY_TAGLIO_POS="Key taglio";
    private final static String KEY_PAESE_POS="Key paese";
    private final static String KEY_URI="Key uri";

    private Spinner taglio, paese;
    private EditText materiale, anno;
    private CircleImageView imageView;

    private Button  add;
    private ActionBar actionBar;
    private Uri imageUri;
    private Coin_Dao_DB_imp coin_dao_db_imp;
    private FloatingActionButton addImageCoin;
    private double taglioValue;
    private String paeseValue;
    private String materialeValue;
    private  int annoValue;
    private  String uriString;
    private Uri savedUri;
    long idColl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar= getSupportActionBar();
        actionBar.setTitle("Aggiungi nuova moneta");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_add_coin);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#574e66")));
        Intent intent=getIntent();

        idColl= Long.parseLong(intent.getStringExtra("id_coll2"));



        add= findViewById(R.id.buttonAddCoin);
        addImageCoin= (FloatingActionButton) findViewById(R.id.addImageCoin);
        taglio= findViewById(R.id.spinnerTaglio);
        paese= findViewById(R.id.spinnerPaese);
        materiale= findViewById(R.id.editTextMateriale);
        anno= findViewById(R.id.editTextAnno);
        imageView= (CircleImageView) findViewById(R.id.imageViewCoin);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        if(savedInstanceState != null){
            //  String savedUriS= savedInstanceState.getString(KEY_URI);
            savedUri = (Uri) savedInstanceState.getParcelable(KEY_URI);
            //  Uri savedUri= Uri.parse(savedUriS);
            imageView.setImageURI(savedUri);
        }



        addImageCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imagePickDialog();

                ImagePicker.with(AddCoin.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                     /*  .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	*/ //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        //***** Aggiungi la moneta al db dopo aver peremuto il pulsante **********

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taglioValue= (double) Double.parseDouble(taglio.getSelectedItem().toString());
                paeseValue= paese.getSelectedItem().toString();
                materialeValue= materiale.getText().toString().trim();

                if(imageUri!= null){
                    uriString=imageUri.toString();
                }else{
                    uriString="";
                }

               try {
                   annoValue = (int) Integer.parseInt(anno.getText().toString());
                }
               catch (NumberFormatException e){
                    annoValue=0;
                }
                addCoin(taglioValue,materialeValue,paeseValue,annoValue,uriString,idColl);

            }
        });
    }

    //****** passa i dati per creare l'0ggetto coin, controlla che gli input siano corretti e salva l'oggetto come riga del db ************

    private void addCoin(double  taglio, String materiale, String paese, int anno,String uri,long idCollection){
        if(materiale.equals("") || paese.equals("") || uri.equals("") || anno==0|| (anno<1999 || anno > 2023)  ){
            Toast.makeText(this, R.string.erroreCampiCoin, Toast.LENGTH_SHORT).show();
        }else {

            coin_dao_db_imp = new Coin_Dao_DB_imp();
            coin_dao_db_imp.open();
            coin_dao_db_imp.insertCoin(new Coin(taglio, materiale, paese, anno, uri,idCollection));
            coin_dao_db_imp.close();

            Toast.makeText(this, R.string.addedCoin, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainCoin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }



    //**** Riceve il risultato della cattura dell'immagine
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       //immagine sarà ricevuta qua

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri non è nullo
            imageUri= data.getData();

            imageView.setImageURI(imageUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cattura cancellata", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
      //  outState.putString(KEY_URI,imageUri.toString());
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_URI, imageUri);

    }


}