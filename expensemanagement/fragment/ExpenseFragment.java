package com.viettel.vpmt.vofficenew.mobile.expensemanagement.fragment;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.viettel.vpmt.vofficenew.R;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.AdapterCallback;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.activity.ExpenseActivity;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.adapter.ExpenseAdapter;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.database.Expense;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {
    List<Expense> expenseList;
    RecyclerView recyclerView;
    CardView btAdd;
    EditText edSearch;
    ExpenseAdapter expenseAdapter;
    public ExpenseFragment() {
    }
    public static ExpenseFragment newInstance(List<Expense> expenseList) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putSerializable("LIST_EXPENSE", (Serializable) expenseList);
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
                expenseList = (List<Expense>) getArguments().getSerializable("LIST_EXPENSE");
        }
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerview);
        btAdd = view.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(v -> {
            ((ExpenseActivity) getActivity()).replaceCreateFragment(new Expense("","",false,"",false,"","",new ArrayList()));
        });
        expenseAdapter = new ExpenseAdapter(expenseList, (ExpenseActivity) getActivity(), new AdapterCallback() {
            @Override
            public void Callback(int position) {
                ((ExpenseActivity) getActivity()).replaceEditFragment(expenseList.get(position),position);
            }
        }, new AdapterCallback() {
            @Override
            public void Callback(int position) {
                ((ExpenseActivity) getActivity()).replaceCreateFragment(expenseList.get(position),position);
            }
        });
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        edSearch = view.findViewById(R.id.search);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                expenseAdapter.getFilter().filter(editable);
                expenseAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        initView(view);
        return view;
    }
}