package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class ImageFullScreenFragment extends Fragment {
    private ImageView imageView;

    // Clé pour extraire l'URL de l'image des arguments
    private static final String ARG_IMAGE_URL = "imageUrl";

    // Méthode statique pour créer une nouvelle instance du fragment avec l'URL de l'image
    public static ImageFullScreenFragment newInstance(String imageUrl) {
        ImageFullScreenFragment fragment = new ImageFullScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_image_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.dialogImageView);

        // Récupérer l'URL de l'image à partir des arguments
        String imageUrl = getArguments().getString(ARG_IMAGE_URL);

        // Charger l'image dans l'ImageView à partir de l'URL avec Picasso
        Picasso.get().load(imageUrl).error(R.drawable.pokemon).fit().centerInside().into(imageView);
    }
}
