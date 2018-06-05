package com.imperialsoupgmail.tesseractexample;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChoiceSpl extends AppCompatActivity {

    private static final String TAG = ChoiceSpl.class.getSimpleName();
    Button btnSplitSeparately;
    Button btnSplitEqually;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splitter);

        btnSplitSeparately = (Button)findViewById(R.id.btnSplitSeparately);
        btnSplitEqually = (Button)findViewById(R.id.btnSplitEqually);

        Log.d(TAG, "Splitter opened...waiting for click");

        btnSplitEqually.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "Switch to OCR");

                Intent myIntent = new Intent(ChoiceSpl.this, Equally.class);
                ChoiceSpl.this.startActivity(myIntent);
            }
        });

        btnSplitSeparately.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "Switch to OCR");

                Intent myIntent = new Intent(ChoiceSpl.this, Separatelly.class);
                ChoiceSpl.this.startActivity(myIntent);
            }
        });

    }

}
