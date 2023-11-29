package com.example.atlas_bio;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                if (!TextUtils.isEmpty(editTextTitle.getText().toString().trim())) {
                    String title = editTextTitle.getText().toString().trim();
                    String dateIn = editTextDateIn.getText().toString().trim();
                    String dateOut = editTextDateOut.getText().toString().trim();
                    String description = editTextDescription.getText().toString().trim();
                    String gps = editTextGPS.getText().toString().trim();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = user.getUid();

                    Campagne campagne = new Campagne(title, dateIn, dateOut, description, gps);
                    campagne.setIdCreator(userId);


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference campagnesRef = database.getReference("campagnes");
                    campagnesRef.child(title).setValue(campagne);


                    // Après avoir ajouté la campagne, vous pouvez utiliser la navigation pour rediriger l'utilisateur vers une autre vue.
                    Toast.makeText(requireContext(), "Campagne ajoutée avec succès", Toast.LENGTH_SHORT).show();

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                    navController.navigate(R.id.first_fragment);
                }
                else
                {
                    Toast.makeText(requireContext(), "Le nom de la campagne est vide ", Toast.LENGTH_SHORT).show();
                }

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
