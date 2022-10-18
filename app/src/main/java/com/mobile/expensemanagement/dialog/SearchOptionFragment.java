package com.mobile.expensemanagement.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.fragment.ExpenseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchOptionFragment extends DialogFragment {
    DialogCallback callback;
    AppCompatSpinner spinner;
    CheckBox btnName,btnDate,btnDestination;
    int option = ExpenseFragment.SORT_BY_NAME;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_option, null, false);
        initView(view);
        Dialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Search option")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(btnDate.isChecked()||btnName.isChecked()||btnDestination.isChecked()) {
                            callback.onClick(option, btnName.isChecked(), btnDestination.isChecked(), btnDate.isChecked());
                            dialogInterface.dismiss();
                        }
                        else{
                            Toast.makeText(getContext(),"Please choose one category to search by",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onClick(ExpenseFragment.SORT_BY_NAME,true,false,false);
                    }
                }).setView(view).create();
        return dialog;
    }

    public SearchOptionFragment() {
    }

    public static SearchOptionFragment newInstance(DialogCallback callback) {
        SearchOptionFragment fragment = new SearchOptionFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_option, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        spinner = view.findViewById(R.id.spinner);
        List<String> stringList = new ArrayList<>();
        stringList.add("Sort by: Name");
        stringList.add("Sort by: Destination");
        stringList.add("Sort by: Date");
        Map<String,Integer> spinnerMap = new HashMap<>();
        spinnerMap.put("Sort by: Name",ExpenseFragment.SORT_BY_NAME);
        spinnerMap.put("Sort by: Destination",ExpenseFragment.SORT_BY_DESTINATION);
        spinnerMap.put("Sort by: Date",ExpenseFragment.SORT_BY_DATE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                option = spinnerMap.get(adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner.setSelection(0);

        btnDate = view.findViewById(R.id.btDate);
        btnDestination = view.findViewById(R.id.btDestination);
        btnName = view.findViewById(R.id.btName);
        btnName.setChecked(true);
    }

    public interface DialogCallback{
        void onClick(int sortOption,boolean byName,boolean byDestination,boolean byDate);
    }
}