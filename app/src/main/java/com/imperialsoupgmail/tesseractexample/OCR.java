package com.imperialsoupgmail.tesseractexample;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class OCR extends AppCompatActivity {

    private static final String TAG = OCR.class.getSimpleName();

    Button btnOCR;
    Bitmap image;
    private TessBaseAPI mTess;
    String datapath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr);

        btnOCR = (Button)findViewById(R.id.btnOCR);
        //init image
        image = BitmapFactory.decodeResource(getResources(), R.drawable.bill_bg);//TODO: Change the image to be the one u take with the camera

        //initialize Tesseract API
        String language = "bul";
        datapath = getFilesDir().toString() + "/";// + "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);
        Log.d(TAG, "Waiting for button click");
        btnOCR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Click and OCR");
                processImage();
            }
        });
    }

    public void processImage(){
        String[] text = null;
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        extractReceipts(OCRresult);
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult);

    }

    private void extractReceipts(String OCRresult){
        String[] text = null;
        text = OCRresult.split("\n+");
        Log.d(TAG, OCRresult);
        Log.d(TAG, "!!!!!!!");
        Log.d(TAG, text[12]);
        for (String line : text) {
            String[] tokens = line.split("\\ +");
            int idx = tokens.length-1;
            boolean notFound = false; // number

            if (tokens.length >= 3) {
                String tok = tokens[idx];
                while (tok.length() == 0) {
                    idx --;
                    tok = tokens[idx];
                }
                for (int n = tok.length() - 1; n >= 0 ; n--) {
                    Character c = tok.charAt(n);
                    if (!(Character.isDigit(c) || c.charValue() == 44 || c.equals("."))) {
                        notFound = true; // not number
                        break;
                    }
                }

                if (notFound == false) {
//                    Log.d(TAG, "[1] " + tok);
                    idx --;
                    tok = tokens[idx];
                    for (int n = tok.length() - 1; n >= 0 ; n--) {
                        Character c = tok.charAt(n);
                        if (!(Character.isDigit(c) || c.charValue() == 44 || c.equals("."))) {
                            notFound = true; // not number
                            break;
                        }
                    }
//                    if (notFound == false) {
//                        Log.d(TAG, "[2] " + tok);
//                    }
                }
                if (notFound == false) {
                    // line has two numbers at the end
                    // tokens[tokens.length()-2], tokens[tokens.length()-1]
                    Log.d(TAG, line);
                }
            }
        }
    }

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
                copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            String datafilepath1 = datapath+ "/tessdata/bul.traineddata";
            File datafile1 = new File(datafilepath);

            if (!datafile.exists() || !datafile1.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            filepath = datapath + "/tessdata/bul.traineddata";
            assetManager = getAssets();

            instream = assetManager.open("tessdata/bul.traineddata");
            outstream = new FileOutputStream(filepath);

            buffer = new byte[1024];
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
