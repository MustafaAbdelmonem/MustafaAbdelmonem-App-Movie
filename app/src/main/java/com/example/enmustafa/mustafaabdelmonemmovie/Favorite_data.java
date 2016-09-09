package com.example.enmustafa.mustafaabdelmonemmovie;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class Favorite_data {
    Realm realm;
    RealmResults<MovieDB> realmResults;
    Context context;

    public Favorite_data(Context context) {
        realm = Realm.getInstance(context);
        this.context = context;
    }

    public void insert(long id, String poster, String title) {
        realm.beginTransaction();
        MovieDB object = new MovieDB(id, poster, title);
        realm.copyToRealm(object);
        realm.commitTransaction();
    }

    public ArrayList<MovieDB> retrive() {
        if (realm == null) {
            realm = Realm.getInstance(context);
        }
        ArrayList<MovieDB> mydata = new ArrayList<>();

        realmResults = realm.where(MovieDB.class).findAll();
        for (int i = 0; i < realmResults.size(); i++) {
            mydata.add(realmResults.get(i));
        }
        return mydata;
    }

    public boolean retrivebyID(long id) {
        if (realm == null) {
            realm = Realm.getInstance(context);
        }

        realmResults = realm.where(MovieDB.class).equalTo("id", id).findAll();
        if (realmResults != null & !realmResults.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
