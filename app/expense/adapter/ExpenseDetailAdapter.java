package com.viettel.vpmt.vofficenew.expense.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.vpmt.vofficenew.R;
import com.viettel.vpmt.vofficenew.expense.database.ExpenseDetail;

import java.util.List;

public class ExpenseDetailAdapter extends RecyclerView.Adapter<ExpenseDetailViewHolder>{
    private List<ExpenseDetail> expenseDetailList;
    @NonNull
    @Override
    public ExpenseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseDetailViewHolder holder, int position) {
        ExpenseDetail detail = expenseDetailList.get(position);
        holder.tvType.setText(detail.getType());
        holder.tvAmount.setText(detail.getAmount());
        holder.tvDate.setText(detail.getDate());
    }

    @Override
    public int getItemCount() {
        return expenseDetailList.size();
    }
}

class ExpenseDetailViewHolder extends RecyclerView.ViewHolder {
    TextView tvType,tvAmount,tvDate;
    public ExpenseDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        tvType = itemView.findViewById(R.id.tv_type);
        tvAmount = itemView.findViewById(R.id.tv_amount);
        tvDate = itemView.findViewById(R.id.tv_date);
    }
}
