package com.example.expensemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.adapter.TransactionAdapter;
import com.example.expensemanager.dao.TransactionDAO;
import com.example.expensemanager.model.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAddTransaction;
    private TextView txtTotalIncome, txtTotalExpense;
    private TransactionAdapter transactionAdapter;
    private TransactionDAO transactionDAO;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần UI
        recyclerView = findViewById(R.id.recyclerView);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        txtTotalIncome = findViewById(R.id.txtTotalIncome); // TextView hiển thị tổng thu
        txtTotalExpense = findViewById(R.id.txtTotalExpense); // TextView hiển thị tổng chi

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionDAO = new TransactionDAO(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Định dạng ngày

        loadTransactions();
        updateStatistics(); // Cập nhật thống kê số tiền

        // Xử lý sự kiện khi nhấn nút thêm giao dịch
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật danh sách giao dịch khi quay lại MainActivity
        loadTransactions();
        updateStatistics(); // Cập nhật thống kê số tiền
    }

    private void loadTransactions() {
        // Lấy danh sách giao dịch từ cơ sở dữ liệu
        List<Transaction> transactionList = transactionDAO.getAllTransactions();

        // Sắp xếp danh sách giao dịch theo ngày từ mới nhất đến cũ nhất
        Collections.sort(transactionList, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                try {
                    return dateFormat.parse(t2.getDay()).compareTo(dateFormat.parse(t1.getDay()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        // Thiết lập adapter cho RecyclerView
        transactionAdapter = new TransactionAdapter(transactionList, new TransactionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transaction transaction) {
                Intent intent = new Intent(MainActivity.this, ViewTransactionActivity.class);
                intent.putExtra("transactionId", transaction.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(transactionAdapter);
    }

    private void updateStatistics() {
        // Cập nhật tổng thu và chi
        double totalIncome = transactionDAO.getTotalIncome();
        double totalExpense = transactionDAO.getTotalExpense();

        // Hiển thị tổng thu và chi lên các TextView
        txtTotalIncome.setText(String.format("Tổng thu: %.2f", totalIncome));
        txtTotalExpense.setText(String.format("Tổng chi: %.2f", totalExpense));
    }
}
