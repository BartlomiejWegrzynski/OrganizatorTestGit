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

import com.google.android.gms.tasks.OnFailureListener;
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

public class przyjacieleLista extends AppCompatActivity implements AdapterPrzyjaciele.ViewHolder.OnNoteListener , DialogPrzyjacielMenu.PrzyjacielDialogListener{

    public static final String EXTRA_TEXT = "EXTRA_TEXT";
    RecyclerView recyclerView;
    AdapterPrzyjaciele adapterPrzyjaciele;
    LinearLayoutManager layoutManager;
    List<PomListPrzyjaciele>listaPrzyjaciolzID;
    Button btnWstecz;
    ImageView dodajPrzyjaciel;
    String userID;
    private TextView textViewPrzyjacieleLista;

    private FirebaseFirestore db;
    private DatabaseReference reference;
    private CollectionReference przyjacieleRef;
    private DocumentReference przyjacieleRef2;
    private FirebaseUser user;
    private DatabaseReference pomReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przyjaciele_lista);
        initData();
        initRecyclerView();


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference =FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();
        textViewPrzyjacieleLista=findViewById(R.id.textViewIDuzytprzyjacielelista);
        textViewPrzyjacieleLista.setText(userID);
        db=FirebaseFirestore.getInstance();
        przyjacieleRef = db.collection(userID).document("PrzyjacieleLista").collection("Przyjaciel");


        btnWstecz = (Button) findViewById(R.id.button_wstecz_Przyjaciele);
        btnWstecz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(przyjacieleLista.this,ProfileActivity.class));

            }
        });

        dodajPrzyjaciel = (ImageView) findViewById(R.id.addImgPrzyjaciel);
        dodajPrzyjaciel.setOnClickListener(new View.OnClickListener() {
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
            String documentID = listaPrzyjaciolzID.get(viewHolder.getAdapterPosition()).idPrzdzielone;
            przyjacieleRef2 =db.collection(userID).document("PrzyjacieleLista").collection("Przyjaciel").document(documentID);
            przyjacieleRef2.delete();
        }
    };

    private void initData() {
        listaPrzyjaciolzID = new ArrayList<>();

    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerViewPrzyjaciele);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapterPrzyjaciele = new AdapterPrzyjaciele(listaPrzyjaciolzID,this);
        recyclerView.setAdapter(adapterPrzyjaciele);
        adapterPrzyjaciele.notifyDataSetChanged();
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onNoteClick(int position) {
        String nazwaPokilknieciu = listaPrzyjaciolzID.get(position).przykladPrzyjaciel.getId();
        openActivityProfilPrzyjaciela(nazwaPokilknieciu);


    }


    @Override
    public void applayText(String idPrzyjaciela) {



        pomReference = FirebaseDatabase.getInstance().getReference("Users");
        pomReference.child(idPrzyjaciela).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile!=null)
                {
                    String nazwaPrzyjaciela = userProfile.fullName;
                    ModelPrzyjaciele modelPrzyjaciele = new ModelPrzyjaciele(idPrzyjaciela,nazwaPrzyjaciela);
                    przyjacieleRef.add(modelPrzyjaciele).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(przyjacieleLista.this, "Coś poszło nie tak Spróbuj Ponownie", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                else
                {
                    Toast.makeText(przyjacieleLista.this, "Nie ma takiego użytkownika", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(przyjacieleLista.this, "Cos poszlo nie tak", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void openDialog()
    {
        DialogPrzyjacielMenu dialogPrzyjacielMenu = new DialogPrzyjacielMenu();
        dialogPrzyjacielMenu.show(getSupportFragmentManager(),"PrzyjacieleDialog");
    }

    @Override
    public void onStart() {

        super.onStart();
        przyjacieleRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                listaPrzyjaciolzID.clear();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String idPrzydzielonePrzyjaciela = documentSnapshot.getId();

                    ModelPrzyjaciele modelPrzyjaciele = documentSnapshot.toObject(ModelPrzyjaciele.class);
                    String nazwaPrzyjaciela = modelPrzyjaciele.getNazwa();
                    String idPrzyjaciela = modelPrzyjaciele.getId();

                    listaPrzyjaciolzID.add(new PomListPrzyjaciele(idPrzydzielonePrzyjaciela,new ModelPrzyjaciele(idPrzyjaciela,nazwaPrzyjaciela)));
                }
                adapterPrzyjaciele.notifyDataSetChanged();

            }
        });

    }

    public void openActivityProfilPrzyjaciela(String przekazText)
    {
        Intent intent = new Intent(this,ProfilPrzyjaciela.class);
        intent.putExtra(EXTRA_TEXT,przekazText);
        startActivity(intent);
    }
}
