package com.mobile.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.expensemanagement.activity.ExpenseActivity;
import com.mobile.expensemanagement.expense.database.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder>{
    private List<Expense> expenseList;
    private ExpenseActivity activity;
    private View.OnClickListener addDetailOnClick;
    public ExpenseAdapter(List<Expense> expenseList,ExpenseActivity activity){
        this.expenseList = expenseList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense,parent,false));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.tvExpense.setText(expense.getName());
        holder.tvDestination.setText(expense.getDestination());
        holder.tvDescription.setText(expense.getDescription());
        holder.tvDate.setText(expense.getDate());
        if(!expense.getOversea()) {
            holder.layoutOverseas.setVisibility(View.GONE);
        }
        else{
            holder.layoutOverseas.setVisibility(View.VISIBLE);
            holder.tvOverseaNation.setText(expense.getOverseaNation());
        }
        if(expense.getRisk()){
            holder.tvIsRisk.setText("Risky");
        }
        else holder.tvIsRisk.setText("Not risky");
        holder.detailRecyclerView.setAdapter(new ExpenseDetailAdapter());
        holder.detailRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.VERTICAL,false));
        holder.btAddDetail.setOnClickListener(view -> {
            activity.replaceEditFragment();
        });
        holder.btDelete.setOnClickListener(view -> {
            expenseList.remove(expense);
            notifyDataSetChanged();
        });
        holder.rlExpense.setOnClickListener(view -> {
            if(holder.layoutInformation.getVisibility() == View.GONE) holder.layoutInformation.setVisibility(View.VISIBLE);
            else holder.layoutInformation.setVisibility(View.GONE);
        });
    }
}
class ExpenseViewHolder extends RecyclerView.ViewHolder{
    TextView tvDescription,tvDestination,tvIsRisk,tvOverseaNation,tvExpense,tvDate;
    LinearLayout layoutInformation,layoutOverseas; RelativeLayout rlExpense;
    ImageView btEdit,btAddDetail,btDelete;
    RecyclerView detailRecyclerView;
    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDescription = itemView.findViewById(R.id.tv_description);
        tvDestination = itemView.findViewById(R.id.tv_destination);
        tvIsRisk = itemView.findViewById(R.id.tv_risk);
        tvOverseaNation = itemView.findViewById(R.id.tv_oversea_nation);
        tvExpense = itemView.findViewById(R.id.tv_expense);
        tvDate = itemView.findViewById(R.id.tv_date);
        layoutInformation = itemView.findViewById(R.id.layout_information);
        layoutInformation = itemView.findViewById(R.id.layout_oversea);
        rlExpense = itemView.findViewById(R.id.rl_expense);
        btEdit = itemView.findViewById(R.id.btEdit);
        btAddDetail = itemView.findViewById(R.id.btnDetailAdd);
        btDelete = itemView.findViewById(R.id.btDelete);
        detailRecyclerView = itemView.findViewById(R.id.detail_recycler_view);
    }
}
