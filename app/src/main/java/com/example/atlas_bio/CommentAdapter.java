package com.example.atlas_bio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// CommentAdapter.java
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment currentComment = commentList.get(position);

        // Récupérer le texte du commentaire et le nom de l'auteur
        String commentText = currentComment.getContent();
        String authorName = currentComment.getAuthor();

        // Définir le texte sur les TextView correspondants
        holder.commentText.setText(commentText);
        holder.authorName.setText(authorName);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentText;
        TextView authorName;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.textViewComment);
            authorName = itemView.findViewById(R.id.textViewAuthor);
        }
    }
}

