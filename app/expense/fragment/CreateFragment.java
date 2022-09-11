package com.viettel.vpmt.vofficenew.expense.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.vpmt.vofficenew.R;
import com.viettel.vpmt.vofficenew.expense.activity.ExpenseActivity;
import com.viettel.vpmt.vofficenew.expense.database.Expense;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {
    Expense expense;
    EditText edName,edDescription,edDestination,edDate,edOverseaNation;
    CheckBox cbRisk,cbOversea;
    Button btConfirm;
    ExpenseActivity activity;
    public CreateFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(Expense expense) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putSerializable("OBJECT", expense);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expense = (Expense) getArguments().getSerializable("OBJECT");
        }
    }
    private void initView(View view){
        edName = view.findViewById(R.id.ed_name);
        edDate = view.findViewById(R.id.ed_date);
        edDescription = view.findViewById(R.id.ed_description);
        edOverseaNation = view.findViewById(R.id.ed_oversea);
        edDestination = view.findViewById(R.id.ed_destination);
        cbOversea = view.findViewById(R.id.cb_oversea);
        cbRisk = view.findViewById(R.id.cb_risk);

        edName.setText(expense.getName());
        edDate.setText(expense.getName());
        edDescription.setText(expense.getDescription());
        edDestination.setText(expense.getDestination());
        cbRisk.setChecked(expense.getRisk());
        btConfirm = view.findViewById(R.id.btConfirm);
        if(expense.getOversea()){
            edOverseaNation.setVisibility(View.GONE);
            edOverseaNation.setText(expense.getOverseaNation());
        }
        else {
            edOverseaNation.setVisibility(View.VISIBLE);
            edOverseaNation.setText(expense.getOverseaNation());
        }

        cbOversea.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                edOverseaNation.setVisibility(View.GONE);
                edOverseaNation.setText("");
            }
            else{
                edOverseaNation.setVisibility(View.VISIBLE);
                edOverseaNation.setText(expense.getOverseaNation());
            }

        });
        btConfirm.setOnClickListener(v -> {
            if(!validForm()) Toast.makeText(getContext(),"There is one or more required fields unfilled",Toast.LENGTH_LONG).show();
            else{
                ((ExpenseActivity) ((Activity) getActivity())).replaceExpenseFragment(new Expense("","",false,"",false,"","",new ArrayList()),-1);
            }
        });
    }

    private boolean validForm(){
        if(edName.getText().toString().isEmpty()) return false;
        if(edDestination.getText().toString().isEmpty()) return false;
        if(edDate.getText().toString().isEmpty()) return false;
        if(edDescription.getText().toString().isEmpty()) return false;
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create, container, false);
        initView(view);
        return view;
    }
}