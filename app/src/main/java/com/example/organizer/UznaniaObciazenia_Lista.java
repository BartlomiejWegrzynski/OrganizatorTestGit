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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class UznaniaObciazenia_Lista extends AppCompatActivity implements AdapterObciazeniauznania.ViewHolderObciazeniauznania.OnNoteListenerObciazeniauznania ,DialogObciazeniaUznania.DialogListenerObciazeniauznania,DialogObciazeniaUznania_Status.DialogListenerObciazeniauznania_Status{
//TODO zabawa w wpisaniem wszytkiegop adaptery i obiekty porobione i jazda


    private Button btnWsteczObciazeniaiUznania;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private FirebaseFirestore db ;
    private CollectionReference userNoteRef;
    private DocumentReference userNoteRef2;
    private CollectionReference userNoteRef3;
    boolean FlagaEdycji;
    private String userIDPodane;
    private TextView textViewIDObciazeniaUznania;
    private ImageView imageViewDodajObciazeniaUznania;
    private String ktowydalobciazenieuznanie;
    private int pozycjawTabeli;


    RecyclerView recyclerViewUznaniaObciazenia;
    List<ModeLlistaObciazeniauznania> modeLlistaObciazeniauznaniaList;
    AdapterObciazeniauznania adapterObciazeniauznania_Lista;
    LinearLayoutManager layoutManagerUznaniaObciazenia_Lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uznania_obciazenia_lista);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    ktowydalobciazenieuznanie = userProfile.fullName;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UznaniaObciazenia_Lista.this,"Something wrong happend!",Toast.LENGTH_LONG).show();
                ktowydalobciazenieuznanie="NoName";

            }
        });

        Intent intent = getIntent();
        userIDPodane = intent.getStringExtra(ProfileActivity.EXTRA_TEXT_ID_DO_PRZEKAZANIA);
        FlagaEdycji=userID.equals(userIDPodane);
        initData();
        initRecyclerView();



        textViewIDObciazeniaUznania = (TextView) findViewById(R.id.textViewIDuzyt_uznania_obciazenia_lista);
        textViewIDObciazeniaUznania.setText(userIDPodane);


        db = FirebaseFirestore.getInstance();
        if (FlagaEdycji==false)
            userNoteRef = db.collection(userIDPodane).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("niepotwierdzoen");
        else
            userNoteRef = db.collection(userID).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("niepotwierdzoen");

        imageViewDodajObciazeniaUznania =(ImageView)findViewById(R.id.addImg_uznania_obciazenia_lista);
        if (FlagaEdycji==true)
            imageViewDodajObciazeniaUznania.setVisibility(View.GONE);
        else
            imageViewDodajObciazeniaUznania.setVisibility(View.VISIBLE);
        imageViewDodajObciazeniaUznania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();


            }
        });




        btnWsteczObciazeniaiUznania =findViewById(R.id.button_wstecz_uznania_obciazenia_lista);
        btnWsteczObciazeniaiUznania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FlagaEdycji)
                    startActivity(new Intent(UznaniaObciazenia_Lista.this,ProfileActivity.class));
                else
                {
                    openActivityProfilPrzyjaciela(userIDPodane);
                }
            }
        });



    }

    private void initRecyclerView() {

        recyclerViewUznaniaObciazenia = findViewById(R.id.recyclerView_uznania_obciazenia_lista);
        layoutManagerUznaniaObciazenia_Lista = new LinearLayoutManager(this);
        layoutManagerUznaniaObciazenia_Lista.setOrientation(RecyclerView.VERTICAL);
        recyclerViewUznaniaObciazenia.setLayoutManager(layoutManagerUznaniaObciazenia_Lista);
        adapterObciazeniauznania_Lista = new AdapterObciazeniauznania(modeLlistaObciazeniauznaniaList,this);
        recyclerViewUznaniaObciazenia.setAdapter(adapterObciazeniauznania_Lista);
        adapterObciazeniauznania_Lista.notifyDataSetChanged();
        //TODO dorobic tutaj przesuwanie i usuwanie itemow
        if (!FlagaEdycji)
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewUznaniaObciazenia);


    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            String documentId = modeLlistaObciazeniauznaniaList.get(viewHolder.getAdapterPosition()).getIdObciazeniauznania();
            userNoteRef2 = db.collection(userIDPodane).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("niepotwierdzoen").document(documentId);
            userNoteRef2.delete();
            ladowanieDanych();



        }
    };

    private void initData() {

        modeLlistaObciazeniauznaniaList = new ArrayList<>();

    }

    public void openActivityProfilPrzyjaciela(String przekazText)
    {
        Intent intent = new Intent(this,ProfilPrzyjaciela.class);
        intent.putExtra(przyjacieleLista.EXTRA_TEXT,przekazText);
        startActivity(intent);
    }

    @Override
    public void onNoteClick(int position) {
        if (FlagaEdycji)
        {
            pozycjawTabeli=position;
            openDialogStatus();
        }



    }

    @Override
    public void applayText(String tytulObciazenia, double kwotaObciazeniauznania, int statusObciazeniaUznania) {
        if (tytulObciazenia.length()==0)
        {
            Toast.makeText(this, "Anulowano", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ModelObciazeniauznania pomocniczaObciazeniauznania = new ModelObciazeniauznania(tytulObciazenia,ktowydalobciazenieuznanie,kwotaObciazeniauznania,statusObciazeniaUznania,userID,userIDPodane);
            userNoteRef.add(pomocniczaObciazeniauznania);
            ladowanieDanych();
        }

    }

    private void openDialog()
    {
        DialogObciazeniaUznania dialogObciazeniaUznania = new DialogObciazeniaUznania();
        dialogObciazeniaUznania.show(getSupportFragmentManager(),"UznaniaObciazeniaDialog");
    }
    private void openDialogStatus()
    {
        DialogObciazeniaUznania_Status dialogObciazeniaUznania_status = new DialogObciazeniaUznania_Status();
        dialogObciazeniaUznania_status.show(getSupportFragmentManager(),"UznaniaObciazeniaDialog_Status");
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

    @Override
    public void applayStatus(int i) {

        ModelObciazeniauznania pomocnicza = modeLlistaObciazeniauznaniaList.get(pozycjawTabeli).Obciazeniauznania;
        pomocnicza.setStatus(i);
        String documentIdstatus=modeLlistaObciazeniauznaniaList.get(pozycjawTabeli).idObciazeniauznania;
        userNoteRef3 = db.collection(pomocnicza.getIdPrzyjacielaObciazeniaUznania()).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("zakonczone");
        userNoteRef3.add(pomocnicza);
        userNoteRef3 = db.collection(pomocnicza.getIdDoKogoObciazenieuznanie()).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("zakonczone");
        userNoteRef3.add(pomocnicza);
        userNoteRef2 = db.collection(userID).document("Wydatki").collection("ObciazeniaUznania").document("AktualneWydatki").collection("niepotwierdzoen").document(documentIdstatus);
        userNoteRef2.delete();
        ladowanieDanych();


    }
}