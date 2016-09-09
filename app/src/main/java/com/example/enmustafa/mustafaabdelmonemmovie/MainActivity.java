package com.example.enmustafa.mustafaabdelmonemmovie;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.enmustafa.mustafaabdelmonemmovie.Fragments.DetailActivityFragment;
import com.example.enmustafa.mustafaabdelmonemmovie.adapters.RecycleAdapter;

public class MainActivity extends AppCompatActivity implements RecycleAdapter.onClick {

    private boolean slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        slide = findViewById(R.id.movie_detail_fragment) != null;
    }

    @Override
    public void onclick(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);

        if (slide) {
            DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
            detailActivityFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_fragment, detailActivityFragment)
                    .commit();
        } else {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        }
    }
}
