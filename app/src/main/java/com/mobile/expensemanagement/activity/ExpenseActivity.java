package com.mobile.expensemanagement.activity;


import android.os.Bundle;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.database.Expense;
import com.mobile.expensemanagement.database.ExpenseDB;
import com.mobile.expensemanagement.fragment.CreateFragment;
import com.mobile.expensemanagement.fragment.EditFragment;
import com.mobile.expensemanagement.fragment.ExpenseFragment;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends FragmentActivity {
    ExpenseDB db;

    List<Expense> expenseList;

    public ExpenseDB getDatabase() {
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        expenseList =new ArrayList<>();
        replaceExpenseFragment();
        db = Room.databaseBuilder(getApplicationContext(),
                ExpenseDB.class, "database-name").build();
    }

    public void replaceEditFragment(Expense expense,int index){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame,EditFragment.newInstance(expense,index),"").addToBackStack(null).commit();
    }

    public void replaceCreateFragment(Expense expense,int index){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame,CreateFragment.newInstance(expense,index),"").addToBackStack(null).commit();
    }

    public void replaceCreateFragment(Expense expense){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame,CreateFragment.newInstance(expense),"").addToBackStack(null).commit();
    }

    public void replaceExpenseFragment(){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame,ExpenseFragment.newInstance(expenseList),"").commit();
    }

    public void replaceExpenseFragment(Expense expense,int index){
        FragmentManager fm = getSupportFragmentManager();
        if(index != -1){
            expenseList.set(index,expense);
        }
        else expenseList.add(expense);
        fm.beginTransaction().replace(R.id.frame,ExpenseFragment.newInstance(expenseList),"").commit();
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }
}