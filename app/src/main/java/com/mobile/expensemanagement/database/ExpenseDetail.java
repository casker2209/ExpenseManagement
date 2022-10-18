package com.mobile.expensemanagement.database;

public class ExpenseDetail {
    private String type;
    private int amount;
    private String date;
    private String comment;
    public ExpenseDetail(String type,int amount,String date,String comment){
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
