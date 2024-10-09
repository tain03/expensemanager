package com.example.expensemanager.model;

public class Transaction {
    private int id;
    private String name;
    private int idCatInOut;
    private double amount; // Sử dụng double cho số tiền
    private String day;
    private String note;
    private int color;

    public Transaction(int id, String name, int idCatInOut, double amount, String day, String note) {
        this.id = id;
        this.name = name;
        this.idCatInOut = idCatInOut;
        this.amount = amount;
        this.day = day;
        this.note = note;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCatInOut() {
        return idCatInOut;
    }

    public void setIdCatInOut(int idCatInOut) {
        this.idCatInOut = idCatInOut;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
