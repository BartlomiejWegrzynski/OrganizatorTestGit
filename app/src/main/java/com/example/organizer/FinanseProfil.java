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

public class FinanseProfil extends AppCompatActivity {

    private  Button finanseProfilPowrotBtn,finanseProfilOczekująceBtn,finanseProfilHistoriaWydatkow;


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private TextView fullNameTextViev,idTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanse_profil);

        user =FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        fullNameTextViev=(TextView)findViewById(R.id.finanse_profil_nazwauser);
        idTextView=(TextView)findViewById(R.id.finanse_profil_idUser);
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
                    idTextView.setText(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FinanseProfil.this,"Something wrong happend!",Toast.LENGTH_LONG).show();

            }
        });



        finanseProfilPowrotBtn=(Button)findViewById(R.id.finanse_profil_powrot_btn);
        finanseProfilPowrotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinanseProfil.this,ProfileActivity.class));
            }
        });

        finanseProfilOczekująceBtn=(Button) findViewById(R.id.finanse_profil_oczekujace_btn);
        finanseProfilOczekująceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityObciazeniaiUznaniaLista(userID);
            }
        });

        finanseProfilHistoriaWydatkow = (Button)findViewById(R.id.finanse_profil_historia_wydatków);
        finanseProfilHistoriaWydatkow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityHistoriaWydatkow(userID);

            }
        });



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