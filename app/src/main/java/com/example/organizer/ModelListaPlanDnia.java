package com.example.organizer;

public class ModelListaPlanDnia extends  ModelPlanDnia {

    ModelPlanDnia planDniaWpis;
    String idModeluPlanuDnia;

    public ModelListaPlanDnia() {
    }

    public ModelListaPlanDnia(ModelPlanDnia planDniaWpis, String idModeluPlanuDnia) {
        this.planDniaWpis = planDniaWpis;
        this.idModeluPlanuDnia = idModeluPlanuDnia;
    }

    public ModelPlanDnia getPlanDniaWpis() {
        return planDniaWpis;
    }

    public void setPlanDniaWpis(ModelPlanDnia planDniaWpis) {
        this.planDniaWpis = planDniaWpis;
    }

    public String getIdModeluPlanuDnia() {
        return idModeluPlanuDnia;
    }

    public void setIdModeluPlanuDnia(String idModeluPlanuDnia) {
        this.idModeluPlanuDnia = idModeluPlanuDnia;
    }
}
