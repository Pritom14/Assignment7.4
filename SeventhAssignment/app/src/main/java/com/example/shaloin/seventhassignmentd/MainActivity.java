package com.example.shaloin.seventhassignmentd;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    List<Employee> list;
    ArrayAdapter<String> myAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AGSQLiteHelper db=new AGSQLiteHelper(this);
        db.onUpgrade(db.getWritableDatabase(),1,2);
        db.createEmployee(new Employee("ID : 1","First Name:X","Last Name:Y"));
        db.createEmployee(new Employee("ID : 2","First Name:M","Last Name:N"));
        db.createEmployee(new Employee("ID : 3","First Name:P","Last Name:Q"));

        list=db.getAllEmployee();
        List<String> listTitle=new ArrayList<String>();
        for (int i=0;i<list.size();i++){
            listTitle.add(i,list.get(i).getFirstName());
        }
        myAdapter=new ArrayAdapter<String>(this,R.layout.row_layout,R.id.firstNameID,listTitle);
        getListView().setOnItemClickListener(this);
        setListAdapter(myAdapter);
    }
}
