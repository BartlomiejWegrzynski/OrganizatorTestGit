package com.example.organizer;

public class ModeLlistaObciazeniauznania extends  ModelObciazeniauznania {
    ModelObciazeniauznania Obciazeniauznania;
    String idObciazeniauznania;

    public ModeLlistaObciazeniauznania() {
    }

    public ModeLlistaObciazeniauznania(ModelObciazeniauznania obciazeniauznania, String idObciazeniauznania) {
        Obciazeniauznania = obciazeniauznania;
        this.idObciazeniauznania = idObciazeniauznania;
    }

    public ModelObciazeniauznania getObciazeniauznania() {
        return Obciazeniauznania;
    }

    public void setObciazeniauznania(ModelObciazeniauznania obciazeniauznania) {
        Obciazeniauznania = obciazeniauznania;
    }

    public String getIdObciazeniauznania() {
        return idObciazeniauznania;
    }

    public void setIdObciazeniauznania(String idObciazeniauznania) {
        this.idObciazeniauznania = idObciazeniauznania;
    }
}

