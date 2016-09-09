package com.example.enmustafa.mustafaabdelmonemmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.enmustafa.mustafaabdelmonemmovie.Fragments.DetailActivityFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = new Bundle();
        bundle.putLong("id", getIntent().getExtras().getLong("id"));
        DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
        detailActivityFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_fragment, detailActivityFragment)
                .commit();
    }

}
