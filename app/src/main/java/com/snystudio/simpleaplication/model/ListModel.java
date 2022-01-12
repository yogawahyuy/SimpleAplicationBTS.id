package com.snystudio.simpleaplication.model;

public class ListModel {
    String nameList;
    int id;
    String items;
    Boolean checklistStatus;

    public ListModel() {
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Boolean getChecklistStatus() {
        return checklistStatus;
    }

    public void setChecklistStatus(Boolean checklistStatus) {
        this.checklistStatus = checklistStatus;
    }
}
