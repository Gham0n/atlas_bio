package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class AddCampagneFragment extends Fragment {

    private TextInputEditText editTextTitle, editTextDateIn, editTextDateOut, editTextDescription, editTextGPS;
    private Button buttonAjouterCampagne;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_camapgne, container, false);

        editTextTitle = view.findViewById(R.id.textViewTitle);
        editTextDateIn = view.findViewById(R.id.textViewDateIn);
        editTextDateOut = view.findViewById(R.id.textViewDateOut);
        editTextDescription = view.findViewById(R.id.textViewDescription);
        editTextGPS = view.findViewById(R.id.textViewGPS);
        buttonAjouterCampagne = view.findViewById(R.id.buttonAjouterCampagne);

        buttonAjouterCampagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String dateIn = editTextDateIn.getText().toString().trim();
                String dateOut = editTextDateOut.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String gps = editTextGPS.getText().toString().trim();

                // Ici, vous devrez enregistrer les données de la campagne dans Firebase ou dans votre base de données.

                // Après avoir ajouté la campagne, vous pouvez afficher un message de succès.
                Toast.makeText(requireContext(), "Campagne ajoutée avec succès", Toast.LENGTH_SHORT).show();

                // Vous pouvez également rediriger l'utilisateur vers une autre vue, par exemple, la liste des campagnes.
            }
        });

        return view;
    }
}
