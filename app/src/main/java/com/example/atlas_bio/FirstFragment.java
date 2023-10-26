package com.example.atlas_bio;

import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment implements CampagneAdapter.OnCampagneClickListener {

    private RecyclerView recyclerView;
    private CampagneAdapter campagneAdapter;
    private List<Campagne> campagnes;

    private Button btn_add_campagne;

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
        campagnes.add(new Campagne("Campagne 1", "01/01/2023", "31/01/2023", "Description de la campagne 1", "Latitude: 12.345, Longitude: 67.890"));
        campagnes.add(new Campagne("Campagne 2", "02/01/2023", "28/02/2023", "Description de la campagne 2", "Latitude: 12.456, Longitude: 68.901"));
        // Ajoutez plus de campagnes si nécessaire

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
