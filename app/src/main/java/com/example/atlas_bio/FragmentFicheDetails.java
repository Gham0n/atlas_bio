package com.example.atlas_bio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class FragmentFicheDetails extends Fragment {
    private TextView espèceTextView, coordonnéesTextView, dateTextView, heureTextView, lieuTextView, observationTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fiche_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String espece = bundle.getString("espece", "");
            String coordonnees = bundle.getString("coordonnees", "");
            String date = bundle.getString("date", "");
            String heure = bundle.getString("heure", "");
            String lieu = bundle.getString("lieu", "");
            String observation = bundle.getString("observation", "");
            String imageUrl = bundle.getString("imageUrl");
            if (imageUrl.isEmpty())imageUrl = "error"; // for fix bug

            // Récupérez les TextViews du layout.
            TextView title = view.findViewById(R.id.textView);
            TextView coordonneesTextView = view.findViewById(R.id.textViewCoordonnees);
            TextView dateTextView = view.findViewById(R.id.textViewDate);
            TextView heureTextView = view.findViewById(R.id.textViewHeure);
            TextView lieuTextView = view.findViewById(R.id.textViewLieu);
            TextView observationTextView = view.findViewById(R.id.textViewObservation);
            ImageView imageView = view.findViewById(R.id.imageDetails);

            title.setText(espece);
            //Picasso.get().load(imageUrl).error(R.drawable.pokemon).into(imageView);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("bird.jpg");

            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Utilisez l'URL pour charger l'image avec Picasso
                String Url = uri.toString();
                Picasso.get().load(Url).error(R.drawable.pokemon).into(imageView);
            }).addOnFailureListener(exception -> {
                // Gestion des erreurs lors de la récupération de l'URL
                Log.e("FirebaseStorage", "Erreur de récupération de l'URL : " + exception.getMessage());
            });


            coordonneesTextView.setText("Coordonnées GPS : " + coordonnees);
            dateTextView.setText("Date : " + date);
            heureTextView.setText("Heure : " + heure);
            lieuTextView.setText("Lieu : " + lieu);
            observationTextView.setText("Observation : " + observation);


        }
        return view;
    }
}