package com.example.organizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NotatkiDialogMenu extends AppCompatDialogFragment {

    private EditText editTextDialog;
    private Button btnOkDialog;
    private Button btnCancelDialog;
    private ExampleDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        editTextDialog = view.findViewById(R.id.editTextDialog);
        btnOkDialog = view.findViewById(R.id.btnOkDialog);
        btnCancelDialog = view.findViewById(R.id.btnCancelDialog);

        btnOkDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notatka = editTextDialog.getText().toString();
                listener.applayText(notatka);
                dismiss();



            }
        });

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        builder.setView(view);


        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement ExampleDialogListener");
        }
    }

    public  interface ExampleDialogListener{

        void applayText(String notatka);
    }
}
