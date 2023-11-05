package com.example.atlas_bio;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFicheFragment extends Fragment {

    private EditText editTextEspece, editTextDate, editTextHeure, editTextLieu, editTextObservation, editTextCoordGPS;
    private Button buttonAjouterFiche;

    private  Button button_return;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_fiche, container, false);

        editTextEspece = root.findViewById(R.id.editTextEspece);
        editTextDate = root.findViewById(R.id.editTextDate);
        editTextHeure = root.findViewById(R.id.editTextHeure);
        editTextLieu = root.findViewById(R.id.editTextLieu);
        editTextObservation = root.findViewById(R.id.editTextObservation);
        editTextCoordGPS = root.findViewById(R.id.editTextCoordGPS);
        buttonAjouterFiche = root.findViewById(R.id.buttonAjouterFiche);
        button_return = root.findViewById(R.id.btn_retour);

        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String nomCampagne = getArguments().getString("nomCampagne");

        buttonAjouterFiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String espece = editTextEspece.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String heure = editTextHeure.getText().toString().trim();
                String lieu = editTextLieu.getText().toString().trim();
                String observation = editTextObservation.getText().toString().trim();
                String coordGPS = editTextCoordGPS.getText().toString().trim();

                Fiche fiche = new Fiche();

                fiche.setEspece(espece);
                fiche.setObservation(observation);
                fiche.setLieu(lieu);
                fiche.setDate(date);
                fiche.setHeure(heure);
                fiche.setCoordoneesGPS(coordGPS);



                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference campagneRef = database.getReference("campagnes").child(nomCampagne);
                DatabaseReference fichesRef = campagneRef.child("fiches");
                fichesRef.child(espece).setValue(fiche);



                // Affichez un message de confirmation
                Toast.makeText(requireContext(), "Fiche ajoutée avec succès", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("nomCampagne", nomCampagne);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.FicheListFragment, bundle);
            }
        });


        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("nomCampagne", nomCampagne);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.FicheListFragment, bundle);


            }

        });



    }
}


