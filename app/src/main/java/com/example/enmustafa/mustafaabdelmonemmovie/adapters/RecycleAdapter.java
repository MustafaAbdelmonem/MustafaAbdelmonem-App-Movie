package com.example.enmustafa.mustafaabdelmonemmovie.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enmustafa.mustafaabdelmonemmovie.MovieDB;
import com.example.enmustafa.mustafaabdelmonemmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.viewHolder> {

    ArrayList<MovieDB> movieDBs;
    Context context;
    LayoutInflater layoutInflater;
    onClick onClick;

    public RecycleAdapter(Context context, ArrayList<MovieDB> movieDBs) {
        this.context = context;
        this.movieDBs = movieDBs;
        layoutInflater = layoutInflater.from(context);
        onClick = (onClick) context;

    }

    @Override
    public RecycleAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new viewHolder(layoutInflater.inflate(R.layout.rowmovie, parent, false));

    }

    @Override
    public void onBindViewHolder(RecycleAdapter.viewHolder holder, int position) {
        Picasso.with(context).load(Uri.parse("http://image.tmdb.org/t/p/w185" + movieDBs.get(position).getPoster_path()))
                .into(holder.imageView);
        holder.title.setText(movieDBs.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return movieDBs.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.poster1);
            title = (TextView) itemView.findViewById(R.id.moive_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClick.onclick(movieDBs.get(getPosition()).getId());

                }
            });
        }
    }

    public interface onClick {
        void onclick(long id);
    }
}
