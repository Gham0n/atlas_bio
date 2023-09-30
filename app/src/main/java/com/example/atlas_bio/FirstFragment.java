package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlas_bio.Campagne;
import com .example.atlas_bio.CampagneAdapter;
import com.example.atlas_bio.R;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private CampagneAdapter campagneAdapter;
    private List<Campagne> campagnes;

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
        campagneAdapter = new CampagneAdapter(campagnes); // Assurez-vous de créer cet adaptateur personnalisé
        recyclerView.setAdapter(campagneAdapter);
    }
}
