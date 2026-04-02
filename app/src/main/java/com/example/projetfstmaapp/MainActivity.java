package com.example.projetfstmaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetfstmaapp.classes.Etudiant;
import com.example.projetfstmaapp.service.EtudiantService;

public class MainActivity extends AppCompatActivity {

    private EditText     nom, prenom, id;
    private Button       add, rechercher, supprimer, btnListe;
    private TextView     res, resId, avatar;
    private LinearLayout resultContainer;
    private TextView     statTotal, statAdded, statDeleted;

    private int addedCount   = 0;
    private int deletedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EtudiantService es = new EtudiantService(this);

        nom             = findViewById(R.id.nom);
        prenom          = findViewById(R.id.prenom);
        add             = findViewById(R.id.bn);
        btnListe        = findViewById(R.id.btn_liste);
        id              = findViewById(R.id.id);
        rechercher      = findViewById(R.id.load);
        supprimer       = findViewById(R.id.delete);
        res             = findViewById(R.id.res);
        resId           = findViewById(R.id.res_id);
        avatar          = findViewById(R.id.avatar);
        resultContainer = findViewById(R.id.result_container);
        statTotal       = findViewById(R.id.stat_total);
        statAdded       = findViewById(R.id.stat_added);
        statDeleted     = findViewById(R.id.stat_deleted);

        refreshStats(es);

        // ── LISTE ─────────────────────────────────────────────
        btnListe.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ListeActivity.class))
        );

        // ── AJOUTER ───────────────────────────────────────────
        add.setOnClickListener(v -> {
            String n = nom.getText().toString().trim();
            String p = prenom.getText().toString().trim();

            if (n.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Remplir Nom et Prenom", Toast.LENGTH_SHORT).show();
                return;
            }

            es.create(new Etudiant(n, p));
            addedCount++;
            nom.setText("");
            prenom.setText("");
            resultContainer.setVisibility(View.GONE);
            refreshStats(es);
            Toast.makeText(this, "Etudiant ajoute avec succes", Toast.LENGTH_SHORT).show();

            for (Etudiant e : es.findAll()) {
                Log.d("LISTE", e.getId() + " : " + e.getNom() + " " + e.getPrenom());
            }
        });

        // ── CHERCHER ──────────────────────────────────────────
        rechercher.setOnClickListener(v -> {
            String txt = id.getText().toString().trim();
            if (txt.isEmpty()) {
                resultContainer.setVisibility(View.GONE);
                Toast.makeText(this, "Saisir un ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Etudiant e = es.findById(Integer.parseInt(txt));
            if (e == null) {
                resultContainer.setVisibility(View.GONE);
                Toast.makeText(this, "Etudiant introuvable", Toast.LENGTH_SHORT).show();
                return;
            }

            String initiales =
                    (e.getNom().length()    > 0 ? String.valueOf(e.getNom().charAt(0))    : "") +
                            (e.getPrenom().length() > 0 ? String.valueOf(e.getPrenom().charAt(0)) : "");
            avatar.setText(initiales.toUpperCase());
            res.setText(e.getNom() + " " + e.getPrenom());
            resId.setText("ID : " + e.getId());
            resultContainer.setVisibility(View.VISIBLE);
        });

        // ── SUPPRIMER ─────────────────────────────────────────
        supprimer.setOnClickListener(v -> {
            String txt = id.getText().toString().trim();
            if (txt.isEmpty()) {
                Toast.makeText(this, "Saisir un ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Etudiant e = es.findById(Integer.parseInt(txt));
            if (e == null) {
                Toast.makeText(this, "Aucun etudiant avec cet ID", Toast.LENGTH_SHORT).show();
                return;
            }

            es.delete(e);
            deletedCount++;
            resultContainer.setVisibility(View.GONE);
            id.setText("");
            refreshStats(es);
            Toast.makeText(this, "Etudiant supprime", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraichir les stats quand on revient de ListeActivity
        refreshStats(new EtudiantService(this));
    }

    private void refreshStats(EtudiantService es) {
        statTotal.setText(String.valueOf(es.findAll().size()));
        statAdded.setText(String.valueOf(addedCount));
        statDeleted.setText(String.valueOf(deletedCount));
    }
}