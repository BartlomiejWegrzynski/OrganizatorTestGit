package com.example.organizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterPrzyjaciele  extends  RecyclerView.Adapter<AdapterPrzyjaciele.ViewHolder>{

    private List<PomListPrzyjaciele> przyjacieleListzID;
    private ViewHolder.OnNoteListener mOnNoteListener;
    public AdapterPrzyjaciele(List<PomListPrzyjaciele>przyjacieleListzID,ViewHolder.OnNoteListener onNoteListener)
    {
        this.przyjacieleListzID =przyjacieleListzID;
        this.mOnNoteListener = onNoteListener;

    }

    @NonNull
    @Override
    public AdapterPrzyjaciele.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_przyjaciel,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nazwaPrzyjaciel = przyjacieleListzID.get(position).przykladPrzyjaciel.getNazwa();
        holder.setDataPrzyjaciel(nazwaPrzyjaciel);

    }

    @Override
    public int getItemCount() {
        return przyjacieleListzID.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textNazaPrzyjaciela;
        OnNoteListener onNoteListener;


        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            textNazaPrzyjaciela = itemView.findViewById(R.id.nazwaPrzyjaciela);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public void setDataPrzyjaciel(String nazwaPrzyjaciel)
        {
            textNazaPrzyjaciela.setText(nazwaPrzyjaciel);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }

        public interface OnNoteListener {
            void  onNoteClick(int position);

        }
    }
}
