package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

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

            // Récupérez les TextViews du layout.
            TextView title = view.findViewById(R.id.textView);
            TextView coordonneesTextView = view.findViewById(R.id.textViewCoordonnees);
            TextView dateTextView = view.findViewById(R.id.textViewDate);
            TextView heureTextView = view.findViewById(R.id.textViewHeure);
            TextView lieuTextView = view.findViewById(R.id.textViewLieu);
            TextView observationTextView = view.findViewById(R.id.textViewObservation);
            ImageView imageView = view.findViewById(R.id.imageDetails);

            title.setText(espece);
            Picasso.get().load(bundle.getString("imageUrl")).error(R.drawable.pokemon).into(imageView);
            coordonneesTextView.setText("Coordonnées GPS : " + coordonnees);
            dateTextView.setText("Date : " + date);
            heureTextView.setText("Heure : " + heure);
            lieuTextView.setText("Lieu : " + lieu);
            observationTextView.setText("Observation : " + observation);


        }
        return view;
    }
}