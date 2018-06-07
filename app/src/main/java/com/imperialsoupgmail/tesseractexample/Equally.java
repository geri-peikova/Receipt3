package com.imperialsoupgmail.tesseractexample;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by gery on 1/29/2018.
 */

public class Equally extends AppCompatActivity {
    private static final String TAG = Equally.class.getSimpleName();

    Receipt originalReceipt;
    Bitmap image;
    private TessBaseAPI mTess;
    private String datapath = "";
    private double total_price;

    TextView txtTotal;
    TextView txtPerEach;
    TextView txtTip;
    Button btnTotalCustomers;
    Button btnNewReceipt;
    private int totalCustomers;
    private String num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equally);

        totalCustomers = 0;
        num = null;
        btnTotalCustomers = (Button) findViewById(R.id.btnTotalCustomers);
        btnNewReceipt = (Button) findViewById(R.id.btnNewReceipt);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtPerEach = (TextView) findViewById(R.id.txtPerEach);
        txtTip = (TextView) findViewById(R.id.txtTip);

        btnNewReceipt.setVisibility(View.INVISIBLE);
        txtTotal.setVisibility(View.INVISIBLE);
        txtPerEach.setVisibility(View.INVISIBLE);
        txtTip.setVisibility(View.INVISIBLE);

        image = BitmapFactory.decodeResource(getResources(), R.drawable.bill_bg);//TODO: Change the image to be the one u take with the camera

        //initialize Tesseract API
        String language = "bul";
        datapath = getFilesDir().toString() + "/";// + "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);
        processImage();
        Log.d(TAG, "Waiting for button click");


        if(totalCustomers!=0){
            txtTotal.setVisibility(View.VISIBLE);
            txtPerEach.setVisibility(View.VISIBLE);
            txtTip.setVisibility(View.VISIBLE);
            btnNewReceipt.setVisibility(View.VISIBLE);
            btnTotalCustomers.setVisibility(View.INVISIBLE);
            double result = total_price/totalCustomers;

            //  DecimalFormat df = new DecimalFormat("#.##");
            //  result = Double.valueOf(df.format(result));

            txtTotal.setText("Total: " + total_price/2 + " lv");
            // txtPerEach.setText("Per each: " + result + " lv");

            //  result = result*totalCustomers - total_price;
            //  txtTip.setText("Tip: " + result + " lv");


            btnNewReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Equally.this, Capture.class);
                    Equally.this.startActivity(myIntent);
                }
            });
        }else{
            btnTotalCustomers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu ppMenu = new PopupMenu(Equally.this, btnTotalCustomers);
                    ppMenu.getMenuInflater().inflate(R.menu.popup_count, ppMenu.getMenu());
                    ppMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            num = item.toString();
                            totalCustomers = Integer.parseInt(num);
                            Toast.makeText(Equally.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                            txtTotal.setVisibility(View.VISIBLE);
                            txtPerEach.setVisibility(View.VISIBLE);
                            txtTip.setVisibility(View.VISIBLE);
                            btnNewReceipt.setVisibility(View.VISIBLE);
                            btnTotalCustomers.setVisibility(View.INVISIBLE);
                            total_price/=2;
                            double result = total_price/totalCustomers;

                            DecimalFormat df = new DecimalFormat("#.##");
                            result = Double.valueOf(df.format(result));
                            if(result<0){
                                result+=totalCustomers*0.01;
                            }

                            txtTotal.setText("Total: " + total_price + " lv");
                            txtPerEach.setText("Per each: " + result + " lv");

                            result = result*totalCustomers - total_price;
                            if(result<0){
                                result+=totalCustomers*0.01;
                            }

                            result = Double.valueOf(df.format(result));
                            txtTip.setText("Tip: " + result + " lv");


                            btnNewReceipt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent myIntent = new Intent(Equally.this, Capture.class);
                                    Equally.this.startActivity(myIntent);
                                }
                            });

                           return true;
                        }
                    });
                    ppMenu.show();
                }

            });
        }


    }

    public void popupMenu(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu ppMenu = new PopupMenu(Equally.this, button);
                ppMenu.getMenuInflater().inflate(R.menu.popup_count, ppMenu.getMenu());
                ppMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Toast.makeText(Equally.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        num = item.toString();
                        if (num != null) {
                            totalCustomers = Integer.parseInt(num);
                        }
                        return true;
                    }
                });
                ppMenu.show();
            }

        });
    }

  /**  public void info(int count){
        txtTotal.setVisibility(View.VISIBLE);
        txtPerEach.setVisibility(View.VISIBLE);
        txtTip.setVisibility(View.VISIBLE);
        btnNewReceipt.setVisibility(View.VISIBLE);
        btnTotalCustomers.setVisibility(View.INVISIBLE);
        double result = total_price/count;

        DecimalFormat df = new DecimalFormat("#.##");
        result = Double.valueOf(df.format(result));

        txtTotal.setText("Total: " + total_price + " lv");
        txtPerEach.setText("Per each: " + result + " lv");

        result = result*count - total_price;
        txtTip.setText("Tip: " + result + " lv");

    }
**/

    public void processImage(){
        String[] text = null;
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        extractOrder(OCRresult);
    }

    private void extractOrder(String OCRresult){
        total_price = 0;
        originalReceipt = new Receipt();
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
                }
                if (notFound == false) {
                    // line has two numbers at the end
                    // tokens[tokens.length()-2], tokens[tokens.length()-1]
                    Log.d(TAG, line);
                    //
                    String name = tokens[0];

                    Log.d(TAG, tokens[tokens.length - 1]);
                    for(int i = 1; i <= tokens.length - 3; i++){
                        name = name + " " + tokens[i];
                    }
                    tokens[tokens.length - 2] = tokens[tokens.length - 2].replace(',', '.');
                    tokens[tokens.length - 1] = tokens[tokens.length - 1].replace(',', '.');
                    addOrderToOriginalReceipt(name, tokens[tokens.length - 2], tokens[tokens.length - 1]);
                    total_price+=Double.parseDouble(tokens[tokens.length - 1]);
                }
            }
        }
        Log.d(TAG, Double.toString(total_price));
    }

    private void addOrderToOriginalReceipt(String name, String count, String price){

        Order order = new Order(name,  Double.parseDouble(count), Double.parseDouble(price));

        Log.d(TAG, order.getName());
        Log.d(TAG, String.valueOf(order.getCount()));
        Log.d(TAG, String.valueOf(order.getPrice()));

        originalReceipt.addOrder(order);
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
