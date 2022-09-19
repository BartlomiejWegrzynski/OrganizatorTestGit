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
import com.google.firebase.auth.FirebaseAuth;
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

public class Lista extends AppCompatActivity implements AdapterLista.ViewHolderLista.OnNoteListener, NotatkiDialogMenu.ExampleDialogListener {
    private Button buttonWstecz;




    private TextView idTextView;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private FirebaseFirestore db ;
    private CollectionReference userNoteRef;
    private DocumentReference userNoteRef2;


    RecyclerView recyclerView;

    List<ListaZid> notatkiLista;
    AdapterLista adapterLista;
    LinearLayoutManager layoutManager;
    ImageView dodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        initData();
        initRecyclerView();

        idTextView=(TextView) findViewById(R.id.textViewIDuzyt);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        idTextView.setText(userID);

        db = FirebaseFirestore.getInstance();
        userNoteRef = db.collection(userID).document("Notatnik").collection("Notatka");




        buttonWstecz = (Button) findViewById(R.id.button_wstecz_lista);
        buttonWstecz.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                startActivity(new Intent(Lista.this,ProfileActivity.class));


                                            }
                                        }
        );

        dodaj = (ImageView) findViewById(R.id.addImg);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });


    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            String documentId = notatkiLista.get(viewHolder.getAdapterPosition()).idNotatki;
            userNoteRef2 = db.collection(userID).document("Notatnik").collection("Notatka").document(documentId);
            userNoteRef2.delete();


        }
    };

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapterLista = new AdapterLista(notatkiLista,this);
        recyclerView.setAdapter(adapterLista);
        adapterLista.notifyDataSetChanged();
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }



    @Override
    public void onNoteClick(int position) {
        if(notatkiLista.get(position).notatka.isCheck_status())
        {
            notatkiLista.get(position).notatka.setCheck_status(false);
            String documentId = notatkiLista.get(position).idNotatki;
            userNoteRef2 = db.collection(userID).document("Notatnik").collection("Notatka").document(documentId);
            userNoteRef2.update("check_status",false);


        }else{

            notatkiLista.get(position).notatka.setCheck_status(true);
            String documentId = notatkiLista.get(position).idNotatki;
            userNoteRef2 = db.collection(userID).document("Notatnik").collection("Notatka").document(documentId);
            userNoteRef2.update("check_status",true);



        }

    }

    @Override
    public void applayText(String notatka) {

        ModelLista  modelLista= new ModelLista(notatka,false);
        userNoteRef.add(modelLista).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });

    }

    private void openDialog(){
        NotatkiDialogMenu notatkiDialogMenu = new NotatkiDialogMenu();

        notatkiDialogMenu.show(getSupportFragmentManager(),"notatkiDialog");


    }


    private void initData() {

        notatkiLista = new ArrayList<>();




    }


    @Override
    public void onStart() {
        super.onStart();


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
                adapterLista.notifyDataSetChanged();




            }


        });

    }











}