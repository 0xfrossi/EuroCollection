package com.example.europroject.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Blob;

public class Coin implements Parcelable {

    private long id;
    private double taglio;
    private String materiale;
    private String paese;
    private int anno;
    private String imageUri;
    private long idCollection;        ;


    public Coin(long id,double taglio, String materiale, String paese, int anno, String imageUri,long idCollection){
        this.taglio=taglio;
        this.materiale=materiale;
        this.paese=paese;
        this.anno=anno;
        this.id=id;
        this.imageUri=imageUri;
        this.idCollection=idCollection;
    }
    public Coin(double taglio, String materiale, String paese, int anno, String imageUri, long idCollection){
        this.taglio=taglio;
        this.materiale=materiale;
        this.paese=paese;
        this.anno=anno;
        this.imageUri=imageUri;
        this.idCollection=idCollection;
    }

   /* public Coin(double taglio, String materiale, String paese, int anno, String imageUri){
        this.taglio=taglio;
        this.materiale=materiale;
        this.paese=paese;
        this.anno=anno;
        this.id=-1; //means not in DB
        this.imageUri=imageUri;
    }   */


    protected Coin(Parcel in) {
        id = in.readLong();
        taglio = in.readDouble();
        materiale = in.readString();
        paese = in.readString();
        anno = in.readInt();
        imageUri = in.readString();
        idCollection = in.readLong();
    }

    public static final Creator<Coin> CREATOR = new Creator<Coin>() {
        @Override
        public Coin createFromParcel(Parcel in) {
            return new Coin(in);
        }

        @Override
        public Coin[] newArray(int size) {
            return new Coin[size];
        }
    };

    //Getters
    public double getTaglio() {
        return taglio;
    }

    public String getMateriale() {
        return materiale;
    }

    public String getPaese() {
        return paese;
    }

    public int getAnno() {
        return anno;
    }

    public long getId() {
        return id;
    }

    public long getIdCollection() {
        return idCollection;
    }

    public String getImageUri() {
        return imageUri;
    }


    //Setters
    public void setTaglio(double taglio) {
        this.taglio = taglio;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(taglio);
        dest.writeString(materiale);
        dest.writeString(paese);
        dest.writeInt(anno);
        dest.writeString(imageUri);
        dest.writeLong(idCollection);
    }
}
