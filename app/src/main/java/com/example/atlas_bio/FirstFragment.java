package com.example.atlas_bio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment implements CampagneAdapter.OnCampagneClickListener {

    private RecyclerView recyclerView;
    private CampagneAdapter campagneAdapter;
    private List<Campagne> campagnes;

    private Button btn_add_campagne;

    String TAG = "GUI";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCampagne);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation des données de campagne (vous pouvez remplacer par vos données réelles)
        campagnes = new ArrayList<>();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("campagnes");



        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Effacez les données précédentes des campagnes
                campagnes.clear();

                for (DataSnapshot campagneSnapshot : dataSnapshot.getChildren()) {
                    Campagne campagne = campagneSnapshot.getValue(Campagne.class);
                    if (campagne != null) {
                        campagnes.add(campagne);
                    }
                }

                // Mettez à jour le RecyclerView ou l'UI avec les campagnes récupérées
                campagneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérez l'erreur ici si nécessaire
            }
        });



        // Configuration du RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        campagneAdapter = new CampagneAdapter(campagnes, this);
        recyclerView.setAdapter(campagneAdapter);





        btn_add_campagne = view.findViewById(R.id.btnAddCampagne);
        btn_add_campagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.addCampagne);

            }

        });




    }


    @Override
    public void onCampagneClick(int position) {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        navController.navigate(R.id.FicheListFragment);
    }
}
