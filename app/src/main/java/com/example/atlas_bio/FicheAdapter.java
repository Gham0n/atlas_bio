package com.example.atlas_bio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        // Mettez à jour les éléments de la vue avec les données de la fiche
        holder.textViewEspece.setText("Espèce: " + fiche.getEspece());
        // Mettez à jour les autres TextView ici
    }

    @Override
    public int getItemCount() {
        return fiches.size();
    }

    static class FicheViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEspece;
        // Déclarez les autres TextView ici

        public FicheViewHolder(View itemView) {
            super(itemView);
            textViewEspece = itemView.findViewById(R.id.textViewEspece);
            // Initialisez les autres TextView ici
        }
    }
}
