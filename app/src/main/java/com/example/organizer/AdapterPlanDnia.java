package com.example.organizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterPlanDnia extends  RecyclerView.Adapter<AdapterPlanDnia.VieHolderPlanDnia>{

    private List<ModelListaPlanDnia> plandniaLista;
    private VieHolderPlanDnia.OnNoteListenerPlanDnia mOnNoteListener;

    public AdapterPlanDnia(List<ModelListaPlanDnia>modelPlanDniaList,VieHolderPlanDnia.OnNoteListenerPlanDnia onNoteListenerPlanDnia)
    {
        this.plandniaLista =modelPlanDniaList;
        this.mOnNoteListener=onNoteListenerPlanDnia;
    }



    @NonNull
    @Override
    public VieHolderPlanDnia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_dnia1,parent,false);
        return new VieHolderPlanDnia(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VieHolderPlanDnia holder, int position) {
        String textPlanu = plandniaLista.get(position).planDniaWpis.getTekstplanudnia();
        String godzinaPlanu = plandniaLista.get(position).planDniaWpis.getGodzinaplanudnia();
        String minutaPlanu = plandniaLista.get(position).planDniaWpis.getMinutaplanudnia();
        holder.setDataPlanDnia(textPlanu,godzinaPlanu+":"+minutaPlanu);

    }

    @Override
    public int getItemCount() {
        return plandniaLista.size();
    }


    public static class VieHolderPlanDnia extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tekstPlanu;
        private TextView godzinaPlanu;
        OnNoteListenerPlanDnia onNoteListenerPlanDnia;
        public VieHolderPlanDnia(@NonNull View itemView,OnNoteListenerPlanDnia onNoteListenerPlanDnia) {
            super(itemView);
            tekstPlanu = itemView.findViewById(R.id.tekstPlanuDnia);
            godzinaPlanu = itemView.findViewById(R.id.godzinaPlanDnia);
            this.onNoteListenerPlanDnia = onNoteListenerPlanDnia;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            onNoteListenerPlanDnia.onNoteClick(getAdapterPosition());
        }

        public  interface OnNoteListenerPlanDnia{
            void  onNoteClick(int position);


        }

        private void setDataPlanDnia(String tekstPlanu1,String godzinaPlanu1)
        {
            tekstPlanu.setText(tekstPlanu1);
            godzinaPlanu.setText(godzinaPlanu1);
        }


    }


}
