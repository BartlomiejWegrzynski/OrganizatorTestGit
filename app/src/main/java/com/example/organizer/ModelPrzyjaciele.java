package com.example.organizer;

public class ModelPrzyjaciele {

    String id;
    String nazwa;

    public ModelPrzyjaciele(String id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public ModelPrzyjaciele() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
