package com.example.enmustafa.mustafaabdelmonemmovie.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.enmustafa.mustafaabdelmonemmovie.ApiMovieKeys;
import com.example.enmustafa.mustafaabdelmonemmovie.Favorite_data;
import com.example.enmustafa.mustafaabdelmonemmovie.MovieDB;
import com.example.enmustafa.mustafaabdelmonemmovie.R;
import com.example.enmustafa.mustafaabdelmonemmovie.adapters.RecycleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivityFragment extends Fragment {


    RequestQueue requestQueue;
    ArrayList<MovieDB> data;
    RecyclerView gridview;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<MovieDB> favorite_film = new ArrayList<MovieDB>();
    Favorite_data film;

    public MainActivityFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
        int choose = sharedPreferences.getInt("choose", 1);

        if (choose == 1) {
            SendjsonRequest(ApiMovieKeys.URLRATE);
        } else if (choose == 2) {
            SendjsonRequest(ApiMovieKeys.URLPOP);
        } else {
            favorite_film = film.retrive();
            gridview.setAdapter(new RecycleAdapter(getActivity(), favorite_film));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        requestQueue = Volley.newRequestQueue(getActivity());

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rated) {
            editor.putInt("choose", 1);
            SendjsonRequest(ApiMovieKeys.URLRATE);
        } else if (id == R.id.pop) {
            editor.putInt("choose", 2);
            SendjsonRequest(ApiMovieKeys.URLPOP);
        } else {
            editor.putInt("choose", 3);
            favorite_film = film.retrive();
            gridview.setAdapter(new RecycleAdapter(getActivity(), favorite_film));
        }


        editor.apply();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        film = new Favorite_data(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        gridview = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);
        gridview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return rootview;
    }

    public void SendjsonRequest(String Url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                data = Getdata(jsonObject);

                if (data != null) {
                    gridview.setAdapter(new RecycleAdapter(getActivity(), data));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );

        requestQueue.add(request);
    }

    public ArrayList<MovieDB> Getdata(JSONObject jsonObject) {

        ArrayList<MovieDB> mydata = new ArrayList<>();

        if (jsonObject == null || jsonObject.length() == 0) {
            return null;
        } else {
            try {
                JSONArray results = jsonObject.getJSONArray(ApiMovieKeys.results);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject row = results.getJSONObject(i);
                    long id = row.getLong(ApiMovieKeys.id);
                    String title = row.getString(ApiMovieKeys.title);
                    String poster_path = row.getString(ApiMovieKeys.poster_path);

                    mydata.add(new MovieDB(id, poster_path, title));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mydata;
    }
}
