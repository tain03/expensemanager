package com.example.expensemanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expensemanager_db";
    private static final int DATABASE_VERSION = 5; // Tăng phiên bản

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng tblInOut
        db.execSQL("CREATE TABLE tblInOut (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Thêm AUTOINCREMENT cho id
                "name TEXT, " +
                "type TEXT" +
                ")");

        // Tạo bảng tblCategory
        db.execSQL("CREATE TABLE tblCategory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Thêm AUTOINCREMENT cho id
                "name TEXT, " +
                "parentid INTEGER, " +
                "icon TEXT, " +
                "note TEXT, " +
                "FOREIGN KEY (parentid) REFERENCES tblCategory(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");

        // Tạo bảng tblCatInOut
        db.execSQL("CREATE TABLE tblCatInOut (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Thêm AUTOINCREMENT cho id
                "idCat INTEGER, " +
                "idInOut INTEGER, " +
                "FOREIGN KEY (idCat) REFERENCES tblCategory(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (idInOut) REFERENCES tblInOut(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");

        // Tạo bảng tblTransaction
        db.execSQL("CREATE TABLE tblTransaction (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Thêm AUTOINCREMENT cho id
                "name TEXT, " +
                "idCatInOut INTEGER, " +
                "amount REAL, " + // Đổi INTEGER thành REAL để lưu số tiền
                "day TEXT, " +
                "note TEXT, " +
                "FOREIGN KEY (idCatInOut) REFERENCES tblCatInOut(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");

        // Dữ liệu mẫu cho bảng tblInOut
        db.execSQL("INSERT INTO tblInOut (name, type) VALUES ('Thu', 'income')");
        db.execSQL("INSERT INTO tblInOut (name, type) VALUES ('Chi', 'expense')");

// Dữ liệu mẫu cho bảng tblCategory
        db.execSQL("INSERT INTO tblCategory (name, parentid, icon, note) VALUES ('Thực phẩm', NULL, 'icon_food.png', 'Các loại thực phẩm')");
        db.execSQL("INSERT INTO tblCategory (name, parentid, icon, note) VALUES ('Giải trí', NULL, 'icon_entertainment.png', 'Các hoạt động giải trí')");
        db.execSQL("INSERT INTO tblCategory (name, parentid, icon, note) VALUES ('Đi lại', NULL, 'icon_transport.png', 'Chi phí đi lại')");
        db.execSQL("INSERT INTO tblCategory (name, parentid, icon, note) VALUES ('Sức khỏe', NULL, 'icon_health.png', 'Chi phí sức khỏe')");

// Dữ liệu mẫu cho bảng tblCatInOut
        db.execSQL("INSERT INTO tblCatInOut (idCat, idInOut) VALUES (1, 1)"); // Thực phẩm - Thu
        db.execSQL("INSERT INTO tblCatInOut (idCat, idInOut) VALUES (2, 2)"); // Giải trí - Chi
        db.execSQL("INSERT INTO tblCatInOut (idCat, idInOut) VALUES (3, 2)"); // Đi lại - Chi
        db.execSQL("INSERT INTO tblCatInOut (idCat, idInOut) VALUES (4, 1)"); // Sức khỏe - Thu

// Dữ liệu mẫu cho bảng tblTransaction
        db.execSQL("INSERT INTO tblTransaction (name, idCatInOut, amount, day, note) VALUES ('Tiền lương', 1, 1500.00, '01/10/2024', 'Tiền lương tháng 9')");
        db.execSQL("INSERT INTO tblTransaction (name, idCatInOut, amount, day, note) VALUES ('Mua sắm', 2, 200.00, '05/10/2024', 'Chi phí mua sắm')");
        db.execSQL("INSERT INTO tblTransaction (name, idCatInOut, amount, day, note) VALUES ('Xăng xe', 3, 100.00, '06/10/2024', 'Chi phí xăng xe')");
        db.execSQL("INSERT INTO tblTransaction (name, idCatInOut, amount, day, note) VALUES ('Khám bệnh', 4, 250.00, '07/10/2024', 'Chi phí khám sức khỏe')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu có
        db.execSQL("DROP TABLE IF EXISTS tblInOut");
        db.execSQL("DROP TABLE IF EXISTS tblCatInOut");
        db.execSQL("DROP TABLE IF EXISTS tblCategory");
        db.execSQL("DROP TABLE IF EXISTS tblTransaction");
        onCreate(db); // Tạo lại các bảng
    }

    // Phương thức lấy danh sách category dưới dạng Cursor
    public Cursor getCategoriesCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, name FROM tblCategory", null); // Thêm alias cho cột id
    }
}
