package com.viettel.vpmt.vofficenew.expense.activity;


import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;



import com.viettel.vpmt.vofficenew.R;
import com.viettel.vpmt.vofficenew.expense.database.Expense;
import com.viettel.vpmt.vofficenew.expense.fragment.CreateFragment;
import com.viettel.vpmt.vofficenew.expense.fragment.EditFragment;
import com.viettel.vpmt.vofficenew.expense.fragment.ExpenseFragment;

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