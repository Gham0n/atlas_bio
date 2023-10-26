package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

public class AddCampagneFragment extends Fragment {

    private TextInputEditText editTextTitle, editTextDateIn, editTextDateOut, editTextDescription, editTextGPS;
    private Button btn_ajouter_campagne;

    private Button btn_return;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_camapgne, container, false);

        editTextTitle = view.findViewById(R.id.textViewTitle);
        editTextDateIn = view.findViewById(R.id.textViewDateIn);
        editTextDateOut = view.findViewById(R.id.textViewDateOut);
        editTextDescription = view.findViewById(R.id.textViewDescription);
        editTextGPS = view.findViewById(R.id.textViewGPS);
        btn_ajouter_campagne = view.findViewById(R.id.buttonAjouterCampagne);
        btn_return = view.findViewById(R.id.btn_retour);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_ajouter_campagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String dateIn = editTextDateIn.getText().toString().trim();
                String dateOut = editTextDateOut.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String gps = editTextGPS.getText().toString().trim();

                // Ici, vous devrez enregistrer les données de la campagne dans Firebase ou dans votre base de données.

                // Après avoir ajouté la campagne, vous pouvez utiliser la navigation pour rediriger l'utilisateur vers une autre vue.
                Toast.makeText(requireContext(), "Campagne ajoutée avec succès", Toast.LENGTH_SHORT).show();

                // Vous pouvez également passer des données à la vue suivante en utilisant un Bundle si nécessaire.
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.first_fragment);


            }

        });

    }



}
