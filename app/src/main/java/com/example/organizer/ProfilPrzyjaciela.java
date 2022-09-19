package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilPrzyjaciela extends AppCompatActivity {

    //TODO zrobic profil przyjaciela zobacz sobie activity i odwozrouj co ma sie dziac jak cos spacerek i kombinuj

    private  TextView textViewIDprzyjaciela;
    private Button btnPowrot;
    private Button btnPlanDniaProfilPrzyjaciel;
    private Button btnObciazeniaiUznaniaProfilPrzyjaciel;
    private Button btnHistoriaWydatkow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_przyjaciela);
        Intent intent = getIntent();
        String idPrzyjaciela = intent.getStringExtra(przyjacieleLista.EXTRA_TEXT);
        textViewIDprzyjaciela =(TextView) findViewById(R.id.textViewIDPrzyjacielaProfil);
        textViewIDprzyjaciela.setText(idPrzyjaciela);
        btnPowrot = (Button) findViewById(R.id.powrtoprofilprzyjaciel);
        btnPowrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfilPrzyjaciela.this, ProfileActivity.class));

            }
        });
        btnPlanDniaProfilPrzyjaciel=(Button) findViewById(R.id.plan_dnia_btnprofilprzyjaciel);
        btnPlanDniaProfilPrzyjaciel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityPlanDniaPrzyjaciel(idPrzyjaciela);

            }
        });

        btnObciazeniaiUznaniaProfilPrzyjaciel = (Button) findViewById(R.id.obicazeniaiUznaniaProfilPrzyjaciel);
        btnObciazeniaiUznaniaProfilPrzyjaciel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityObciazeniaiUznaniaLista(idPrzyjaciela);
            }
        });

        btnHistoriaWydatkow = (Button) findViewById(R.id.historiawydatkowprofilprzyjaciel);
        btnHistoriaWydatkow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityHistoriaWydatkow(idPrzyjaciela);

            }
        });

    }

    public void openActivityPlanDniaPrzyjaciel(String przekazText)
    {
        Intent intent = new Intent(this,plan_dnia.class);
        intent.putExtra(ProfileActivity.EXTRA_TEXT_ID_DO_PRZEKAZANIA,przekazText);
        startActivity(intent);
    }

    public void openActivityObciazeniaiUznaniaLista(String przekazText2)
    {
        Intent intent = new Intent(this,UznaniaObciazenia_Lista.class);
        intent.putExtra(ProfileActivity.EXTRA_TEXT_ID_DO_PRZEKAZANIA,przekazText2);
        startActivity(intent);
    }

    public void openActivityHistoriaWydatkow(String przekazText2)
    {
        Intent intent = new Intent(this,Historia_Wydatkow1.class);
        intent.putExtra(ProfileActivity.EXTRA_TEXT_ID_DO_PRZEKAZANIA,przekazText2);
        startActivity(intent);
    }
}