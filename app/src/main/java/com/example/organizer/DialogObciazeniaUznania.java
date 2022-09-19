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
import android.widget.EditText;

public class DialogObciazeniaUznania extends AppCompatDialogFragment {

    private EditText editTextDialogTytulObciazeniauznania;
    private EditText editTextDialogKwotaObciazeniauznania;
    private Button btnOkDialogObciazeniauznania;
    private Button btnCancelDialogObciazeniauznania;
    private DialogListenerObciazeniauznania listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_obciazenia_uznania,null);
        editTextDialogTytulObciazeniauznania = view.findViewById(R.id.editDialogWydatkiTextDialog);
        editTextDialogKwotaObciazeniauznania = view.findViewById(R.id.editDialogWydatkiKwota);
        btnCancelDialogObciazeniauznania = view.findViewById(R.id.btnDialogWydatkiCancelDialog);
        btnOkDialogObciazeniauznania = view.findViewById(R.id.btnDialogWydatkiOkDialog);

        btnCancelDialogObciazeniauznania.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View view) {
            dismiss();
        }
        });
        btnOkDialogObciazeniauznania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tytulObciazeniauznania =editTextDialogTytulObciazeniauznania.getText().toString();
                String kwotaObciazeniaSlowo=editTextDialogKwotaObciazeniauznania.getText().toString();
                double kwotaObciazeniauznania=0;
                try {
                    kwotaObciazeniauznania =Double.parseDouble(kwotaObciazeniaSlowo);
                }catch (NumberFormatException e){
                    tytulObciazeniauznania="";
                }
                listener.applayText(tytulObciazeniauznania,kwotaObciazeniauznania,0);
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
            listener = (DialogObciazeniaUznania.DialogListenerObciazeniauznania) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement DialogListenerObciazeniauznania");
        }
    }

    public interface DialogListenerObciazeniauznania{
        void applayText(String tytulObciazenia,double kwotaObciazeniauznania,int statusObciazeniaUznania);

    }

}