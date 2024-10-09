package com.example.expensemanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanager.model.Transaction;
import com.example.expensemanager.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public TransactionDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = database.query("tblTransaction", new String[]{"id", "name", "idCatInOut", "amount", "day", "note"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(cursor.getInt(0));
                transaction.setName(cursor.getString(1));
                transaction.setIdCatInOut(cursor.getInt(2));
                transaction.setAmount(cursor.getDouble(3)); // Đổi thành double nếu amount là REAL
                transaction.setDay(cursor.getString(4));
                transaction.setNote(cursor.getString(5));

//                // Thiết lập màu sắc dựa vào idCatInOut
//                if (transaction.getIdCatInOut() == 1) {
//                    transaction.setColor(android.graphics.Color.parseColor("#7bed9f")); // Thu: Màu xanh
//                } else if (transaction.getIdCatInOut() == 2) {
//                    transaction.setColor(android.graphics.Color.parseColor("#ff6b81")); // Chi: Màu đỏ
//                }

                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public Transaction getTransactionById(int id) {
        Cursor cursor = database.query("tblTransaction", new String[]{"id", "name", "idCatInOut", "amount", "day", "note"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            Transaction transaction = new Transaction();
            transaction.setId(cursor.getInt(0));
            transaction.setName(cursor.getString(1));
            transaction.setIdCatInOut(cursor.getInt(2));
            transaction.setAmount(cursor.getDouble(3)); // Giữ nguyên là double nếu amount là REAL
            transaction.setDay(cursor.getString(4));
            transaction.setNote(cursor.getString(5));
            cursor.close();
            return transaction;
        }
        cursor.close();
        return null;
    }

    public boolean addTransaction(Transaction transaction) {
        try {
            database.execSQL("INSERT INTO tblTransaction (name, idCatInOut, amount, day, note) VALUES (?, ?, ?, ?, ?)",
                    new Object[]{transaction.getName(), transaction.getIdCatInOut(), transaction.getAmount(), transaction.getDay(), transaction.getNote()});
            return true; // Thêm thành công
        } catch (Exception e) {
            e.printStackTrace(); // In ra logcat để kiểm tra
            return false; // Có lỗi xảy ra
        }
    }


    public void updateTransaction(Transaction transaction) {
        database.execSQL("UPDATE tblTransaction SET name = ?, idCatInOut = ?, amount = ?, day = ?, note = ? WHERE id = ?",
                new Object[]{transaction.getName(), transaction.getIdCatInOut(), transaction.getAmount(), transaction.getDay(), transaction.getNote(), transaction.getId()});
    }

    public void deleteTransaction(int id) {
        database.execSQL("DELETE FROM tblTransaction WHERE id = ?", new Object[]{id});
    }

    public void close() {
        dbHelper.close();
    }

    public double getTotalIncome() {
        double totalIncome = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(amount) FROM tblTransaction WHERE idCatInOut = 1", null);
        if (cursor.moveToFirst()) {
            totalIncome = cursor.getDouble(0);
        }
        cursor.close();
        return totalIncome;
    }

    public double getTotalExpense() {
        double totalExpense = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(amount) FROM tblTransaction WHERE idCatInOut = 2", null);
        if (cursor.moveToFirst()) {
            totalExpense = cursor.getDouble(0);
        }
        cursor.close();
        return totalExpense;
    }
    public List<Transaction> getAllTransactionsByMonth(String month) {
        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tblTransaction WHERE strftime('%m', day) = ?", new String[]{month});
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(cursor.getInt(0));
                transaction.setName(cursor.getString(1));
                transaction.setIdCatInOut(cursor.getInt(2));
                transaction.setAmount(cursor.getDouble(3)); // Chỉnh sửa để lấy giá trị thực
                transaction.setDay(cursor.getString(4));
                transaction.setNote(cursor.getString(5));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

}
