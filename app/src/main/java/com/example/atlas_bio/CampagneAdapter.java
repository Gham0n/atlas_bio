package com.example.atlas_bio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class CampagneAdapter extends RecyclerView.Adapter<CampagneAdapter.CampagneViewHolder> {

    private List<Campagne> campagnes;
    private OnCampagneClickListener campagneClickListener;

    String TAG = "GUI";

    public CampagneAdapter(List<Campagne> campagnes, OnCampagneClickListener listener) {
        this.campagnes = campagnes;
        this.campagneClickListener = listener;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String campagneId = campagne.getTitre();
        DatabaseReference campagneRef = database.getReference("campagnes").child(campagneId).child("fiches");

        List<String> fiches = new ArrayList<>();

        campagneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fiches.clear(); // Effacez les données précédentes des fiches
                for (DataSnapshot ficheSnapshot : dataSnapshot.getChildren()) {
                    String coordGPS = ficheSnapshot.child("coordGPS").getValue(String.class);
                    fiches.add(coordGPS);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérez les erreurs ici si nécessaire
                Log.e(TAG, "Erreur Firebase : " + databaseError.getMessage());
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (campagneClickListener != null) {
                    campagneClickListener.onCampagneClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("coordonneeGPS", (ArrayList<String>) fiches);
            Navigation.findNavController(v).navigate(R.id.campagneToMap,bundle);
            return true;
        });


    }

    @Override
    public int getItemCount() {
        return campagnes.size();
    }

    public interface OnCampagneClickListener {
        void onCampagneClick(int position);
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
