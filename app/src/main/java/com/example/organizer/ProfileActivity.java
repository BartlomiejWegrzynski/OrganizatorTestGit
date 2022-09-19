package com.example.organizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    //TODO zropbic okienko z obarzkiem jako zdjecie profilowe i pod imieniem da ID uzytkownika

    //TODO estetyka przyciskow wez to kurcze pozaaokrokgalj


    //TODO dodac opcje z edycja profili zeby mozna dane bylo edytowac

    private Button logout, lista,przyjacieleBtn,planDniaBTN,finanseProfilMainbtn;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    //TODO dorobic zakladke wydatki i ja dostosowac do uzytku
    //TODO klasyk z grup by i wyswietlaniem zliczonych sum

    //TODO do profili zrobic podzialke na dodanie wydatkow
    public static final String EXTRA_TEXT_ID_DO_PRZEKAZANIA = "EXTRA_TEXT_ID_DO_PRZEKAZANIA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.signOut);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        lista = (Button)findViewById(R.id.btnListaNotatek);
        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,Lista.class));
            }
        });

        przyjacieleBtn =(Button)findViewById(R.id.przyjacieleBtn);
        przyjacieleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,przyjacieleLista.class));

            }
        });

        planDniaBTN = (Button)findViewById(R.id.plan_dnia_btn);
        planDniaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivityPlanDnia(userID);

            }
        });
        finanseProfilMainbtn = (Button)findViewById(R.id.finanseProfilMain);
        finanseProfilMainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,FinanseProfil.class));
            }
        });



        user =FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextViev = (TextView) findViewById(R.id.fullName);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            // reference.child(userID).child("User2").addListenerForSingleValueEvent(new ValueEventListener() {

            //test
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String id = userID;
                    fullNameTextViev.setText(fullName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something wrong happend!",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void openActivityPlanDnia(String przekazText)
    {
        Intent intent = new Intent(this,plan_dnia.class);
        intent.putExtra(EXTRA_TEXT_ID_DO_PRZEKAZANIA,przekazText);
        startActivity(intent);
    }


}