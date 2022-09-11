package com.mobile.expensemanagement.activity;


import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;



import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.database.Expense;
import com.mobile.expensemanagement.fragment.CreateFragment;
import com.mobile.expensemanagement.fragment.EditFragment;
import com.mobile.expensemanagement.fragment.ExpenseFragment;

public class ExpenseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        replaceExpenseFragment();
    }

    public void replaceEditFragment(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(EditFragment.newInstance(),"").commit();
    }

    public void replaceCreateFragment(Expense expense){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(CreateFragment.newInstance(expense),"").commit();
    }

    public void replaceExpenseFragment(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(ExpenseFragment.newInstance(),"").commit();
    }

    public void replaceExpenseFragment(Expense expense,int index){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(ExpenseFragment.newInstance(expense,index),"").commit();
    }
}