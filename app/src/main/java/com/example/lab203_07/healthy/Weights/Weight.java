package com.example.lab203_07.healthy.Weights;

public class Weight {
    String date;
    float weight;
    private String status;

    public Weight(){}
    public Weight(String date, float weight) {
        this.date = date;
        this.weight = weight;
        setStatus("UP");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

}
