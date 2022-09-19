package com.example.organizer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterObciazeniauznania extends  RecyclerView.Adapter<AdapterObciazeniauznania.ViewHolderObciazeniauznania>{

    private List<ModeLlistaObciazeniauznania> obciazeniauznaniaList;
    private ViewHolderObciazeniauznania.OnNoteListenerObciazeniauznania mOnNoteListener;
    public AdapterObciazeniauznania(List<ModeLlistaObciazeniauznania>modeLlistaObciazeniauznania,ViewHolderObciazeniauznania.OnNoteListenerObciazeniauznania onNoteListenerObciazeniauznania)
    {
        this.obciazeniauznaniaList = modeLlistaObciazeniauznania;
        this.mOnNoteListener = onNoteListenerObciazeniauznania;
    }
    @NonNull
    @Override
    public ViewHolderObciazeniauznania onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uznaniaobciazenia_lista,parent,false);
        return new ViewHolderObciazeniauznania(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderObciazeniauznania holder, int position) {
        String textTytuluObciazeniauznania =obciazeniauznaniaList.get(position).Obciazeniauznania.getTytulObciazeniauznania();
        String ktowydalObciazenieuznanie = obciazeniauznaniaList.get(position).Obciazeniauznania.getKtowydalObciazenieuznanie();
        Double kwotaObiciazeniauznania = obciazeniauznaniaList.get(position).Obciazeniauznania.getKwota();
        int statusObciazeniauznania = obciazeniauznaniaList.get(position).Obciazeniauznania.getStatus();
        holder.setDataObciazeniauznania(textTytuluObciazeniauznania,ktowydalObciazenieuznanie,kwotaObiciazeniauznania,statusObciazeniauznania);

    }

    @Override
    public int getItemCount() {
        return obciazeniauznaniaList.size();
    }

    public static class ViewHolderObciazeniauznania extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewTytulObciazeniauznania;
        private  TextView textViewKwotaObciazeniauznania;
        OnNoteListenerObciazeniauznania onNoteListenerObciazeniauznania;
        public ViewHolderObciazeniauznania(@NonNull View itemView,OnNoteListenerObciazeniauznania onNoteListenerObciazeniauznania) {
            super(itemView);
            textViewTytulObciazeniauznania = itemView.findViewById(R.id.textViewitemUznaniaiobciazenia_tytul);
            textViewKwotaObciazeniauznania = itemView.findViewById(R.id.textViewitemUznaniaiobciazenia_kwota);
            this.onNoteListenerObciazeniauznania = onNoteListenerObciazeniauznania;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListenerObciazeniauznania.onNoteClick(getAdapterPosition());
        }

        public interface OnNoteListenerObciazeniauznania
        {
            void onNoteClick(int position);
        }

        private void setDataObciazeniauznania(String tytulObciazeniauznania1,String ktowydalObciazenieuznaine1, double kwotaObciazeniauznania1, int statusObciazeniauznania1)
        {
            String kwota = Double.toString(kwotaObciazeniauznania1);
            if (ktowydalObciazenieuznaine1.equals(""))
                textViewTytulObciazeniauznania.setText(tytulObciazeniauznania1);
            else
                textViewTytulObciazeniauznania.setText(ktowydalObciazenieuznaine1+": "+tytulObciazeniauznania1);
            textViewKwotaObciazeniauznania.setText(kwota);
            if (statusObciazeniauznania1>0)
            {
                textViewTytulObciazeniauznania.setBackgroundResource(R.color.kolorObciazeniauznaniaGood);
                textViewKwotaObciazeniauznania.setBackgroundResource(R.color.kolorObciazeniauznaniaGood);
            }
            else if (statusObciazeniauznania1<0) {
                textViewTytulObciazeniauznania.setBackgroundResource(R.color.kolorObciazeniauznaniaBad);
                textViewKwotaObciazeniauznania.setBackgroundResource(R.color.kolorObciazeniauznaniaBad);
            } else
            {
                textViewTytulObciazeniauznania.setBackgroundResource(R.color.kolornotatka);
                textViewKwotaObciazeniauznania.setBackgroundResource(R.color.kolornotatka);
            }
        }
    }
}
