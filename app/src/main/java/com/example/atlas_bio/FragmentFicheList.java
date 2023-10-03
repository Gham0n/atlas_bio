package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentFicheList extends Fragment {
    private RecyclerView recyclerView;
    private FicheAdapter adapter;
    private List<Fiche> fiches;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fiche_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFiche);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fiches = new ArrayList<>(); // Initialisez votre liste de fiches ici
        adapter = new FicheAdapter(fiches);
        recyclerView.setAdapter(adapter);


        // Ajoutez quelques fiches fictives à la liste
        Fiche fiche1 = new Fiche();
        fiche1.setEspece("Oiseau");
        fiche1.setCoordoneesGPS("12.345, 67.890");
        fiche1.setDate("2023-09-28");
        fiche1.setHeure("14:30");
        fiche1.setLieu("Parc");
        fiche1.setObservation("Observation d'un oiseau rare.");

        Fiche fiche2 = new Fiche();
        fiche2.setEspece("Papillon");
        fiche2.setCoordoneesGPS("45.678, 23.456");
        fiche2.setDate("2023-09-29");
        fiche2.setHeure("10:00");
        fiche2.setLieu("Jardin botanique");
        fiche2.setObservation("Observation d'un papillon coloré.");

        // Ajoutez d'autres fiches fictives au besoin

        // Ajoutez les fiches à la liste
        fiches.add(fiche1);
        fiches.add(fiche2);

        adapter = new FicheAdapter(fiches);
        recyclerView.setAdapter(adapter);

        return view;
    }
}


