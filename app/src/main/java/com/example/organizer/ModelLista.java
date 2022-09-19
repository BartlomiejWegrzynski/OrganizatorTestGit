package com.example.organizer;

public class ModelLista {

    String tekst1;
    boolean check_status;

    public ModelLista(String tekst1, boolean check_status) {
        this.tekst1 = tekst1;
        this.check_status = check_status;
    }
    public ModelLista() {
        //public no-arg constructor neeeded
    }

    public String getTekst1() {
        return tekst1;
    }

    public void setTekst1(String tekst1) {
        this.tekst1 = tekst1;
    }

    public boolean isCheck_status() {
        return check_status;
    }

    public void setCheck_status(boolean check_status) {
        this.check_status = check_status;
    }


}
