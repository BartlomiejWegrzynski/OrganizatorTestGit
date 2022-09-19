package com.example.organizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Historia_Wydatkow1 extends AppCompatActivity implements AdapterObciazeniauznania.ViewHolderObciazeniauznania.OnNoteListenerObciazeniauznania{


    private Button btnWsteczHistoria_Wydatkow;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    boolean FlagaEdycji;
    private String userIDPodane;
    boolean FlagaUzytkownika;


    private FirebaseFirestore db ;
    private CollectionReference userNoteRef;


    RecyclerView recyclerViewUznaniaObciazenia;
    List<ModeLlistaObciazeniauznania> modeLlistaObciazeniauznaniaList;
    AdapterObciazeniauznania adapterObciazeniauznania_Lista;
    LinearLayoutManager layoutManagerUznaniaObciazenia_Lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_wydatkow1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        Intent intent = getIntent();
        userIDPodane = intent.getStringExtra(ProfileActivity.EXTRA_TEXT_ID_DO_PRZEKAZANIA);
        FlagaEdycji=userID.equals(userIDPodane);

        initData();
        initRecyclerView();

        db = FirebaseFirestore.getInstance();
        if (FlagaEdycji==false)
            userNoteRef = db.collection(userIDPodane).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("zakonczone");
        else
            userNoteRef = db.collection(userID).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("zakonczone");



        btnWsteczHistoria_Wydatkow=(Button) findViewById(R.id.button_wstecz_historia_wydatkow);
        btnWsteczHistoria_Wydatkow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (FlagaEdycji)
                    startActivity(new Intent(Historia_Wydatkow1.this,ProfileActivity.class));
                else
                {
                    openActivityProfilPrzyjaciela(userIDPodane);
                }

            }
        });

    }

    @Override
    public void onNoteClick(int position) {

    }

    public void openActivityProfilPrzyjaciela(String przekazText)
    {
        Intent intent = new Intent(this,ProfilPrzyjaciela.class);
        intent.putExtra(przyjacieleLista.EXTRA_TEXT,przekazText);
        startActivity(intent);
    }

    private void initRecyclerView() {

        recyclerViewUznaniaObciazenia = findViewById(R.id.recyclerView_historia_wydatkow);
        layoutManagerUznaniaObciazenia_Lista = new LinearLayoutManager(this);
        layoutManagerUznaniaObciazenia_Lista.setOrientation(RecyclerView.VERTICAL);
        recyclerViewUznaniaObciazenia.setLayoutManager(layoutManagerUznaniaObciazenia_Lista);
        adapterObciazeniauznania_Lista = new AdapterObciazeniauznania(modeLlistaObciazeniauznaniaList,this);
        recyclerViewUznaniaObciazenia.setAdapter(adapterObciazeniauznania_Lista);
        adapterObciazeniauznania_Lista.notifyDataSetChanged();

    }

    private void initData() {

        modeLlistaObciazeniauznaniaList = new ArrayList<>();

    }

    @Override
    public void onStart() {
        super.onStart();
        ladowanieDanych();



    }

    public void ladowanieDanych()
    {
        modeLlistaObciazeniauznaniaList.clear();
        adapterObciazeniauznania_Lista.notifyDataSetChanged();
        if ((!FlagaEdycji))
        {
            userNoteRef.whereEqualTo("idPrzyjacielaObciazeniaUznania",userID)
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                        @Override

                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                            if (error!=null)
                            {
                                return;
                            }
                            modeLlistaObciazeniauznaniaList.clear();
                            adapterObciazeniauznania_Lista.notifyDataSetChanged();

                            for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                            {

                                String idWpisuPlanuDnia = documentSnapshot.getId();
                                ModelObciazeniauznania modelObciazeniauznania = documentSnapshot.toObject(ModelObciazeniauznania.class);

                                String tytul = modelObciazeniauznania.getTytulObciazeniauznania();
                                String idPrzyjaciela = modelObciazeniauznania.getIdPrzyjacielaObciazeniaUznania();
                                String idDokogo = modelObciazeniauznania.getIdDoKogoObciazenieuznanie();
                                double kwota = modelObciazeniauznania.getKwota();
                                int status = modelObciazeniauznania.getStatus();
                                String ktowydalObciazenieuznanie2 ="";

                                modeLlistaObciazeniauznaniaList.add(new ModeLlistaObciazeniauznania(new ModelObciazeniauznania(tytul,ktowydalObciazenieuznanie2,kwota,status,idPrzyjaciela,idDokogo),idWpisuPlanuDnia));



                            }
                            adapterObciazeniauznania_Lista.notifyDataSetChanged();




                        }


                    });
        }
        else
        {
            userNoteRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override

                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                    if (error!=null)
                    {
                        return;
                    }
                    modeLlistaObciazeniauznaniaList.clear();
                    adapterObciazeniauznania_Lista.notifyDataSetChanged();

                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                    {

                        String idWpisuPlanuDnia = documentSnapshot.getId();
                        ModelObciazeniauznania modelObciazeniauznania = documentSnapshot.toObject(ModelObciazeniauznania.class);

                        String tytul = modelObciazeniauznania.getTytulObciazeniauznania();
                        String idPrzyjaciela = modelObciazeniauznania.getIdPrzyjacielaObciazeniaUznania();
                        String idDokogo = modelObciazeniauznania.getIdDoKogoObciazenieuznanie();
                        double kwota = modelObciazeniauznania.getKwota();
                        int status = modelObciazeniauznania.getStatus();
                        String ktowydalObciazenieuznanie2 = modelObciazeniauznania.getKtowydalObciazenieuznanie();

                        modeLlistaObciazeniauznaniaList.add(new ModeLlistaObciazeniauznania(new ModelObciazeniauznania(tytul,ktowydalObciazenieuznanie2,kwota,status,idPrzyjaciela,idDokogo),idWpisuPlanuDnia));



                    }
                    adapterObciazeniauznania_Lista.notifyDataSetChanged();




                }


            });
        }

    }

}
