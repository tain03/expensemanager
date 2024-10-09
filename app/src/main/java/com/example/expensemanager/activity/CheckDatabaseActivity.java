package com.example.expensemanager.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.expensemanager.R;
import com.example.expensemanager.utils.DBHelper;

public class CheckDatabaseActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_database); // Tạo layout cho Activity này

        textView = findViewById(R.id.textView);

        DBHelper dbHelper = new DBHelper(this);
        checkTables(dbHelper);
    }

    private void checkTables(DBHelper dbHelper) {
        String[] tables = {"tblInOut", "tblCatInOut", "tblCategory", "tblTransaction"};
        StringBuilder result = new StringBuilder();

        for (String table : tables) {
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{table});
            if (cursor.getCount() > 0) {
                result.append(table).append(" exists\n");
            } else {
                result.append(table).append(" does not exist\n");
            }
            cursor.close();
        }

        textView.setText(result.toString());
    }
}
