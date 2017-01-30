package com.example.shaloin.seventhassignmentd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shaloin on 29/1/17.
 */

public class AGSQLiteHelper extends SQLiteOpenHelper {
    private static final int database_VERSION=1;
    private static final String database_NAME="EmployeeDB";
    private static final String table_EMPLOYEES="employees";
    private static final String employee_ID="id";
    private static final String employee_FIRST="first";
    private static final String employee_LAST="last";

    private static final String[] COLUMNS={employee_ID,employee_FIRST,employee_LAST};

    public AGSQLiteHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    public AGSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BOOK_TABLE="CREATE TABLE employees ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "first TEXT, " + "last TEXT )";
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS employees");
        this.onCreate(sqLiteDatabase);

    }

    public void createEmployee(Employee employee){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(employee_ID,employee.getId());
        values.put(employee_FIRST,employee.getFirstName());
        values.put(employee_LAST,employee.getLastName());
        long id=db.insert(table_EMPLOYEES,null,values);
        db.close();
    }

    public Employee readEmployee(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(table_EMPLOYEES,COLUMNS,"id = ?",new String[] {String.valueOf(id)},null,null,null,null);
        Employee employee=new Employee();
        if(cursor!=null && cursor.moveToFirst()){
            do{

                employee.setId((cursor.getString(0)));
                employee.setFirstName(cursor.getString(1));
                employee.setLastName(cursor.getString(2));

            }while(cursor.moveToNext());
        }
        return employee;
    }
    public List<Employee> getAllEmployee(){
        List<Employee> employees=new LinkedList<>();
        String query="SELECT * FROM " + table_EMPLOYEES;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        Employee employee=new Employee();
        if(cursor.moveToFirst()){
            do{

                employee.setId((cursor.getString(0)));
                employee.setFirstName(cursor.getString(1));
                employee.setLastName(cursor.getString(2));
                employees.add(employee);
            }while (cursor.moveToNext());
        }
        return employees;
    }

    public int updateBook(Employee employee){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",employee.getId());
        values.put("first",employee.getFirstName());
        values.put("last",employee.getLastName());

        int i=db.update(table_EMPLOYEES,values,employee_ID + " = ?",new String[] {String.valueOf(employee.getId())});
        db.close();
        return i;
    }


}

