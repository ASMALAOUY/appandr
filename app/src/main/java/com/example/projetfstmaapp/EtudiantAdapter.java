package com.example.projetfstmaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetfstmaapp.classes.Etudiant;

import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> {

    public interface OnActionListener {
        void onModifier(Etudiant e, int position);
        void onSupprimer(Etudiant e, int position);
    }

    private List<Etudiant> liste;
    private OnActionListener listener;

    public EtudiantAdapter(List<Etudiant> liste, OnActionListener listener) {
        this.liste    = liste;
        this.listener = listener;
    }

    public void setListe(List<Etudiant> liste) {
        this.liste = liste;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_etudiant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etudiant e = liste.get(position);

        holder.nomTextView.setText(e.getNom());
        holder.prenomTextView.setText(e.getPrenom());
        holder.idTextView.setText("ID " + e.getId());

        String initiales =
                (e.getNom().length()    > 0 ? String.valueOf(e.getNom().charAt(0))    : "") +
                        (e.getPrenom().length() > 0 ? String.valueOf(e.getPrenom().charAt(0)) : "");
        holder.avatarTextView.setText(initiales.toUpperCase());

        holder.btnModifier.setOnClickListener(v -> listener.onModifier(e, position));
        holder.btnSupprimer.setOnClickListener(v -> listener.onSupprimer(e, position));
    }

    @Override
    public int getItemCount() { return liste != null ? liste.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView avatarTextView, nomTextView, prenomTextView, idTextView;
        Button   btnModifier, btnSupprimer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarTextView  = itemView.findViewById(R.id.item_avatar);
            nomTextView     = itemView.findViewById(R.id.item_nom);
            prenomTextView  = itemView.findViewById(R.id.item_prenom);
            idTextView      = itemView.findViewById(R.id.item_id);
            btnModifier     = itemView.findViewById(R.id.btn_modifier);
            btnSupprimer    = itemView.findViewById(R.id.btn_item_delete);
        }
    }
}