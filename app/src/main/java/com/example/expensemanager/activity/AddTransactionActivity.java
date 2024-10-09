package com.example.expensemanager.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.R;
import com.example.expensemanager.dao.TransactionDAO;
import com.example.expensemanager.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText etName, etAmount, etDay, etNote;
    private Button btnAddTransaction, btnCancel; // Thêm nút hủy
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private RadioGroup radioGroupType;
    private RadioButton radioIncome, radioExpense;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        // Khởi tạo các view
        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);

        // Khởi tạo Calendar và định dạng ngày
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());  // Định dạng ngày là dd/MM/yyyy
        etDay = findViewById(R.id.etDay);

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTransactionActivity.this, dateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etNote = findViewById(R.id.etNote);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        btnCancel = findViewById(R.id.btnCancel); // Khởi tạo nút hủy

        // Khởi tạo RadioGroup và RadioButtons
        radioGroupType = findViewById(R.id.radioGroupType);
        radioIncome = findViewById(R.id.radioIncome);
        radioExpense = findViewById(R.id.radioExpense);

        // Thiết lập sự kiện khi nhấn nút thêm giao dịch
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
            }
        });

        // Thiết lập sự kiện khi nhấn nút hủy
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng activity và quay về Home
            }
        });
    }

    // Xử lý sự kiện chọn ngày từ DatePicker
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            etDay.setText(dateFormat.format(calendar.getTime()));  // Định dạng ngày hiển thị là dd/MM/yyyy
        }
    };

    private void addTransaction() {
        // Lấy thông tin từ các EditText
        String name = etName.getText().toString().trim();

        // Lấy idCatInOut từ RadioGroup
        int idCatInOut;
        if (radioIncome.isChecked()) {
            idCatInOut = 1; // Thu
        } else {
            idCatInOut = 2; // Chi
        }

        double amount;
        try {
            amount = Double.parseDouble(etAmount.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        String day = etDay.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        // Tạo một đối tượng Transaction mới
        Transaction transaction = new Transaction();
        transaction.setName(name);
        transaction.setIdCatInOut(idCatInOut);
        transaction.setAmount(amount);
        transaction.setDay(day);
        transaction.setNote(note);

        // Sử dụng TransactionDAO để thêm giao dịch
        TransactionDAO transactionDAO = new TransactionDAO(this);
        boolean isSuccess = transactionDAO.addTransaction(transaction);
        if (isSuccess) {
            Toast.makeText(this, "Thêm giao dịch thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity sau khi thêm thành công
        } else {
            Toast.makeText(this, "Thêm giao dịch thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
