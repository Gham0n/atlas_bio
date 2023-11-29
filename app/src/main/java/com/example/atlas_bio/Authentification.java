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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Authentification extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    private Button buttonSignUp;

    private EditText editTextName;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextName = view.findViewById(R.id.editTextName);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);

        FirebaseApp.initializeApp(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String name = editTextName.getText().toString();

                if(name.isEmpty() && editTextName.getVisibility() == View.GONE) {
                    editTextName.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // L'inscription a réussi, vous pouvez rediriger l'utilisateur ou effectuer d'autres actions ici.
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        User user1 = new User(name,email,user.getUid());

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference usersRef = database.getReference("users");
                                        usersRef.child(user.getUid()).setValue(user1);


                                        Toast.makeText(getContext(), "Inscription réussie!", Toast.LENGTH_SHORT).show();

                                        // Ajoutez ici le code pour rediriger l'utilisateur vers une autre activité ou effectuer d'autres actions après l'inscription réussie.
                                    } else {
                                        // L'inscription a échoué, affichez un message d'erreur.
                                        Toast.makeText(getContext(), "Erreur lors de l'inscription: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });




        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if ( editTextName.getVisibility() == View.VISIBLE)
                {
                    editTextName.setVisibility(View.GONE);
                    editTextName.setText("");
                }

                if (email.isEmpty() || password.isEmpty())
                    Toast.makeText(getContext(),"Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                else {
                    // Authentification de l'utilisateur avec Firebase
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // L'authentification a réussi, vous pouvez rediriger l'utilisateur ou effectuer d'autres actions ici.
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                                        navController.navigate(R.id.first_fragment);

                                    } else {
                                        // L'authentification a échoué, affichez un message d'erreur.

                                        Toast.makeText(getContext(), "Nom d'utilisateur ou mot de passe faux", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }
}

