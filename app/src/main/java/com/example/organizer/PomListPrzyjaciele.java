package com.example.organizer;

public class PomListPrzyjaciele {

    String idPrzdzielone;
    ModelPrzyjaciele przykladPrzyjaciel;

    public PomListPrzyjaciele() {
    }

    public PomListPrzyjaciele(String idPrzdzielone, ModelPrzyjaciele przykladPrzyjaciel) {
        this.idPrzdzielone = idPrzdzielone;
        this.przykladPrzyjaciel = przykladPrzyjaciel;
    }

    public String getIdPrzdzielone() {
        return idPrzdzielone;
    }

    public void setIdPrzdzielone(String idPrzdzielone) {
        this.idPrzdzielone = idPrzdzielone;
    }

    public ModelPrzyjaciele getPrzykladPrzyjaciel() {
        return przykladPrzyjaciel;
    }

    public void setPrzykladPrzyjaciel(ModelPrzyjaciele przykladPrzyjaciel) {
        this.przykladPrzyjaciel = przykladPrzyjaciel;
    }
}
