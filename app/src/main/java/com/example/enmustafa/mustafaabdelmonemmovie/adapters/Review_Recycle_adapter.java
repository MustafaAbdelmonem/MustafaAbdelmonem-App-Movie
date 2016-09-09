package com.example.enmustafa.mustafaabdelmonemmovie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enmustafa.mustafaabdelmonemmovie.MovieDB;
import com.example.enmustafa.mustafaabdelmonemmovie.R;

import java.util.ArrayList;


public class Review_Recycle_adapter  extends RecyclerView.Adapter<Review_Recycle_adapter.viewHolder> {

    ArrayList<MovieDB> movieDB;
    Context context;
    LayoutInflater layoutInflater;

    public Review_Recycle_adapter(Context context, ArrayList<MovieDB> movieDB) {
        this.context = context;
        this.movieDB = movieDB;
        layoutInflater = layoutInflater.from(context);


    }

    @Override
    public Review_Recycle_adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new viewHolder(layoutInflater.inflate(R.layout.review_row, parent, false));

    }

    @Override
    public void onBindViewHolder(Review_Recycle_adapter.viewHolder holder, int position) {

        holder.author.setText(movieDB.get(position).getAuthor());
        holder.content.setText(movieDB.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return movieDB.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;

        public viewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}

