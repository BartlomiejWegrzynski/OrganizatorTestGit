package com.example.organizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class plan_dnia extends AppCompatActivity implements  AdapterPlanDnia.VieHolderPlanDnia.OnNoteListenerPlanDnia, TimePickerDialog.OnTimeSetListener ,NotatkiDialogMenu.ExampleDialogListener, DatePickerDialog.OnDateSetListener {

    int yearMain;
    int monthMain;
    int dayMain;
    private Button buttonWsteczPD;
    private TextView idTextViewPD;
    private TextView  textViewDataPlanDnia;
    private String zmiennagodzina;
    private String zmiennaminuta;
    private String zmiennaTresc;
    private String data;
    private ImageView viewArrowLeft,viewArrowRight;

    RecyclerView recyclerViewPlanDnia;
    List<ModelListaPlanDnia> modelPlanDniaList;
    AdapterPlanDnia adapterPlanDnia;
    LinearLayoutManager layoutManagerPlanDnia;

    ImageView dodajPlanDnia;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private FirebaseFirestore db ;
    private CollectionReference userNoteRef;
    private DocumentReference userNoteRef2;
    boolean FlagaEdycji;
    private String userIDPodane;


    //TODO polaczyc wszystko do jednego elementu gdzie zeby sie tu dostac podaje sie dane jakiego uzytkownika przegladamy i tylko jesli userAuth i przekazany userid sie zgadza to pokazuje sie przycisk dodaj innacze chowac
    //TODO doda po sirodku informacje z dniem ktory aktualnie przegladasz i dorobic do niego dialog zmiany datay
    //TODO dodac przyciski zmiany daty w prawo i w lewo (ogarnij jak dziala data -1 i +1 zeby byo z kalendarzem zgodne



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_dnia);
        Intent intent = getIntent();
        userIDPodane = intent.getStringExtra(ProfileActivity.EXTRA_TEXT_ID_DO_PRZEKAZANIA);

        initData();
        initRecyclerView();

        textViewDataPlanDnia=findViewById(R.id.textViewDataPlanDnia);
        getDateFirtTime();

        idTextViewPD=(TextView)findViewById(R.id.textViewIDuzyt_plan_dnia);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        idTextViewPD.setText(userIDPodane);

        FlagaEdycji=userID.equals(userIDPodane);

        db = FirebaseFirestore.getInstance();
        userNoteRef = db.collection(userIDPodane).document("PlanyDnia").collection(data);

        textViewDataPlanDnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        dodajPlanDnia = (ImageView) findViewById(R.id.addImg_plan_dnia);
        dodajPlanDnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimerDialog();

            }
        });

        buttonWsteczPD = (Button) findViewById(R.id.button_wstecz_plan_dnia);
        buttonWsteczPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FlagaEdycji)
                    startActivity(new Intent(plan_dnia.this,ProfileActivity.class));
                else
                {
                    openActivityProfilPrzyjaciela(userIDPodane);
                }
            }
        });


        viewArrowLeft = (ImageView) findViewById(R.id.imageViewArrowLeft);
        viewArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelPlanDniaList.clear();
                changeDate(-1);

            }
        });
        viewArrowRight = (ImageView) findViewById(R.id.imageViewArrowRight);
        viewArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modelPlanDniaList.clear();
                changeDate(1);


            }
        });
        if (FlagaEdycji==false)
        {
            dodajPlanDnia.setVisibility(View.GONE);
        }


    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            String documentId = modelPlanDniaList.get(viewHolder.getAdapterPosition()).getIdModeluPlanuDnia();
            userNoteRef2 = db.collection(userIDPodane).document("PlanyDnia").collection(data).document(documentId);
            userNoteRef2.delete();
            ladowanieDanych();



        }
    };




    private void initRecyclerView() {

        recyclerViewPlanDnia = findViewById(R.id.recyclerView_plan_dnia);
        layoutManagerPlanDnia = new LinearLayoutManager(this);
        layoutManagerPlanDnia.setOrientation(RecyclerView.VERTICAL);
        recyclerViewPlanDnia.setLayoutManager(layoutManagerPlanDnia);
        adapterPlanDnia = new AdapterPlanDnia(modelPlanDniaList,this);
        recyclerViewPlanDnia.setAdapter(adapterPlanDnia);
        adapterPlanDnia.notifyDataSetChanged();
        if (FlagaEdycji)
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewPlanDnia);


    }

    private void initData() {

        modelPlanDniaList = new ArrayList<>();

    }





    @Override
    public void onNoteClick(int position) {
        //TODO rozwinac pozniej opcje z klikaniem jak bedzie potrzeba
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        zmiennagodzina="";
        zmiennaminuta="";
        zmiennagodzina=Integer.toString(i);
        zmiennaminuta=Integer.toString(i1);
        if (zmiennaminuta.length()==1)
        {
            zmiennaminuta="0"+zmiennaminuta;
        }

        if(zmiennaminuta.isEmpty()|zmiennagodzina.isEmpty())
        {
            Toast.makeText(this, "Anulowano", Toast.LENGTH_SHORT).show();
        }
        else
        {
            openDialog();
        }
    }

    private void openDialog(){
        NotatkiDialogMenu notatkiDialogMenu = new NotatkiDialogMenu();

        notatkiDialogMenu.show(getSupportFragmentManager(),"notatkiDialog");


    }

    @Override
    public void applayText(String notatka) {
        zmiennaTresc="";
        zmiennaTresc=notatka;
        if (notatka.isEmpty())
        {
            Toast.makeText(this, "Anulowano", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ModelPlanDnia pomocniczaPlanDnia = new ModelPlanDnia(zmiennaTresc,zmiennagodzina,zmiennaminuta);
            userNoteRef.add(pomocniczaPlanDnia);
            ladowanieDanych();
        }

    }

    public void openTimerDialog()
    {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"time_picker");
    }


    @Override
    public void onStart() {
        super.onStart();
        ladowanieDanych();



    }
    public void ladowanieDanych()
    {
        modelPlanDniaList.clear();
        adapterPlanDnia.notifyDataSetChanged();
        userNoteRef = db.collection(userIDPodane).document("PlanyDnia").collection(data);
        userNoteRef.orderBy("godzinaplanudnia").orderBy("minutaplanudnia").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                {

                    String idWpisuPlanuDnia = documentSnapshot.getId();
                    ModelPlanDnia modelPlanDnia = documentSnapshot.toObject(ModelPlanDnia.class);

                    String tresc = modelPlanDnia.getTekstplanudnia();
                    String godzina = modelPlanDnia.getGodzinaplanudnia();
                    String minuta = modelPlanDnia.getMinutaplanudnia();

                    modelPlanDniaList.add(new ModelListaPlanDnia(new ModelPlanDnia(tresc,godzina,minuta),idWpisuPlanuDnia));



                }
                adapterPlanDnia.notifyDataSetChanged();




            }


        });
    }

    public void getDateFirtTime()
    {
        Calendar c = Calendar.getInstance();
        data = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        textViewDataPlanDnia.setText(data);
        yearMain = c.get(Calendar.YEAR);
        monthMain = c.get(Calendar.MONTH);
        dayMain = c.get(Calendar.DAY_OF_MONTH);
    }
    public void  changeDate(int ile)
    {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,yearMain);
        c.set(Calendar.MONTH,monthMain);
        c.set(Calendar.DAY_OF_MONTH,dayMain);
        c.add(Calendar.DATE,ile);
        data = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        textViewDataPlanDnia.setText(data);
        yearMain = c.get(Calendar.YEAR);
        monthMain = c.get(Calendar.MONTH);
        dayMain = c.get(Calendar.DAY_OF_MONTH);
        ladowanieDanych();



    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        data = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDataPlanDnia.setText(data);
        yearMain = calendar.get(Calendar.YEAR);
        monthMain = calendar.get(Calendar.MONTH);
        dayMain = calendar.get(Calendar.DAY_OF_MONTH);
        ladowanieDanych();

    }

    public void openActivityProfilPrzyjaciela(String przekazText)
    {
        Intent intent = new Intent(this,ProfilPrzyjaciela.class);
        intent.putExtra(przyjacieleLista.EXTRA_TEXT,przekazText);
        startActivity(intent);
    }
}