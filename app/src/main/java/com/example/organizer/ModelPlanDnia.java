package com.example.organizer;

public class ModelPlanDnia {

    String tekstplanudnia;
    String godzinaplanudnia;
    String minutaplanudnia;


    public ModelPlanDnia() {
    }

    public ModelPlanDnia(String tekstplanudnia, String godzinaplanudnia, String minutaplanudnia) {
        this.tekstplanudnia = tekstplanudnia;
        this.godzinaplanudnia = godzinaplanudnia;
        this.minutaplanudnia = minutaplanudnia;
    }

    public String getTekstplanudnia() {
        return tekstplanudnia;
    }

    public void setTekstplanudnia(String tekstplanudnia) {
        this.tekstplanudnia = tekstplanudnia;
    }

    public String getGodzinaplanudnia() {
        return godzinaplanudnia;
    }

    public void setGodzinaplanudnia(String godzinaplanudnia) {
        this.godzinaplanudnia = godzinaplanudnia;
    }

    public String getMinutaplanudnia() {
        return minutaplanudnia;
    }

    public void setMinutaplanudnia(String minutaplanudnia) {
        this.minutaplanudnia = minutaplanudnia;
    }
}
