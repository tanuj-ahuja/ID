package com.example.android.servurance;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.servurance.data.SignUpContract;
import com.example.android.servurance.data.SignUpDbHelper;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    EditText mName,mDate,mFatherName,mPanNumber;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName =(EditText) findViewById(R.id.editText);
        mDate = (EditText) findViewById(R.id.editText2);
        mFatherName = (EditText) findViewById(R.id.editText3);
        mPanNumber = (EditText) findViewById(R.id.editText4);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        MainActivity.this,
                        R.style.datepicker,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("sd", String.valueOf(year+month+dayOfMonth));
                String date=String.valueOf(year+"/"+month+"/"+dayOfMonth);
               mDate.setText(String.valueOf(date));

            }
        };

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<=0){
                    mName.setError("Enter Name");
                }
                else{
                    mName.setError(null);
                }

            }
        });

        mDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    mDate.setError("Enter Date");
                }
                else {
                    mDate.setError(null);
                }
            }
        });

        mPanNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<=0){
                    mPanNumber.setError("Enter Pan Number");
                }
                else{
                    mPanNumber.setError(null);
                }
            }
        });



        SignUpDbHelper dbHelper=new SignUpDbHelper(this);
        mDb=dbHelper.getWritableDatabase();
    }

    public void addUser(View view){
        String name=mName.getText().toString();
        String date=mDate.getText().toString();
        String fatherName=mFatherName.getText().toString();
        String panNumber=mPanNumber.getText().toString();



        boolean cname=validateName(name);
        boolean cdate=validateAge(date);
        boolean cpan=validatePan(panNumber);
        boolean vpf=validatePanFormat(panNumber);
        if(!cname){
            mName.setError("Enter Name");

        }

        if(!cdate){
            mDate.setError("Enter Date");

        }

        if(!cpan){
            mPanNumber.setError("Enter Pan Number");

        }

        if(!vpf){
            mPanNumber.setError("Pan number is not valid");
        }

        if(!cdate||!cpan||!cname||!vpf){
            return;
        }

        long id=addUserInDb(name,date,fatherName,panNumber);

        if(id!=-1)
        {
            Intent intent=new Intent(this,Welcome.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"DATA could not be SAVED",Toast.LENGTH_LONG).show();

        }



    }



    private boolean validateName(String name){
        if(name.length()==0)
            return false;
        else
            return true;
    }

    private boolean validateAge(String date){
        if(date.length()==0)
            return false;
        else
            return true;
    }

    private boolean validatePan(String panNumber){

        if(panNumber.length()==0)
            return false;
        else
            return true;
    }

    private boolean validatePanFormat(String panNumber){
        int flag=-1;
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

        Matcher matcher = pattern.matcher(panNumber);
// Check if pattern matches
        if (matcher.matches()) {
            flag=0;
        }
        if(flag==0)return true;
        else return false;
    }

    private long addUserInDb(String name,String date,String fatherName,String panNumber){
        ContentValues cv=new ContentValues();
        cv.put(SignUpContract.SignUpEntry.COLUMN_NAME,name);
        cv.put(SignUpContract.SignUpEntry.COLUMN_DATE,date);
        cv.put(SignUpContract.SignUpEntry.COLUMN_FATHER_NAME,fatherName);
        cv.put(SignUpContract.SignUpEntry.COLUMN_PAN_NUMBER,panNumber);
        return mDb.insert(SignUpContract.SignUpEntry.TABLE_NAME,null,cv);
    }
}
