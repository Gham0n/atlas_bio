package com.example.atlas_bio;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.FicheViewHolder> {
    private List<Fiche> fiches;
    private OnItemClickListener itemClickListener; // Ajout de l'interface

    public FicheAdapter(List<Fiche> fiches) {
        this.fiches = fiches;
    }

    // Setter pour l'interface
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
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

        holder.textViewEspece.setText(fiche.getEspece());
        holder.textViewDateEtHeure.setText(fiche.getDate());
        holder.textViewObservation.setText(fiche.getObservation());
        holder.textViewLieu.setText(fiche.getLieu());
        holder.textViewGPS.setText("");

        if (fiche.getImageUrl() != null && !fiche.getImageUrl().isEmpty()) {
            try {
                Picasso.get().load(fiche.getImageUrl()).into(holder.imageFiche);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Picasso.get().load(R.drawable.pokemon).into(holder.imageFiche);
        }
      
        holder.itemView.setOnLongClickListener(v -> {
            if(fiche.getCoordoneesGPS() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("coordonneeGPS", fiche.getCoordoneesGPS());
                Navigation.findNavController(v).navigate(R.id.ficheToMap,bundle);
                return true;
            }
            else {
                Toast msg = Toast.makeText(v.getContext().getApplicationContext(),R.string.error_fiche_gps,Toast.LENGTH_SHORT);
                msg.show();
                return false;
            }
        });

        holder.cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Utilisation de l'interface pour passer l'URL à l'activité
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(fiche.getImageUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fiches.size();
    }



    public static class FicheViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEspece;
        TextView textViewDateEtHeure;
        TextView textViewObservation;
        TextView textViewLieu;
        TextView textViewGPS;
        ImageView imageFiche;

        CardView cardImage;

        public FicheViewHolder(View itemView) {
            super(itemView);
            textViewEspece = itemView.findViewById(R.id.textViewEspece);
            textViewDateEtHeure = itemView.findViewById(R.id.textViewDateHeure);
            textViewObservation = itemView.findViewById(R.id.textViewObservation);
            textViewLieu = itemView.findViewById(R.id.textViewLieu);
            textViewGPS = itemView.findViewById(R.id.textViewCoordonneesGPS);
            imageFiche = itemView.findViewById(R.id.imageFiche);
            cardImage = itemView.findViewById(R.id.cardViewImageFiche);
        }
    }

    // Interface pour gérer les clics
    public interface OnItemClickListener {
        void onItemClick(String imageUrl);
    }
}