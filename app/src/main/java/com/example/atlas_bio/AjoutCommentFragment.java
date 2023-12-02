package com.example.atlas_bio;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AjoutCommentFragment extends Fragment {

    private EditText commentEditText;

    private Button addCom,buttonReturn;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Bundle bundle;
    String authorName;

    String commentText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_comment, container, false);
        bundle = getArguments();
        commentEditText = view.findViewById(R.id.editTextCommentaire);

        //recuperer le nom de l'auteur
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseRef.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        authorName = user.getName();
                    }
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }
        });

        buttonReturn = view.findViewById(R.id.btn_retour);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentText = commentEditText.getText().toString().trim();


                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.fiche_detail, bundle);
            }
        });

        addCom = view.findViewById(R.id.buttonAjouterComentaire);
        addCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentText = commentEditText.getText().toString().trim();


            //ajouter le commentaire à la liste
            addComment();

            //retourner a detail fiche
            bundle.putString("commentText", commentText);
            bundle.putString("authorName", authorName);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.fiche_detail, bundle);
        }
        });


        return view;
    }

    public void addComment() {
        Log.d("GUI", "Author: " + authorName);
        Log.d("GUI", "Commentaire: " + commentText);

        Comment comment = new Comment(commentText, authorName);

        String nomCampagne = bundle.getString("nomCampagne", "");
        String espece = bundle.getString("espece", "");

        // Obtenez une référence vers la base de données Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Chemin vers la fiche spécifique dans la base de données
        String fichePath = "campagnes/" + nomCampagne + "/fiches/" + espece;

        // Ajoutez le nouveau commentaire à la liste existante dans la base de données
        databaseReference.child(fichePath).child("comments").push().setValue(comment);
    }


}
