package com.example.organizer;

public class ModelObciazeniauznania {

    private String tytulObciazeniauznania;
    private String ktowydalObciazenieuznanie;
    private double kwota;
    private int status;
    private String idPrzyjacielaObciazeniaUznania;
    private String idDoKogoObciazenieuznanie;

    public ModelObciazeniauznania() {
    }

    public ModelObciazeniauznania(String tytulObciazeniauznania, String ktowydalObciazenieuznanie, double kwota, int status, String idPrzyjacielaObciazeniaUznania, String idDoKogoObciazenieuznanie) {
        this.tytulObciazeniauznania = tytulObciazeniauznania;
        this.ktowydalObciazenieuznanie = ktowydalObciazenieuznanie;
        this.kwota = kwota;
        this.status = status;
        this.idPrzyjacielaObciazeniaUznania = idPrzyjacielaObciazeniaUznania;
        this.idDoKogoObciazenieuznanie = idDoKogoObciazenieuznanie;
    }

    public String getTytulObciazeniauznania() {
        return tytulObciazeniauznania;
    }

    public void setTytulObciazeniauznania(String tytulObciazeniauznania) {
        this.tytulObciazeniauznania = tytulObciazeniauznania;
    }

    public String getKtowydalObciazenieuznanie() {
        return ktowydalObciazenieuznanie;
    }

    public void setKtowydalObciazenieuznanie(String ktowydalObciazenieuznanie) {
        this.ktowydalObciazenieuznanie = ktowydalObciazenieuznanie;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdPrzyjacielaObciazeniaUznania() {
        return idPrzyjacielaObciazeniaUznania;
    }

    public void setIdPrzyjacielaObciazeniaUznania(String idPrzyjacielaObciazeniaUznania) {
        this.idPrzyjacielaObciazeniaUznania = idPrzyjacielaObciazeniaUznania;
    }

    public String getIdDoKogoObciazenieuznanie() {
        return idDoKogoObciazenieuznanie;
    }

    public void setIdDoKogoObciazenieuznanie(String idDoKogoObciazenieuznanie) {
        this.idDoKogoObciazenieuznanie = idDoKogoObciazenieuznanie;
    }
}
