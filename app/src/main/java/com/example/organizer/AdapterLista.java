package com.example.organizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.ViewHolderLista> {

    private List<ListaZid> notatkiLista;
    private ViewHolderLista.OnNoteListener mOnNoteListener;
    public AdapterLista(List<ListaZid>notatkiLista, ViewHolderLista.OnNoteListener onNoteListener)
    {
        this.notatkiLista = notatkiLista;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public AdapterLista.ViewHolderLista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista1,parent,false);
        return new ViewHolderLista(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLista.ViewHolderLista holder, int position) {

        String textNotatki = notatkiLista.get(position).notatka.getTekst1();
        boolean statusCheck = notatkiLista.get(position).notatka.isCheck_status();

        holder.setData(textNotatki,statusCheck);

    }




    @Override
    public int getItemCount() {
        return notatkiLista.size();
    }

    public static class ViewHolderLista extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tekstNotatki;
        private ImageView checkLista;
        OnNoteListener onNoteListener;

        public ViewHolderLista(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            tekstNotatki=itemView.findViewById(R.id.tekstNotatki);
            checkLista=itemView.findViewById(R.id.checkNotatki);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());



        }

        public  interface OnNoteListener{
            void  onNoteClick(int position);


        }
        private void setData(String Notatka,boolean check_status) {

            tekstNotatki.setText(Notatka);
            if (check_status)
            {
                checkLista.setImageResource(R.drawable.check);
            }
            else {
                checkLista.setImageResource(R.drawable.not_check);

            }
        }
    }
}

