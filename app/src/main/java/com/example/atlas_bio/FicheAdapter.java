package com.example.atlas_bio;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.FicheViewHolder> {
    private List<Fiche> fiches;

    public FicheAdapter(List<Fiche> fiches) {
        this.fiches = fiches;
    }

    @NonNull
    @Override
    public FicheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fiche, parent, false);
        return new FicheViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FicheViewHolder holder, int position) {
        Fiche fiche = fiches.get(position);
/*
        holder.textViewEspece.setText("Espèce: " + fiche.getEspece());
        holder.textViewDateEtHeure.setText("Date et heure: " + fiche.getDate());
        holder.textViewObservation.setText("Observation: " + fiche.getObservation());
        holder.textViewLieu.setText("Lieu: " + fiche.getLieu());
        holder.textViewGPS.setText("Coordonées GPS: " + fiche.getCoordoneesGPS());
*/
        holder.textViewEspece.setText(fiche.getEspece());
        holder.textViewDateEtHeure.setText(fiche.getDate());
        holder.textViewObservation.setText(fiche.getObservation());
        holder.textViewLieu.setText(fiche.getLieu());
        holder.textViewGPS.setText("");

        if(fiche.getImageUrl() != null)
            Picasso.get().load(fiche.getImageUrl()).into(holder.imageFiche);

    }

    @Override
    public int getItemCount() {
        return fiches.size();
    }

    static class FicheViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEspece;
        TextView textViewDateEtHeure;
        TextView textViewObservation;
        TextView textViewLieu;
        TextView textViewGPS;

        ImageView imageFiche;

        public FicheViewHolder(View itemView) {
            super(itemView);
            textViewEspece = itemView.findViewById(R.id.textViewEspece);
            textViewDateEtHeure = itemView.findViewById(R.id.textViewDateHeure);
            textViewObservation = itemView.findViewById(R.id.textViewObservation);
            textViewLieu = itemView.findViewById(R.id.textViewLieu);
            textViewGPS = itemView.findViewById(R.id.textViewCoordonneesGPS);
            imageFiche = itemView.findViewById(R.id.imageFiche);


        }
    }
}
