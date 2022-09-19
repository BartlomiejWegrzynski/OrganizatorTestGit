package com.example.organizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class przyjaciel_podglad_planu extends AppCompatActivity implements AdapterLista.ViewHolderLista.OnNoteListener {


    TextView textViewID;
    private Button buttonWsteczPrzyjacielPodgladPlan;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private FirebaseFirestore db ;
    private CollectionReference userNoteRef;
    private DocumentReference userNoteRef2;

    RecyclerView recyclerView;

    List<ListaZid> notatkiLista;
    AdapterLista adapterLista2;
    LinearLayoutManager layoutManager;
    ImageView dodaj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przyjaciel_podglad_planu);
        Intent intent = getIntent();
        String idPrzyjaciela = intent.getStringExtra(przyjacieleLista.EXTRA_TEXT);
        textViewID =(TextView)findViewById(R.id.textViewIDuzyt_podglad_przyjaciel);
        textViewID.setText(idPrzyjaciela);
        setContentView(R.layout.activity_przyjaciel_podglad_planu);

        initData();

        initRecyclerView();

        reference = FirebaseDatabase.getInstance().getReference(idPrzyjaciela);
        db = FirebaseFirestore.getInstance();
        userNoteRef = db.collection(idPrzyjaciela).document("Notatnik").collection("Notatka");

        buttonWsteczPrzyjacielPodgladPlan = (Button) findViewById(R.id.button_wstecz_podglad_przyjaciel);
        buttonWsteczPrzyjacielPodgladPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(przyjaciel_podglad_planu.this,przyjacieleLista.class));
            }
        });




    }


    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView_podglad_przyjaciel);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapterLista2 = new AdapterLista(notatkiLista,this);
        recyclerView.setAdapter(adapterLista2);
        adapterLista2.notifyDataSetChanged();


    }



    private void initData() {

        notatkiLista = new ArrayList<>();




    }



    @Override
    public void onNoteClick(int position) {

    }


    @Override
    public void onStart() {
        super.onStart();


        //TODO W przyszlosci moze tu byc przeszkoda z zaciaganiem danych bo nie wiem czy nie bedzie sie odpalac zapytanie caly czas i pobierac danych a to powinno tylko raz odpalic chyba albo i nie i czyszczenie calej tabeli tutaj bedzie i odczyt ponowny
        userNoteRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {
                    return;
                }
                notatkiLista.clear();
                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                {
                    String idNotatki = documentSnapshot.getId();
                    ModelLista modelLista1 = documentSnapshot.toObject(ModelLista.class);

                    String notatka = modelLista1.getTekst1();
                    boolean statusnotatki = modelLista1.isCheck_status();

                    notatkiLista.add(new ListaZid(new ModelLista(notatka,statusnotatki),idNotatki));


                }
                adapterLista2.notifyDataSetChanged();




            }


        });

    }


}