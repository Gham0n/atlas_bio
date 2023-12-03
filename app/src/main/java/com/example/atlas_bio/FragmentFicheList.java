package com.example.atlas_bio;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentFicheList extends Fragment implements FicheAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FicheAdapter adapter;
    private List<Fiche> fiches;
    String nomCampagne;

    private Button btn_add_fiche;

    String TAG = "GUI";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fiche_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFiche);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fiches = new ArrayList<>();

        nomCampagne = getArguments().getString("nomCampagne");

        // Vérifier si nomCampagne est nul ou vide avant de construire la référence de la base de données
        if (!TextUtils.isEmpty(nomCampagne)) {
            Log.d(TAG, "Nom de la campagne: " + nomCampagne);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String campagneId = nomCampagne;
            DatabaseReference campagneRef = database.getReference("campagnes").child(campagneId).child("fiches");

            TextView titreLayout = view.findViewById(R.id.titreFicheList);
            titreLayout.setText(nomCampagne);


            campagneRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fiches.clear(); // Effacez les données précédentes des fiches
                    for (DataSnapshot ficheSnapshot : dataSnapshot.getChildren()) {
                        String espece = ficheSnapshot.child("espece").getValue(String.class);
                        String date = ficheSnapshot.child("date").getValue(String.class);
                        String heure = ficheSnapshot.child("heure").getValue(String.class);
                        String lieu = ficheSnapshot.child("lieu").getValue(String.class);
                        String observation = ficheSnapshot.child("observation").getValue(String.class);
                        String coordGPS = ficheSnapshot.child("coordoneesGPS").getValue(String.class);
                        String imageUrl = ficheSnapshot.child("imageUrl").getValue(String.class);

                        Fiche fiche = new Fiche();
                        fiche.setEspece(espece);
                        fiche.setDate(date);
                        fiche.setHeure(heure);
                        fiche.setLieu(lieu);
                        fiche.setObservation(observation);
                        fiche.setCoordoneesGPS(coordGPS);
                        fiche.setImageUrl(imageUrl);

                        fiches.add(fiche);
                    }
                    adapter.notifyDataSetChanged(); // Informez l'adaptateur des changements
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Gérez les erreurs ici si nécessaire
                    Log.e(TAG, "Erreur Firebase : " + databaseError.getMessage());
                }
            });



            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new FicheAdapter(fiches);
            adapter.setOnItemClickListener(this); // Définir le gestionnaire de clics
            recyclerView.setAdapter(adapter);

            btn_add_fiche = view.findViewById(R.id.btnAddFiche);

            btn_add_fiche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nomCampagne", nomCampagne);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.addFiche, bundle);
                }
            });
        } else {
            Log.e(TAG, "Nom de la campagne est nul ou vide");
        }
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            public void onItemClick(View view, int position) {
                // Lorsque l'élément de la liste est cliqué, affichez les détails de la fiche dans un nouveau fragment.
                Fiche selectedFiche = fiches.get(position);
                FragmentFicheDetails fragmentFicheDetail = new FragmentFicheDetails();

                // Transmettez les détails de la fiche au fragment de détails.
                Bundle bundle = new Bundle();
                bundle.putString("espece", selectedFiche.getEspece());
                bundle.putString("coordonnees", selectedFiche.getCoordoneesGPS());
                bundle.putString("date", selectedFiche.getDate());
                bundle.putString("heure", selectedFiche.getHeure());
                bundle.putString("lieu", selectedFiche.getLieu());
                bundle.putString("observation", selectedFiche.getObservation());
                bundle.putString("imageUrl", selectedFiche.getImageUrl());


                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.ficheList_To_FicheDetail,bundle);

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Gérez un clic long si nécessaire.
            }
        }));
    }


    // Implémentation de l'interface OnItemClickListener
    @Override
    public void onItemClick(String imageUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("imageURL", imageUrl);
        bundle.putString("nomCampagne", nomCampagne);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);





        ImageFullScreenFragment imageFullScreenFragment = ImageFullScreenFragment.newInstance(imageUrl);


        navController.navigate(R.id.image_full_screen, imageFullScreenFragment.getArguments());


    }
}