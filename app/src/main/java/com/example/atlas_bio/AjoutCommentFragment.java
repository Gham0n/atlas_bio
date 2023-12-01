package com.example.atlas_bio;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
public class AjoutCommentFragment extends Fragment {

    private EditText commentEditText;

    private Button addCom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_comment, container, false);

        commentEditText = view.findViewById(R.id.editTextCommentaire);

        addCom = view.findViewById(R.id.buttonAjouterComentaire);
        addCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentEditText.getText().toString().trim();
                String authorName = ""; //recuperer pseudo

                Comment com = new Comment();

                com.setAuthor(authorName);
                com.setContent(commentText);

                Bundle bundle = new Bundle();
                bundle.putString("commentText", commentText);
                bundle.putString("authorName", authorName);

                getParentFragmentManager().setFragmentResult("commentKey", bundle);
                getParentFragmentManager().popBackStack();

                getParentFragmentManager().setFragmentResultListener("commentKey", getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String commentText = result.getString("commentText");
                        String authorName = result.getString("authorName");

                        // Ajouter le commentaire à la liste des commentaires
                        addComment(commentText, authorName);
                    }
                });
            }
        });



        return view;
    }
}
