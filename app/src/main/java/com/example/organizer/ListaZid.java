package com.example.organizer;

public class ListaZid {

    ModelLista notatka;
    String idNotatki;

    public ListaZid() {

    }

    public ListaZid(ModelLista notatka, String idNotatki) {
        this.notatka = notatka;
        this.idNotatki = idNotatki;
    }

    public ModelLista getNotatka() {
        return notatka;
    }

    public void setNotatka(ModelLista notatka) {
        this.notatka = notatka;
    }

    public String getIdNotatki() {
        return idNotatki;
    }

    public void setIdNotatki(String idNotatki) {
        this.idNotatki = idNotatki;
    }
}
