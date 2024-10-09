package com.example.expensemanager.model;

public class CatInOut {
    private int id;
    private int idCat;
    private int idInOut;

    public CatInOut(int id, int idCat, int idInOut) {
        this.id = id;
        this.idCat = idCat;
        this.idInOut = idInOut;
    }

    public CatInOut() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdInOut() {
        return idInOut;
    }

    public void setIdInOut(int idInOut) {
        this.idInOut = idInOut;
    }
}
