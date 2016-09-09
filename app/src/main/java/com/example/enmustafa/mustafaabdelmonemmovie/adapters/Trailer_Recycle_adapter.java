package com.example.enmustafa.mustafaabdelmonemmovie.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enmustafa.mustafaabdelmonemmovie.MovieDB;
import com.example.enmustafa.mustafaabdelmonemmovie.R;

import java.util.ArrayList;


public class Trailer_Recycle_adapter  extends RecyclerView.Adapter<Trailer_Recycle_adapter.viewHolder> {

    ArrayList<MovieDB> movieDB;
    Context context;
    LayoutInflater layoutInflater;

    public Trailer_Recycle_adapter(Context context, ArrayList<MovieDB> movieDB) {
        this.context = context;
        this.movieDB = movieDB;
        layoutInflater = layoutInflater.from(context);


    }

    @Override
    public Trailer_Recycle_adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new viewHolder(layoutInflater.inflate(R.layout.trailer_row, parent, false));

    }

    @Override
    public void onBindViewHolder(Trailer_Recycle_adapter.viewHolder holder, int position) {

        holder.trailername.setText(movieDB.get(position).getTrailername());

    }

    @Override
    public int getItemCount() {
        return movieDB.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView trailername;

        public viewHolder(final View itemView) {
            super(itemView);
            trailername = (TextView) itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.youtube.com/watch?v=" + movieDB.get(getPosition()).getTrailerkey() ));
                    context.startActivity(i);
                }
            });

        }
    }
}
