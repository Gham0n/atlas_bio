package com.example.atlas_bio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;

public class FragmentFicheDetails extends Fragment {
    private TextView espèceTextView, coordonnéesTextView, dateTextView, heureTextView, lieuTextView, observationTextView;
    private RecyclerView recyclerView;
    private List<String> commentsList = new ArrayList<>();
    private CommentAdapter commentAdapter;

    private Button addCommentButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fiche_detail, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewComments);
        commentAdapter = new CommentAdapter(commentsList);

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
            Picasso.get().load(imageUrl).error(R.drawable.pokemon).into(imageView);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("bird.jpg");

            coordonneesTextView.setText("Coordonnées GPS : " + coordonnees);
            dateTextView.setText("Date : " + date);
            heureTextView.setText("Heure : " + heure);
            lieuTextView.setText("Lieu : " + lieu);
            observationTextView.setText("Observation : " + observation);


        }

        // Gestion du bouton d'ajout de commentaire
        addCommentButton = view.findViewById(R.id.button_add_comment);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.ficheDetailToAddComment);

            }
        });
        return view;
    }
}