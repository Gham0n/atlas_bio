package com.example.atlas_bio;


import static android.app.Activity.RESULT_OK;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddFicheFragment extends Fragment {

    private EditText editTextEspece, editTextDate, editTextHeure, editTextLieu, editTextObservation, editTextCoordGPS, editTextImageUrl;
    private Button buttonAjouterFiche, button_return;

    private ImageButton btn_add_pic;
    private static final int PICK_IMAGE_REQUEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_fiche, container, false);

        editTextEspece = root.findViewById(R.id.editTextEspece);
        editTextDate = root.findViewById(R.id.editTextDate);
        editTextHeure = root.findViewById(R.id.editTextHeure);
        editTextLieu = root.findViewById(R.id.editTextLieu);
        editTextObservation = root.findViewById(R.id.editTextObservation);
        editTextCoordGPS = root.findViewById(R.id.editTextCoordGPS);
        editTextImageUrl = root.findViewById(R.id.editTextImageUrl);
        buttonAjouterFiche = root.findViewById(R.id.buttonAjouterFiche);
        button_return = root.findViewById(R.id.btn_retour);
        btn_add_pic = root.findViewById(R.id.button_add_image);

        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String nomCampagne = getArguments().getString("nomCampagne");

        buttonAjouterFiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextEspece.getText().toString().trim())) {
                    String espece = editTextEspece.getText().toString().trim();
                    String date = editTextDate.getText().toString().trim();
                    String heure = editTextHeure.getText().toString().trim();
                    String lieu = editTextLieu.getText().toString().trim();
                    String observation = editTextObservation.getText().toString().trim();
                    String coordGPS = editTextCoordGPS.getText().toString().trim();
                    String imageUrl = editTextImageUrl.getText().toString().trim();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = user.getUid();

                    Fiche fiche = new Fiche();

                    fiche.setEspece(espece);
                    fiche.setObservation(observation);
                    fiche.setLieu(lieu);
                    fiche.setDate(date);
                    fiche.setHeure(heure);
                    fiche.setCoordoneesGPS(coordGPS);
                    fiche.setImageUrl(imageUrl);
                    fiche.setIdCreator(userId);

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
                } else {
                    //Message d'erreur car le nom de l'espèce est vide
                    Toast.makeText(requireContext(), "Erreur: le nom de l'espèce est vide", Toast.LENGTH_SHORT).show();
                }


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


        btn_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ouvrir la galerie pour sélectionner une image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Télécharger l'image sélectionnée sur Firebase Storage
            uploadImage(selectedImageUri);
        }
    }

    private void uploadImage(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child("uploaded_image.jpg");

        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Téléchargement en cours...");
        progressDialog.show();

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), "Image téléchargée avec succès", Toast.LENGTH_SHORT).show();

                    // Obtenez l'URL de l'image téléchargée
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        editTextImageUrl.setText(imageUrl);
                    });
                })
                .addOnFailureListener(exception -> {
                    progressDialog.dismiss();
                    Log.e("FirebaseStorage", "Erreur de téléchargement : " + exception.getMessage());
                    Toast.makeText(requireContext(), "Erreur lors du téléchargement de l'image", Toast.LENGTH_SHORT).show();
                })
                .addOnProgressListener(snapshot -> {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressDialog.setMessage("Téléchargement en cours : " + (int) progress + "%");
                });


    }


}


