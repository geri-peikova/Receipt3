package com.imperialsoupgmail.tesseractexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equally);

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

        builder.setSingleChoiceItems(R.array.ip, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                totalCustomers = which;
            }
        });
        builder.show();

    }

}
