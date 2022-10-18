package com.mobile.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.database.ExpenseDetail;

import java.util.List;

public class ExpenseDetailAdapter extends RecyclerView.Adapter<ExpenseDetailViewHolder>{
    private List<ExpenseDetail> expenseDetailList;
    public ExpenseDetailAdapter(List<ExpenseDetail> expenseDetails){
        this.expenseDetailList = expenseDetails;
    }
    @NonNull
    @Override
    public ExpenseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseDetailViewHolder holder, int position) {
        ExpenseDetail detail = expenseDetailList.get(position);
        holder.tvType.setText(detail.getType());
        holder.tvAmount.setText(String.valueOf(detail.getAmount()));
        holder.tvDate.setText(detail.getDate());
        if(detail.getComment().isEmpty()){
            holder.layoutComment.setVisibility(View.GONE);
        }
        else holder.layoutComment.setVisibility(View.VISIBLE);
        holder.tvComment.setText(detail.getComment());
    }

    @Override
    public int getItemCount() {
        return expenseDetailList == null ? 0 : expenseDetailList.size();
    }
}

class ExpenseDetailViewHolder extends RecyclerView.ViewHolder {
    TextView tvType,tvAmount,tvDate,tvComment;
    LinearLayout layoutComment;
    public ExpenseDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        tvType = itemView.findViewById(R.id.tv_type);
        tvAmount = itemView.findViewById(R.id.tv_amount);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvComment = itemView.findViewById(R.id.tv_comment);
        layoutComment = itemView.findViewById(R.id.layout_comment);
    }
}
