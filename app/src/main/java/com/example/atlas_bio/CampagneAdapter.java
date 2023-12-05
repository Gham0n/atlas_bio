package com.example.atlas_bio;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class    CampagneAdapter extends RecyclerView.Adapter<CampagneAdapter.CampagneViewHolder> {

    private List<Campagne> campagnes;
    private OnCampagneClickListener campagneClickListener;

    String TAG = "GUI";

    Context ctx;

    public CampagneAdapter(List<Campagne> campagnes, OnCampagneClickListener listener) {
        this.campagnes = campagnes;
        this.campagneClickListener = listener;
    }

    @NonNull
    @Override
    public CampagneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campagne, parent, false);
        ctx = parent.getContext().getApplicationContext();
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

        //List<String> fiches = new ArrayList<>();

        Bundle fiches = new Bundle();


        campagneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fiches.clear(); // Effacez les données précédentes des fiches
                int i = 0;
                for (DataSnapshot ficheSnapshot : dataSnapshot.getChildren()) {
                    String espece = ficheSnapshot.child("espece").getValue(String.class);
                    String date = ficheSnapshot.child("date").getValue(String.class);
                    String heure = ficheSnapshot.child("heure").getValue(String.class);
                    String lieu = ficheSnapshot.child("lieu").getValue(String.class);
                    String observation = ficheSnapshot.child("observation").getValue(String.class);
                    String coordGPS = ficheSnapshot.child("coordoneesGPS").getValue(String.class);
                    String imageUrl = ficheSnapshot.child("imageUrl").getValue(String.class);
                    if (imageUrl.isEmpty())imageUrl = "error";

                    Bundle fiche = new Bundle();
                    fiche.putString("coordonneeGPS", coordGPS);
                    fiche.putString("nomCampagne", campagne.getTitre());
                    fiche.putString("espece", espece);
                    fiche.putString("coordonnees", coordGPS);
                    fiche.putString("date", date);
                    fiche.putString("heure", heure);
                    fiche.putString("lieu", lieu);
                    fiche.putString("observation", observation);
                    fiche.putString("imageUrl", imageUrl);

                    fiches.putBundle("fiche"+i,fiche);
                    i++;
                }
                if(fiches.isEmpty()) {
                    Toast msg = Toast.makeText(ctx,R.string.error_campagne_gps,Toast.LENGTH_SHORT);
                    msg.show();
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
            //Bundle bundle = new Bundle();
            //bundle.putStringArrayList("coordonneeGPS", (ArrayList<Bundle>) fiches);
            //bundle.putBundle("fiches",fiches);
            boolean isMappable = false;

            for(int i = 0; i < fiches.size(); i++) {
                String coord = fiches.getBundle("fiche"+i).getString("coordonneeGPS");
                if(coord != null && !coord.isEmpty()) isMappable = true;
            }

            if(isMappable) {
                Navigation.findNavController(v).navigate(R.id.campagneToMap,fiches);
            } else {
                Toast msg = Toast.makeText(ctx,R.string.error_campagne_gps,Toast.LENGTH_SHORT);
                msg.show();
            }
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
