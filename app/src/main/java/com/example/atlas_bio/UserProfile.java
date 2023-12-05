package com.example.atlas_bio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserProfile extends Fragment implements CampagneAdapter.OnCampagneClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FicheAdapter adapter;
    private RecyclerView recyclerView;
    List<String> listCampagnes;
    List<Fiche> allFiches;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        disableFab();
        super.onCreate(savedInstanceState);
    }

    private void disableFab() {
        ((MainActivity) requireActivity()).disableFab();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile, container, false);

        TextView name = view.findViewById(R.id.userProfile_name);
        TextView email = view.findViewById(R.id.userProfile_email);

        email.setText(firebaseUser.getEmail());

        ImageButton btn_logout = view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.auth);
            }
        });

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseRef.orderByChild("email").equalTo(firebaseUser.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        // L'utilisateur a été trouvé, vous pouvez accéder à ses propriétés ici
                        String userName = user.getName();
                        name.setText(userName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gestion des erreurs
            }
        });

        recyclerView = view.findViewById(R.id.RecyclerMesFiches);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler view
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid(); //on recupere l'id de l'user



        listCampagnes = new ArrayList<>();
        allFiches = new ArrayList<>();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("campagnes");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot campagneSnapshot : dataSnapshot.getChildren()) {
                    String nomCampagne = campagneSnapshot.getKey();
                    listCampagnes.add(nomCampagne);

                }

                // Utilisez la liste nomsCampagnes comme vous le souhaitez ici
                for (String nomCampagne : listCampagnes) {
                    Log.d("GUI", nomCampagne);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String campagneId = nomCampagne;
                    DatabaseReference campagneRef = database.getReference("campagnes").child(campagneId).child("fiches");
                    campagneRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ficheSnapshot : dataSnapshot.getChildren()) {
                                String espece = ficheSnapshot.child("espece").getValue(String.class);
                                String date = ficheSnapshot.child("date").getValue(String.class);
                                String heure = ficheSnapshot.child("heure").getValue(String.class);
                                String lieu = ficheSnapshot.child("lieu").getValue(String.class);
                                String observation = ficheSnapshot.child("observation").getValue(String.class);
                                String coordGPS = ficheSnapshot.child("coordGPS").getValue(String.class);
                                String imageUrl = ficheSnapshot.child("imageUrl").getValue(String.class);
                                String idCreator = ficheSnapshot.child("idCreator").getValue(String.class);

                                Fiche fiche = new Fiche();
                                fiche.setEspece(espece);
                                fiche.setDate(date);
                                fiche.setHeure(heure);
                                fiche.setLieu(lieu);
                                fiche.setObservation(observation);
                                fiche.setCoordoneesGPS(coordGPS);
                                fiche.setImageUrl(imageUrl);


                                if(idCreator != null && idCreator.equals(userId)) {
                                    allFiches.add(fiche);
                                }
                            }
                            adapter.notifyDataSetChanged(); // Informez l'adaptateur des changements
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Gérez les erreurs ici si nécessaire
                            Log.e("GUI", "Erreur Firebase : " + databaseError.getMessage());
                        }
                    });
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new FicheAdapter(allFiches);
                adapter.setOnItemClickListener(UserProfile.this::onItemClick); // Définir le gestionnaire de clics
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    public void onItemClick(View view, int position) {
                        // Lorsque l'élément de la liste est cliqué, affichez les détails de la fiche dans un nouveau fragment.
                        Fiche selectedFiche = allFiches.get(position);
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

                    }
                }));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérez l'erreur ici si nécessaire
            }
        });
    }

    @Override
    public void onCampagneClick(int position) {


    }

    public void onItemClick(String imageUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("imageURL", imageUrl);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);





        ImageFullScreenFragment imageFullScreenFragment = ImageFullScreenFragment.newInstance(imageUrl);


        navController.navigate(R.id.image_full_screen, imageFullScreenFragment.getArguments());


    }
}
