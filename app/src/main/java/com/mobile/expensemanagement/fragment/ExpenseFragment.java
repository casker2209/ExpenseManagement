package com.mobile.expensemanagement.fragment;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.mobile.expensemanagement.AdapterCallback;
import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.activity.ExpenseActivity;
import com.mobile.expensemanagement.adapter.ExpenseAdapter;
import com.mobile.expensemanagement.database.Expense;
import com.mobile.expensemanagement.dialog.SearchOptionFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {
    List<Expense> expenseList;
    RecyclerView recyclerView;
    CardView btAdd;
    EditText edSearch;
    ExpenseAdapter expenseAdapter;
    TextView tvExpenseTotal;
    ImageView btSearchOption;
    ExpenseActivity activity;
    public final static int SORT_BY_NAME = 0;
    public final static int SORT_BY_DESTINATION = 1;
    public final static int SORT_BY_DATE = 2;
    public boolean byDate = false;
    public boolean byDescription = false;
    public boolean byName = true;
    public int sortOption = SORT_BY_NAME;
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
        /*if (getArguments() != null) {
                expenseList = (List<Expense>) getArguments().getSerializable("LIST_EXPENSE");
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if(activity == null) activity = ((ExpenseActivity) getActivity());
        if(expenseList == null) expenseList = activity.getDatabase().expenseDAO().getExpenseByName("");
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
        tvExpenseTotal = view.findViewById(R.id.tv_expense_total);
        tvExpenseTotal.setText(String.valueOf(expenseAdapter.getTotalSum()));
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
                expenseList = activity.getDatabase().expenseDAO().getExpenseByName(editable.toString());
                expenseAdapter.notifyDataSetChanged();
                tvExpenseTotal.setText(expenseAdapter.getTotalSum());
            }
        });
        btSearchOption = view.findViewById(R.id.btSearchOption);
        btSearchOption.setOnClickListener(v -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment prev = fm.findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            SearchOptionFragment dialogFragment = SearchOptionFragment.newInstance(
                    (option, b1, b2, b3) -> {
                        sortOption = option;
                        byName = b1;
                        byDescription = b2;
                        byDate = b3;
                    }
            );
            dialogFragment.show(fm,"dialog");
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