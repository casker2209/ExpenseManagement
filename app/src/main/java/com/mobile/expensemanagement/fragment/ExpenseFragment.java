package com.mobile.expensemanagement.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.activity.ExpenseActivity;
import com.mobile.expensemanagement.adapter.ExpenseAdapter;
import com.mobile.expensemanagement.database.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {
    List<Expense> expenseList;
    RecyclerView recyclerView;
    CardView btAdd;
    ExpenseAdapter expenseAdapter;
    public ExpenseFragment() {
    }
    public static ExpenseFragment newInstance() {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public static ExpenseFragment newInstance(Expense expense,int index){
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putSerializable("EXPENSE",expense);
        args.putSerializable("INDEX",index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().getSerializable("EXPENSE") != null){
                Expense expense = (Expense) getArguments().getSerializable("EXPENSE");
                if(getArguments().getInt("INDEX") != -1){
                    expenseList.set(getArguments().getInt("INDEX"),expense);
                }
                else expenseList.add(expense);
                expenseAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        btAdd = view.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(v -> {
            ((ExpenseActivity) ((Activity) getActivity())).replaceCreateFragment(new Expense("","",false,"",false,"","",new ArrayList()));
        });
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenseList,(ExpenseActivity) ((Activity) getActivity()));
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        initView(view);
        return view;
    }
}