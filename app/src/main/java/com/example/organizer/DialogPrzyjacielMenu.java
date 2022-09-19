package com.example.organizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogPrzyjacielMenu extends AppCompatDialogFragment {
    private EditText editTextPrzyjaciel;
    private Button btnOKDialogPrzyjaciel;
    private Button btnCancelDialoPrzyjaciel;
    private PrzyjacielDialogListener listenerPrzyjaciel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_przyjaciel,null);
        editTextPrzyjaciel = view.findViewById(R.id.editTextDialog_przyjaciel);
        btnOKDialogPrzyjaciel = view.findViewById(R.id.btnOkDialog_przyjaciel);
        btnCancelDialoPrzyjaciel = view.findViewById(R.id.btnCancelDialog_przyjaciel);
        btnOKDialogPrzyjaciel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idprzyjaciel = editTextPrzyjaciel.getText().toString();
                listenerPrzyjaciel.applayText(idprzyjaciel);
                dismiss();
            }
        });
        btnCancelDialoPrzyjaciel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setView(view);
        return  builder.create();
    }

    public  interface PrzyjacielDialogListener{

        void applayText(String idPrzyjaciela );
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenerPrzyjaciel = (DialogPrzyjacielMenu.PrzyjacielDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement ExampleDialogListener");
        }
    }


}
