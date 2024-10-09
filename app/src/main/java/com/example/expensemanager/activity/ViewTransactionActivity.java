package com.example.expensemanager.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.R;
import com.example.expensemanager.dao.TransactionDAO;
import com.example.expensemanager.model.Transaction;

public class ViewTransactionActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView amountTextView;
    private TextView dayTextView;
    private TextView noteTextView;

    private TransactionDAO transactionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        // Khởi tạo các thành phần giao diện
        nameTextView = findViewById(R.id.nameTextView);
        amountTextView = findViewById(R.id.amountTextView);
        dayTextView = findViewById(R.id.dayTextView);
        noteTextView = findViewById(R.id.noteTextView);

        // Khởi tạo DAO
        transactionDAO = new TransactionDAO(this);

        // Lấy ID giao dịch từ Intent
        int transactionId = getIntent().getIntExtra("transactionId", -1);
        loadTransaction(transactionId);
    }

    private void loadTransaction(int id) {
        // Lấy giao dịch theo ID
        Transaction transaction = transactionDAO.getTransactionById(id);
        if (transaction != null) {
            // Hiển thị thông tin giao dịch
            nameTextView.setText(transaction.getName());
            amountTextView.setText(String.valueOf(transaction.getAmount()));
            dayTextView.setText(transaction.getDay());
            noteTextView.setText(transaction.getNote());
        }
    }
}
