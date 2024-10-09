package com.example.expensemanager.model;

public class Category {
    private int id;
    private String name;
    private Integer parentId; // Đặt là Integer để có thể null
    private String icon;
    private String note;

    public Category(int id, String name, Integer parentId, String icon, String note) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.icon = icon;
        this.note = note;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
