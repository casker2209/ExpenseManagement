package com.mobile.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.expensemanagement.R;
import com.mobile.expensemanagement.AdapterCallback;
import com.mobile.expensemanagement.activity.ExpenseActivity;
import com.mobile.expensemanagement.database.Expense;
import com.mobile.expensemanagement.database.ExpenseDetail;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> implements Filterable {
    private List<Expense> expenseList;
    private List<Expense> filteredExpenseList;
    private List<Expense> visibleList;
    private List<Expense> visibleListDetail;
    private ExpenseActivity activity;
    private AdapterCallback addDetailOnClick;
    private AdapterCallback editOnClick;
    public ExpenseAdapter(List<Expense> expenseList,ExpenseActivity activity,AdapterCallback addDetailOnClick,AdapterCallback editOnClick){
        this.expenseList = expenseList;
        this.filteredExpenseList = expenseList;
        this.activity = activity;
        this.addDetailOnClick = addDetailOnClick;
        this.editOnClick = editOnClick;
        this.visibleList = new ArrayList<>();
        this.visibleListDetail = new ArrayList<>();
    }
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense,parent,false));
    }

    @Override
    public int getItemCount() {
        return filteredExpenseList == null ? 0 :filteredExpenseList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = filteredExpenseList.get(position);
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
        int sum = 0;
        for(ExpenseDetail detail : expense.getDetails()){
            sum+=detail.getAmount();
        }
        holder.tvExpenseTotal.setText(String.valueOf(sum));
        holder.detailRecyclerView.setAdapter(new ExpenseDetailAdapter(expense.getDetails()));
        holder.detailRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.VERTICAL,false));
        holder.btAddDetail.setOnClickListener(view -> {
            addDetailOnClick.Callback(position);
        });
        holder.btEdit.setOnClickListener(view -> {
            editOnClick.Callback(position);
        });
        holder.btDelete.setOnClickListener(view -> {
            expenseList.remove(expense);
            filteredExpenseList.remove(expense);
            activity.getExpenseList().remove(position);
            notifyDataSetChanged();
        });

        if(!(visibleList.contains(expense) && filteredExpenseList.contains(expense))){
            holder.layoutInformation.setVisibility(View.GONE);
        }
        else holder.layoutInformation.setVisibility(View.VISIBLE);

        if(!(visibleListDetail.contains(expense) && filteredExpenseList.contains(expense))){
            holder.detailRecyclerView.setVisibility(View.GONE);
        }
        else{
            holder.detailRecyclerView.setVisibility(View.VISIBLE);
        }

        holder.rlExpense.setOnClickListener(view -> {
            if(holder.layoutInformation.getVisibility() == View.GONE) {
                holder.layoutInformation.setVisibility(View.VISIBLE);
                visibleList.add(expense);
            }
            else {
                holder.layoutInformation.setVisibility(View.GONE);
                visibleList.remove(position);
            }
        });

        holder.layoutDetail.setOnClickListener(view -> {
            if(holder.layoutInformation.getVisibility() == View.GONE) {
                holder.detailRecyclerView.setVisibility(View.VISIBLE);
                visibleListDetail.add(expense);
            }
            else {
                holder.detailRecyclerView.setVisibility(View.GONE);
                visibleListDetail.remove(expense);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredExpenseList = expenseList;
                } else {
                    List<Expense> filteredList = new ArrayList<>();
                    for (Expense expense : expenseList) {
                        if (expense.getName().toLowerCase().contains(charString.toLowerCase())
                                || expense.getDescription().contains(charSequence)
                                || expense.getDestination().contains(charSequence)) {
                            filteredList.add(expense);
                        }
                    }
                    filteredExpenseList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredExpenseList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredExpenseList = (ArrayList<Expense>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public int getTotalSum(){
        int sum = 0;
        if(filteredExpenseList.size() != 0){
            for(Expense expense : filteredExpenseList){
                if(expense.getDetails().size()!=0) {
                    for (ExpenseDetail detail : expense.getDetails()) {
                        sum += detail.getAmount();
                    }
                }
            }
        }
        return sum;
    }
}
class ExpenseViewHolder extends RecyclerView.ViewHolder{
    TextView tvDescription,tvDestination,tvIsRisk,tvOverseaNation,tvExpense,tvDate,tvExpenseTotal;
    LinearLayout layoutInformation,layoutOverseas,layoutDetail; RelativeLayout rlExpense;
    ImageView btEdit,btAddDetail,btDelete;
    RecyclerView detailRecyclerView;
    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDescription = itemView.findViewById(R.id.tv_description);
        tvDestination = itemView.findViewById(R.id.tv_destination);
        tvIsRisk = itemView.findViewById(R.id.tv_risk);
        tvOverseaNation = itemView.findViewById(R.id.tv_oversea_nation);
        tvExpense = itemView.findViewById(R.id.tv_expense);
        tvExpenseTotal = itemView.findViewById(R.id.tv_expense_total);
        tvDate = itemView.findViewById(R.id.tv_date);
        layoutInformation = itemView.findViewById(R.id.layout_information);
        layoutOverseas = itemView.findViewById(R.id.layout_oversea);
        layoutDetail = itemView.findViewById(R.id.layout_detail);
        rlExpense = itemView.findViewById(R.id.rl_expense);
        btEdit = itemView.findViewById(R.id.btEdit);
        btAddDetail = itemView.findViewById(R.id.btnDetailAdd);
        btDelete = itemView.findViewById(R.id.btDelete);
        detailRecyclerView = itemView.findViewById(R.id.detail_recycler_view);
    }

}
