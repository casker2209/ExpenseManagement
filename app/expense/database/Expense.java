package com.mobile.expensemanagementw.expense.database;

import java.io.Serializable;
import java.util.List;

public class Expense implements Serializable {
    private String name;
    private String destination;
    private Boolean isRisk;
    private String description;
    private Boolean isOversea;
    private String overseaNation;
    private String date;
    private List<ExpenseDetail> details;

    public Expense(){}

    public Expense(String name,String destination,Boolean isRisk,String description,boolean isOversea,String overseaNation,String date,List<ExpenseDetail> details){
        this.name = name;
        this.description = description;
        this.destination = destination;
        this.isRisk = isRisk;
        this.isOversea = isOversea;
        this.overseaNation = overseaNation;
        this.details = details;
        this.date = date;
    }

    public Boolean getOversea() {
        return isOversea;
    }

    public Boolean getRisk() {
        return isRisk;
    }

    public String getDescription() {
        return description;
    }

    public String getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public String getOverseaNation() {
        return overseaNation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOversea(Boolean oversea) {
        isOversea = oversea;
    }

    public void setOverseaNation(String overseaNation) {
        this.overseaNation = overseaNation;
    }

    public void setRisk(Boolean risk) {
        isRisk = risk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
