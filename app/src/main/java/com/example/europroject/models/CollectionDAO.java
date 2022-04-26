package com.example.europroject.models;

import java.util.List;

public interface CollectionDAO {

    public void open();
    public void close();

    public Collection insertCollection(Collection coll);
    public void deleteCollection(Collection coll);
    public List<Collection> getAllCollection();
    public void editNote(Collection coll, String newNote);
}
