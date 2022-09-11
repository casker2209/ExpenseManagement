package com.mobile.expensemanagement.database;

public class ExpenseDetail {
    private String type;
    private int amount;
    private String date;
    public ExpenseDetail(String type,int amount,String date){
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }
}
