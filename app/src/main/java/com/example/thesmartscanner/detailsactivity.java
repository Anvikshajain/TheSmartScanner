package com.example.thesmartscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class detailsactivity extends AppCompatActivity {

    Button approve;
    EditText uname,umobile,uemail,unoofpersons,uguestof,udate;
    String name,mobile,email,noofpersons,guestof,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsactivity);

        uname=findViewById(R.id.name);
        umobile=findViewById(R.id.mobile_no);
        unoofpersons=findViewById(R.id.noofpersons);
        uemail=findViewById(R.id.email);
        uguestof=findViewById(R.id.guestof);
        udate=findViewById(R.id.date);
        approve=findViewById(R.id.approve_btn);

        String[] dataArray=scanactivity.getResult();
        getValues(dataArray);

        uname.setText(name);
        umobile.setText(mobile);
        unoofpersons.setText(noofpersons);
        uemail.setText(email);
        uguestof.setText(guestof);
        udate.setText(date);

        approve.setOnClickListener(v -> {
            sendUserData();
            startActivity(new Intent(detailsactivity.this,approvedactivity.class));
        });
    }
    public void getValues(String[] dataArray){
        name= dataArray[1].trim();
        mobile= dataArray[3].trim();
        email= dataArray[5].trim();
        noofpersons= dataArray[7].trim();
        guestof= dataArray[9].trim();
        date= dataArray[11].trim();
    }
    public void sendUserData(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference();
        Details details=new Details(name,mobile,noofpersons,email,guestof,date);
        myRef.child("ID: ").child(genID(name)).setValue(details);
    }

    public static String genID(String name){
        return "TSS"+name.substring(0,3);
    }
}