package com.example.projetfstmaapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetfstmaapp.classes.Etudiant;
import com.example.projetfstmaapp.service.EtudiantService;

import java.util.List;

public class ListeActivity extends AppCompatActivity {

    private EtudiantService es;
    private EtudiantAdapter adapter;
    private List<Etudiant> liste;
    private TextView countLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        es = new EtudiantService(this);

        // Bouton retour
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        countLabel = findViewById(R.id.count_label);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        liste = es.findAll();
        updateCountLabel();

        adapter = new EtudiantAdapter(liste, new EtudiantAdapter.OnActionListener() {
            @Override
            public void onModifier(Etudiant e, int position) {
                showDialogModifier(e, position);
            }

            @Override
            public void onSupprimer(Etudiant e, int position) {
                String message = String.format(getString(R.string.msg_supprimer_confirmer),
                        e.getNom(), e.getPrenom());
                new AlertDialog.Builder(ListeActivity.this)
                        .setTitle(R.string.confirmer)
                        .setMessage(message)
                        .setPositiveButton(R.string.supprimer, (dialog, which) -> {
                            es.delete(e);
                            liste.remove(position);
                            adapter.notifyItemRemoved(position);
                            updateCountLabel();
                            Toast.makeText(ListeActivity.this,
                                    R.string.msg_etudiant_supprime, Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.annuler, null)
                        .show();
            }
        });

        rv.setAdapter(adapter);
    }

    private void showDialogModifier(Etudiant e, int position) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_modifier, null);
        EditText dialogNom = dialogView.findViewById(R.id.dialog_nom);
        EditText dialogPrenom = dialogView.findViewById(R.id.dialog_prenom);

        dialogNom.setText(e.getNom());
        dialogPrenom.setText(e.getPrenom());

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton(R.string.enregistrer, (dialog, which) -> {
                    String n = dialogNom.getText().toString().trim();
                    String p = dialogPrenom.getText().toString().trim();

                    if (n.isEmpty() || p.isEmpty()) {
                        Toast.makeText(this, R.string.msg_champs_vides, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    e.setNom(n);
                    e.setPrenom(p);
                    es.update(e);

                    liste.set(position, e);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(this, R.string.msg_modifie_succes, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.annuler, null)
                .show();
    }

    private void updateCountLabel() {
        String text = getResources().getQuantityString(R.plurals.etudiant_count, liste.size(), liste.size());
        countLabel.setText(text);
    }
}