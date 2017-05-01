package Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Entities.Match;
import pineapple.ezscore.R;

/**
 * Created by rasmusmadsen on 01/05/2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder>{

    private List<Match> lMatches;

    public MatchAdapter (List<Match> _lMatches) {
        this.lMatches = _lMatches;
    }

    @Override
    public int getItemCount() {
        return lMatches.size();
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        MatchViewHolder mvh = new MatchViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        holder.team1.setText(lMatches.get(position).getTeam1());
        holder.team1Score.setText(lMatches.get(position).getTeam1Score().toString());
        holder.team2.setText(lMatches.get(position).getTeam2());
        holder.team2Score.setText(lMatches.get(position).getTeam2Score().toString());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView team1;
        TextView team1Score;
        TextView team2;
        TextView team2Score;

        MatchViewHolder(View itemView) {
            super (itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            team1 = (TextView) itemView.findViewById(R.id.team1);
            team1Score = (TextView) itemView.findViewById(R.id.team1Score);
            team2 = (TextView) itemView.findViewById(R.id.team2);
            team2Score = (TextView) itemView.findViewById(R.id.team2Score);
        }
    }

}
