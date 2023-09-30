package com.example.atlas_bio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CampagneAdapter extends RecyclerView.Adapter<CampagneAdapter.CampagneViewHolder> {

    private List<Campagne> campagnes;

    public CampagneAdapter(List<Campagne> campagnes) {
        this.campagnes = campagnes;
    }

    @NonNull
    @Override
    public CampagneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campagne, parent, false);
        return new CampagneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampagneViewHolder holder, int position) {
        Campagne campagne = campagnes.get(position);

        // Lier les données de campagne aux vues de l'élément
        holder.textViewTitle.setText(campagne.getTitre());
        holder.textViewDateIn.setText("Date de début: " + campagne.getDateIn());
        holder.textViewDateOut.setText("Date de fin: " + campagne.getDateOut());
        holder.textViewDescription.setText(campagne.getDescription());
        holder.textViewGPS.setText("Position GPS: " + campagne.getPositionGPS());
    }

    @Override
    public int getItemCount() {
        return campagnes.size();
    }

    public static class CampagneViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDateIn;
        TextView textViewDateOut;
        TextView textViewDescription;
        TextView textViewGPS;

        public CampagneViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDateIn = itemView.findViewById(R.id.textViewDateIn);
            textViewDateOut = itemView.findViewById(R.id.textViewDateOut);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewGPS = itemView.findViewById(R.id.textViewGPS);
        }
    }
}
