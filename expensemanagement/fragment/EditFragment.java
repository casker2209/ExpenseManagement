package com.viettel.vpmt.vofficenew.mobile.expensemanagement.fragment;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.viettel.vpmt.vofficenew.R;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.activity.ExpenseActivity;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.database.Expense;
import com.viettel.vpmt.vofficenew.mobile.expensemanagement.database.ExpenseDetail;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    Expense expense;
    int index;
    EditText edType,edAmount,edDate;
    Button btConfirm;
    public static Fragment newInstance(Expense expense,int index) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable("EXPENSE",expense);
        args.putInt("INDEX",index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expense = (Expense) getArguments().getSerializable("EXPENSE");
            index = getArguments().getInt("INDEX");
        }
    }

    private void initView(View view){
        edType = view.findViewById(R.id.ed_type);
        edAmount = view.findViewById(R.id.ed_amount);
        edDate = view.findViewById(R.id.ed_date);
        btConfirm = view.findViewById(R.id.btConfirm);
        edAmount.setText("0");
        edDate.setText(expense.getDate());
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validForm()) Toast.makeText(getContext(),"There is one or more required fields unfilled",Toast.LENGTH_LONG).show();
                else {
                    expense.getDetails().add(new ExpenseDetail(edType.getText().toString(),Integer.parseInt(edAmount.getText().toString()),edDate.getText().toString()));
                    ((ExpenseActivity) (getActivity())).replaceExpenseFragment(expense,index);
                }
            }
        });
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
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

    private boolean validForm() {
        return !(edType.getText().toString().isEmpty() || edAmount.getText().toString().isEmpty() || edDate.getText().toString().isEmpty());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        initView(view);
        return view;
    }
}