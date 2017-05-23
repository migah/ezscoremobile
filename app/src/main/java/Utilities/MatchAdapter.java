package Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import Entities.Match;
import Repositories.MatchRepository;
import pineapple.ezscore.MatchActivity;
import pineapple.ezscore.MatchEditActivity;
import pineapple.ezscore.R;

/**
 * Created by rasmusmadsen on 01/05/2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder>{

    private List<Match> lMatches;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private MatchRepository matchRepository;

    /**
     * Contstruct the MatchAdapter
     * @param context the context
     * @param _lMatches the list of matches to adapt
     */
    public MatchAdapter (Context context, List<Match> _lMatches) {
        this.lMatches = _lMatches;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        matchRepository = new MatchRepository();
    }

    /**
     * Returns the size of the list
     * @return the item count as an int
     */
    @Override
    public int getItemCount() {
        return lMatches.size();
    }

    /**
     * Initiates a view with the Card Layout and attaches it to a holder.
     * @param parent the parent, to get the context from.
     * @param viewType viewType
     * @return the MatchViewHolder
     */
    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MatchViewHolder(v);
    }

    /**
     * Binds the data to the Card Layouts widgets
     * @param holder the holder to bind to
     * @param position the position of the current match to adapt
     */
    @Override
    public void onBindViewHolder(MatchViewHolder holder, final int position) {
        holder.team1.setText(lMatches.get(position).getTeam1());
        holder.team1Score.setText(lMatches.get(position).getTeam1Score().toString());
        holder.team2.setText(lMatches.get(position).getTeam2());
        holder.team2Score.setText(lMatches.get(position).getTeam2Score().toString());
        switch (lMatches.get(position).getSport().getName().toLowerCase()) {
            case "football":
                holder.img.setImageResource(R.mipmap.football);
                break;
            case "cs:go":
                holder.img.setImageResource(R.mipmap.csgo);
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (lMatches.get(position).getCreatorId().equals(firebaseAuth.getCurrentUser().getUid())) {
                        System.out.println("Right user");
                        Intent i = new Intent(context, MatchEditActivity.class);
                        i.putExtra("MatchKey", lMatches.get(position).getId());
                        context.startActivity(i);
                    } else {
                        Intent i = new Intent(context, MatchActivity.class);
                        i.putExtra("MatchKey", lMatches.get(position).getId());
                        context.startActivity(i);
                    }
                } catch (NullPointerException ex) {
                    Intent i = new Intent(context, MatchActivity.class);
                    i.putExtra("MatchKey", lMatches.get(position).getId());
                    context.startActivity(i);
                }

            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (lMatches.get(position).getCreatorId().equals(firebaseAuth.getCurrentUser().getUid())) {
                    new AlertDialog.Builder(context)
                            .setTitle("Are you sure you want to delete this match?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    matchRepository.removeMatch(lMatches.get(position).getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return true;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * The MatchViewHolder class
     */
    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView team1;
        TextView team1Score;
        TextView team2;
        TextView team2Score;
        ImageView img;

        /**
         * Initializes holder
         * @param itemView the View to get the widgets from
         */
        MatchViewHolder(View itemView) {
            super (itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            team1 = (TextView) itemView.findViewById(R.id.team1);
            team1Score = (TextView) itemView.findViewById(R.id.team1Score);
            team2 = (TextView) itemView.findViewById(R.id.team2);
            team2Score = (TextView) itemView.findViewById(R.id.team2Score);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

}
