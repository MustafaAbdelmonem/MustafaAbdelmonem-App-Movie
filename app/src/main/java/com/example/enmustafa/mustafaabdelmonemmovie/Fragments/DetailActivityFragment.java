package com.example.enmustafa.mustafaabdelmonemmovie.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.enmustafa.mustafaabdelmonemmovie.Favorite_data;
import com.example.enmustafa.mustafaabdelmonemmovie.MovieDB;
import com.example.enmustafa.mustafaabdelmonemmovie.R;
import com.example.enmustafa.mustafaabdelmonemmovie.adapters.Review_Recycle_adapter;
import com.example.enmustafa.mustafaabdelmonemmovie.adapters.Trailer_Recycle_adapter;
import com.example.enmustafa.mustafaabdelmonemmovie.ApiMovieKeys;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivityFragment extends Fragment {

    long id;
    String title;
    String poster_path;
    RequestQueue requestQueue;
    MovieDB data;
    ArrayList<MovieDB> review_data, Trailer_data;
    ImageView panner, poster, star;
    ///////
    TextView overview, release_date, vote_average ,movietitle;
    RecyclerView Review_list, Trailer_list;
    Favorite_data favorite;

    Bundle bundle = null;


    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());

        bundle = getArguments();
        id = bundle.getLong("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_detail, container, false);

        panner = (ImageView) rootview.findViewById(R.id.moviepanner);
        poster = (ImageView) rootview.findViewById(R.id.poster2);
        overview = (TextView) rootview.findViewById(R.id.summary);
        release_date = (TextView) rootview.findViewById(R.id.release_date);
        vote_average = (TextView) rootview.findViewById(R.id.vote_average);
        star = (ImageView) rootview.findViewById(R.id.star);
////////////////
        movietitle = (TextView) rootview.findViewById(R.id.movietitle);

        favorite = new Favorite_data(getActivity());
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star.setImageResource(android.R.drawable.star_big_on);
                if (favorite.retrivebyID(id)) {
                    Toast.makeText(getActivity(), "This Film is Already in Favorite List", Toast.LENGTH_LONG).show();
                } else {
                    favorite.insert(id, poster_path, title);
                }
            }
        });

        Trailer_list = (RecyclerView) rootview.findViewById(R.id.recycler_Trailer);
        Trailer_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        Review_list = (RecyclerView) rootview.findViewById(R.id.recycler_Review);
        Review_list.setLayoutManager(new LinearLayoutManager(getActivity()));


        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean x;
        x = favorite.retrivebyID(id);
        if (x) {
            star.setImageResource(android.R.drawable.star_big_on);
        }
        SendjsonRequest("http://api.themoviedb.org/3/movie/" + id + "?api_key=3714a158c3b4fd34cb9e90ce29a46dad");
        sendjsonRequestReview("http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=3714a158c3b4fd34cb9e90ce29a46dad");
        sendjsonRequestTrailer("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=3714a158c3b4fd34cb9e90ce29a46dad");

    }

    public void SendjsonRequest(String Url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                data = Getdata(jsonObject);

                if (data != null) {
                    Picasso.with(getActivity()).load(Uri.parse("http://image.tmdb.org/t/p/w185" + data.getBackdrop_path()))
                            .into(panner);
                    Picasso.with(getActivity()).load(Uri.parse("http://image.tmdb.org/t/p/w185" + data.getPoster_path()))
                            .into(poster);
                    overview.setText(data.getOverview());
                    release_date.setText(data.getRelease_date());
                    vote_average.setText(data.getVote_average() + "");
///////////////////////////
                    movietitle.setText(data.getTitle());

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

    public MovieDB Getdata(JSONObject jsonObject) {

        MovieDB mydata = null;

        if (jsonObject == null || jsonObject.length() == 0) {
            return null;
        } else {
            try {
                title = jsonObject.getString(ApiMovieKeys.title);
                poster_path = jsonObject.getString(ApiMovieKeys.poster_path);
                String backdrop_path = jsonObject.getString(ApiMovieKeys.backdrop_path);
                String release_date = jsonObject.getString(ApiMovieKeys.release_date);
                double vote_average = jsonObject.getDouble(ApiMovieKeys.vote_average);
                String overview = jsonObject.getString(ApiMovieKeys.overview);
                mydata = new MovieDB(poster_path, overview, release_date, title, backdrop_path, vote_average);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mydata;
    }

    public void sendjsonRequestReview(String Url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                review_data = getDataReview(jsonObject);

                if (review_data != null) {
                    Review_list.setAdapter(new Review_Recycle_adapter(getActivity(), review_data));
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

    public ArrayList<MovieDB> getDataReview(JSONObject jsonObject) {

        ArrayList<MovieDB> mydata = new ArrayList<>();

        if (jsonObject == null || jsonObject.length() == 0) {
            return null;
        } else {
            try {
                JSONArray results = jsonObject.getJSONArray(ApiMovieKeys.results);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject row = results.getJSONObject(i);

                    String author = row.getString(ApiMovieKeys.author);
                    String content = row.getString(ApiMovieKeys.content);
                    mydata.add(new MovieDB(author, content));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mydata;
    }

    public void sendjsonRequestTrailer(String Url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Trailer_data = getDataTrailer(jsonObject);
                if (Trailer_data != null) {
                    Trailer_list.setAdapter(new Trailer_Recycle_adapter(getActivity(), Trailer_data));
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

    public ArrayList<MovieDB> getDataTrailer(JSONObject jsonObject) {

        ArrayList<MovieDB> mydata = new ArrayList<>();
        if (jsonObject == null || jsonObject.length() == 0) {
            return null;
        } else {
            try {
                JSONArray results = jsonObject.getJSONArray(ApiMovieKeys.results);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject row = results.getJSONObject(i);
                    MovieDB index = new MovieDB();
                    String trailer_name = row.getString(ApiMovieKeys.trailer_name);
                    String trailer_key = row.getString(ApiMovieKeys.trailer_key);

                    index.setTrailername(trailer_name);
                    index.setTrailerkey(trailer_key);
                    mydata.add(index);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mydata;
    }

}
