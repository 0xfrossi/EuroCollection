package com.example.europroject.models;

import java.util.List;

public interface CoinDAO {

    public void open();
    public void close();

    public Coin insertCoin(Coin coin);
    public void deleteCoin(Coin coin);
    public List<Coin> getAllCoin(long idColl);

    public List<Coin> getCoinsForCountry(String paese, long idColl);
    public List<Coin> getCoinsForYear(int anno,  long idColl);
    public List<Coin> getCoinsForPiece(float taglio,long idColl);
    public int getCount(long idColl);


    public List<String> getCountrys(long idColl);
    public List<Integer> getYears(long idColl);




}
