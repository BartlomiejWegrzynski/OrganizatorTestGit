package com.example.organizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class DialogObciazeniaUznania_Status extends AppCompatDialogFragment {

    private TextView komunikat;
    private Button btnOdrzuc,btnPotwierdz;
    private DialogListenerObciazeniauznania_Status listener2;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_obciazenia_uznania_status,null);
        komunikat = view.findViewById(R.id.textViewDialogObciazeniauznaniaKomunikat);
        btnOdrzuc = view.findViewById(R.id.btnDialogObciazeniauznaniaStatus_Odrzucenie);
        btnPotwierdz = view.findViewById(R.id.btnDialogObciazeniauznaniaStatus_Potwierdzenie);



        btnOdrzuc.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View view) {
            listener2.applayStatus(-1);
            dismiss();
        }
        });
        btnPotwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener2.applayStatus(1);
                dismiss();
            }
        });



        builder.setView(view);
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {//TODO dokonczyc doppiero poczatek
            listener2 = (DialogObciazeniaUznania_Status.DialogListenerObciazeniauznania_Status) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement DialogListenerObciazeniauznania");
        }
    }


    public interface DialogListenerObciazeniauznania_Status{
        void applayStatus(int i);

    }
}