package com.mobile.expensemanagement.fragment;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.activity.ExpenseActivity;
import com.mobile.expensemanagement.database.Expense;
import com.mobile.expensemanagement.database.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {
    Expense expense;
    TextInputEditText edName,edDescription,edDestination,edOverseaNation;
    TextView edDate;
    CheckBox cbRisk,cbOversea;
    Button btConfirm;
    int index;
    boolean has
    public CreateFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(Expense expense,int index) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putSerializable("OBJECT", expense);
        args.putSerializable("INDEX",index);
        fragment.setArguments(args);
        return fragment;
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
            index = getArguments().getInt("INDEX",-1);
        }
    }
    private void initView(View view){
        List<ExpenseDetail> detailList  = expense.getDetails();
        edName = view.findViewById(R.id.ed_name);
        edDate = view.findViewById(R.id.ed_date);
        edDescription = view.findViewById(R.id.ed_description);
        edOverseaNation = view.findViewById(R.id.ed_oversea);
        edDestination = view.findViewById(R.id.ed_destination);
        cbOversea = view.findViewById(R.id.cb_oversea);
        cbRisk = view.findViewById(R.id.cb_risk);

        edName.setText(expense.getName());
        edDescription.setText(expense.getDescription());
        edDestination.setText(expense.getDestination());
        cbRisk.setChecked(expense.getRisk());
        btConfirm = view.findViewById(R.id.btConfirm);
        if(!expense.getOversea()){
            edOverseaNation.setVisibility(View.GONE);
        }
        else {
            edOverseaNation.setVisibility(View.VISIBLE);
        }
        edOverseaNation.setText(expense.getOverseaNation());
        cbOversea.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                edOverseaNation.setVisibility(View.VISIBLE);
            }
            else{
                edOverseaNation.setVisibility(View.GONE);
            }

        });
        btConfirm.setOnClickListener(v -> {
            if(!validForm()) Toast.makeText(getContext(),"There is one or more required fields unfilled",Toast.LENGTH_LONG).show();
            else{

                ((ExpenseActivity) (getActivity())).replaceExpenseFragment(new Expense(edName.getText().toString()
                        ,edDestination.getText().toString(),
                        cbRisk.isChecked(),
                        edDescription.getText().toString(),
                        cbOversea.isChecked(),
                        cbOversea.isChecked() ? edOverseaNation.getText().toString() : "" ,
                        edDate.getText().toString(),
                        detailList),index);
            }
        });
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String strMonth = (month+1) < 10 ? "0"+String.valueOf(month+1) : String.valueOf(month+1);
        String strDay = day < 10 ? "0"+String.valueOf(day) : String.valueOf(day);
        if(expense.getDate().isEmpty()) edDate.setText(strDay+"/"+strMonth+"/"+year);
        else edDate.setText(expense.getDate());
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String strMonth = month < 10 ? "0"+String.valueOf(month) : String.valueOf(month);
                        String strDay = day < 10 ? "0"+String.valueOf(day) : String.valueOf(day);
                        edDate.setText(strDay+"/"+strMonth+"/"+year);
                    }
                },year,month,day);
                dialog.show();
            }
        });
    }

    private boolean validForm(){
        if(edName.getText().toString().isEmpty()) return false;
        if(edDestination.getText().toString().isEmpty()) return false;
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