package com.imperialsoupgmail.tesseractexample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imperialsoupgmail.tesseractexample.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

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
    static final int RESULT_LOAD_IMG = 1;
    static private Bitmap photo = null;

    Uri imageUri;
    InputStream imageStream;

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
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                imageStream = getContentResolver().openInputStream(imageUri);
                photo= BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Capture.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(Capture.this, "You haven't picked Image!",Toast.LENGTH_LONG).show();
        }
            btnConfirmCapture.setVisibility(View.VISIBLE);
            logo.setVisibility(View.INVISIBLE);
            textLogo.setVisibility(View.INVISIBLE);

            btnConfirmCapture.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(TAG, "Switch to OCR");
                    CommonResources.photoFinishBitmap = photo;

                    Intent myIntent = new Intent(Capture.this, ChoiceSpl.class);
                    Capture.this.startActivity(myIntent);
                }
            });

    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }
}
