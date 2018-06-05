package com.imperialsoupgmail.tesseractexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by gery on 1/29/2018.
 */

public class Equally extends AppCompatActivity {

    private static final String TAG = Equally.class.getSimpleName();

    Button btnTotalCustomers;
    EditText txtTotal;
    EditText txtPerEach;
    EditText txtTip;
    Button btnNewReceipt;
    public int totalCustomers;
    public double total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equally);
        Bundle extras = getIntent().getExtras();

        total_price = extras.getDouble("TOTAL_PRICE");
        totalCustomers = 0;
        btnTotalCustomers = (Button) findViewById(R.id.btnTotalCustomers);
        btnNewReceipt = (Button) findViewById(R.id.btnNewReceipt);
        txtTotal = (EditText) findViewById(R.id.txtTotal);
        txtPerEach = (EditText) findViewById(R.id.txtPerEach);
        txtTip = (EditText) findViewById(R.id.txtTip);

        btnNewReceipt.setVisibility(View.INVISIBLE);
        txtTotal.setVisibility(View.INVISIBLE);
        txtPerEach.setVisibility(View.INVISIBLE);
        txtTip.setVisibility(View.INVISIBLE);


    }

    public void popUpCount(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Equally.this);
        builder.setTitle("Between how may people do you want to split te bill?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Equally.this, totalCustomers, Toast.LENGTH_SHORT).show();

            }
        });
        builder.setSingleChoiceItems(R.array.ip, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                totalCustomers = which;
            }
        });
        builder.show();

    }

    public void info(int count){
        txtTotal.setVisibility(View.VISIBLE);
        txtPerEach.setVisibility(View.VISIBLE);
        txtTip.setVisibility(View.VISIBLE);
        btnNewReceipt.setVisibility(View.VISIBLE);
        btnTotalCustomers.setVisibility(View.INVISIBLE);

        txtTotal.setText("Total: " + total_price);


    }

}
