package com.imperialsoupgmail.tesseractexample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imperialsoupgmail.tesseractexample.R;

import org.w3c.dom.Text;

/**
 * Created by gery on 1/29/2018.
 */

public class Capture extends AppCompatActivity {

    private static final String TAG = Capture.class.getSimpleName();

    Button btnCapture;
    Button btnConfirmCapture;
    ImageView logo;
    ImageView imageView;
    TextView textLogo;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static private Bitmap photo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture);

        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnConfirmCapture = (Button) findViewById(R.id.btnConfirmCapture);
        logo = (ImageView) findViewById(R.id.logo);
        imageView = (ImageView) findViewById(R.id.imageView);
        textLogo = (TextView) findViewById(R.id.textLogo);
        btnConfirmCapture.setVisibility(View.INVISIBLE);

        if(!hasCamera()){
            Log.d(TAG, "Camera is not available");
            btnCapture.setEnabled(false);
        }else {
            Log.d(TAG, "Camera launch");
            btnCapture.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    launchCamera();
                }
            });
        }

    }

    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
            btnConfirmCapture.setVisibility(View.VISIBLE);
            logo.setVisibility(View.INVISIBLE);
            textLogo.setVisibility(View.INVISIBLE);

            btnConfirmCapture.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(TAG, "Switch to OCR");

                    Intent myIntent = new Intent(Capture.this, ChoiceSpl.class);
                    Capture.this.startActivity(myIntent);
                }
            });

        }

    }

    static public Bitmap getPhoto(){
        return photo;
    }
}
