package com.example.expensemanager.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.dao.TransactionDAO;
import com.example.expensemanager.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions;
    private OnItemClickListener listener;
    private TransactionDAO transactionDAO;

    public TransactionAdapter(List<Transaction> transactions, OnItemClickListener listener) {
        this.transactions = transactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        // Thiết lập giá trị cho các TextView
        holder.txtName.setText(transaction.getName());
        holder.txtAmount.setText(String.valueOf(transaction.getAmount()));
        holder.txtDay.setText(transaction.getDay());
        holder.txtNote.setText(transaction.getNote());

        // Kiểm tra idCatInOut để xác định loại giao dịch
        if (transaction.getIdCatInOut() == 1) {
            // Thu: Màu xanh lá cây
            holder.txtAmount.setTextColor(android.graphics.Color.parseColor("#7bed9f")); // Hoặc Color.rgb(0, 255, 0)
        } else if (transaction.getIdCatInOut() == 2) {
            // Chi: Màu đỏ
            holder.txtAmount.setTextColor(android.graphics.Color.parseColor("#ff6b81")); // Hoặc Color.rgb(255, 0, 0)
        }

        if (position % 2 == 0) {
            // Dòng chẵn: Màu nền xanh nhạt
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#e5ebf3"));
        } else {
            // Dòng lẻ: Màu nền trắng
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
        }
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAmount, txtDay, txtNote;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtNote = itemView.findViewById(R.id.txtNote);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Transaction transaction);
    }
}
