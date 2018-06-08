package com.imperialsoupgmail.tesseractexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Separatelly extends AppCompatActivity {
    private static final String TAG = Separatelly.class.getSimpleName();

    Receipt rcp1;
    Receipt rcp2;
    Receipt rcp3;
    Receipt rcp4;
    Receipt rcp5;

    int orders;
    int i;
    int count1;
    int count2;
    int count3;
    int count4;
    int count5;

    int test;

    Double price1;
    Double price2;
    Double price3;
    Double price4;
    Double price5;




    Receipt originalReceipt;
    Bitmap image;
    private TessBaseAPI mTess;
    private String datapath = "";
    private double total_price;

    ImageView imgLogo;
    TextView txtInfo;
    TextView txtTotalS;
    TextView txtLogo;
    TextView txtFood;
    Button btnP1;
    Button btnP2;
    Button btnP3;
    Button btnP4;
    Button btnP5;
    Button btnTotalCustomers;
    Button btnNewReceipt;
    private int totalCustomers;
    private String num;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.separately);

        rcp1 = new Receipt();
        rcp2 = new Receipt();
        rcp3 = new Receipt();
        rcp4 = new Receipt();
        rcp5 = new Receipt();


        count1 = 0;
        count2 = 0;
        count3 = 0;
        count4 = 0;
        count5 = 0;

        price1 = 0.00;
        price2 = 0.00;
        price3 = 0.00;
        price4 = 0.00;
        price5 = 0.00;

        totalCustomers = 0;
        num = null;
        imgLogo = (ImageView) findViewById(R.id.logo);
        txtLogo = (TextView) findViewById(R.id.textLogo);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtTotalS = (TextView) findViewById(R.id.txtTotalS);
        btnTotalCustomers = (Button) findViewById(R.id.btnTotalCustomers);
        btnNewReceipt = (Button) findViewById(R.id.btnNewReceipt);
        txtFood = (TextView) findViewById(R.id.txtFood);
        btnP1 = (Button) findViewById(R.id.btnP1);
        btnP2 = (Button) findViewById(R.id.btnP2);
        btnP3= (Button) findViewById(R.id.btnP3);
        btnP4 = (Button) findViewById(R.id.btnP4);
        btnP5 = (Button) findViewById(R.id.btnP5);

        btnNewReceipt.setVisibility(View.INVISIBLE);
        txtTotalS.setVisibility(View.INVISIBLE);
        txtInfo.setVisibility(View.INVISIBLE);
        txtFood.setVisibility(View.INVISIBLE);
        btnP1.setVisibility(View.INVISIBLE);
        btnP2.setVisibility(View.INVISIBLE);
        btnP3.setVisibility(View.INVISIBLE);
        btnP4.setVisibility(View.INVISIBLE);
        btnP5.setVisibility(View.INVISIBLE);

        bitmap=CommonResources.photoFinishBitmap;
        image = bitmap;//TODO: Change the image to be the one u take with the camera

        //initialize Tesseract API
        String language = "bul";
        datapath = getFilesDir().toString() + "/";// + "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);
        processImage();
        Log.d(TAG, "Waiting for button click");


        if(totalCustomers!=0){
            btnNewReceipt.setVisibility(View.VISIBLE);
            btnTotalCustomers.setVisibility(View.INVISIBLE);
            double result = total_price/totalCustomers;

            //  DecimalFormat df = new DecimalFormat("#.##");
            //  result = Double.valueOf(df.format(result));

            // txtPerEach.setText("Per each: " + result + " lv");

            //  result = result*totalCustomers - total_price;
            //  txtTip.setText("Tip: " + result + " lv");


            btnNewReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Separatelly.this, Capture.class);
                    Separatelly.this.startActivity(myIntent);
                }
            });
        }else{
            btnTotalCustomers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu ppMenu = new PopupMenu(Separatelly.this, btnTotalCustomers);
                    ppMenu.getMenuInflater().inflate(R.menu.popup_count, ppMenu.getMenu());
                    ppMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            num = item.toString();
                            totalCustomers = Integer.parseInt(num);
                            Toast.makeText(Separatelly.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                            btnNewReceipt.setVisibility(View.VISIBLE);
                            imgLogo.setVisibility(View.INVISIBLE);
                            txtLogo.setVisibility(View.INVISIBLE);
                            btnTotalCustomers.setVisibility(View.INVISIBLE);
                            txtInfo.setVisibility(View.VISIBLE);
                            txtTotalS.setVisibility(View.VISIBLE);
                            txtTotalS.setText("Total: " + String.valueOf(total_price) + " lv");
                            txtFood.setVisibility(View.VISIBLE);

                            switch (totalCustomers) {
                                case 1:  btnP1.setVisibility(View.VISIBLE);
                                    break;
                                case 2:  btnP1.setVisibility(View.VISIBLE);
                                         btnP2.setVisibility(View.VISIBLE);
                                    break;
                                case 3:  btnP1.setVisibility(View.VISIBLE);
                                         btnP2.setVisibility(View.VISIBLE);
                                         btnP3.setVisibility(View.VISIBLE);
                                    break;
                                case 4:  btnP1.setVisibility(View.VISIBLE);
                                         btnP2.setVisibility(View.VISIBLE);
                                         btnP3.setVisibility(View.VISIBLE);;
                                         btnP4.setVisibility(View.VISIBLE);
                                    break;
                                case 5:  btnP1.setVisibility(View.VISIBLE);
                                         btnP2.setVisibility(View.VISIBLE);
                                         btnP3.setVisibility(View.VISIBLE);
                                         btnP4.setVisibility(View.VISIBLE);
                                         btnP5.setVisibility(View.VISIBLE);
                            }

                            orders = originalReceipt.countOrders();
                                    i = 0;
                                   foodClicker(0);

                         //   "     Person 1          " + count1 + "               " + originalReceipt.getOrder(i).getPrice()




                            btnNewReceipt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent myIntent = new Intent(Separatelly.this, Capture.class);
                                    Separatelly.this.startActivity(myIntent);
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
                final PopupMenu ppMenu = new PopupMenu(Separatelly.this, button);
                ppMenu.getMenuInflater().inflate(R.menu.popup_count, ppMenu.getMenu());
                ppMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Toast.makeText(Separatelly.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
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
                    Double c = Double.parseDouble(tokens[tokens.length - 2]);

                    if(c > 1.00 && c<20){
                        Double number = Double.parseDouble(tokens[tokens.length - 2]);
                        Double numb = Double.parseDouble(tokens[tokens.length - 1]);
                        numb/=c;
                        number/=c;
                        for(int n = 1; n <= c;n++) {
                            total_price+=numb;
                            DecimalFormat df = new DecimalFormat("#.##");
                            total_price = Double.valueOf(df.format(total_price));
                            addOrderToOriginalReceipt(name + "(" + n + ")", number.toString(), numb.toString());
                        }
                    }else{
                        addOrderToOriginalReceipt(name, tokens[tokens.length - 2], tokens[tokens.length - 1]);
                        total_price+=Double.parseDouble(tokens[tokens.length - 1]);
                        DecimalFormat df = new DecimalFormat("#.##");
                        total_price = Double.valueOf(df.format(total_price));
                    }

                }
            }
        }
        int last = originalReceipt.countOrders()- 1;
        if(last<0){
            Toast.makeText(Separatelly.this, "Cannot resolve picture!", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Separatelly.this, Capture.class);
            Separatelly.this.startActivity(myIntent);

        }else {
            Order ord = originalReceipt.getOrder(last);
            total_price -= ord.getPrice();
            originalReceipt.removeOrder(ord);

            Log.d(TAG, Double.toString(total_price));

        }

    }

    private void addOrderToOriginalReceipt(String name, String count, String price){

        Order order = new Order(name,  Double.parseDouble(count), Double.parseDouble(price));

        Log.d(TAG, order.getName());
        Log.d(TAG, String.valueOf(order.getCount()));
        Log.d(TAG, String.valueOf(order.getPrice()));

        originalReceipt.addOrder(order);
    }

    private void foodClicker(int counter){
        i=counter;
        Order rd = originalReceipt.getOrder(i);
        String name = rd.getName();
        txtFood.setText(name);

        btnP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order rd = originalReceipt.getOrder(i);
                Double num = rd.getPrice();
                DecimalFormat df = new DecimalFormat("#.##");
                num = Double.valueOf(df.format(num));
                price1+=num;
                price1 = Double.valueOf(df.format(price1));
                count1++;
                rcp1.addOrder(rd);

                btnP1.setText("     Person 1                " + count1 + "                  " + price1 + " lv");

               btnHolder(btnP1,"Person 1", rcp1 );
                i++;
                int sum = orders-1;
                if(i <= sum){
                    foodClicker(i);
                }else{
                    Toast.makeText(Separatelly.this, "NO MORE ORDERS!", Toast.LENGTH_LONG).show();
                    clicker(btnP1);
                    clicker(btnP2);
                    clicker(btnP3);
                    clicker(btnP4);
                    clicker(btnP5);

                    btnHolder(btnP1,"Person 1", rcp1 );
                    btnHolder(btnP2,"Person 2", rcp2 );
                    btnHolder(btnP3,"Person 3", rcp3 );
                    btnHolder(btnP4,"Person 4", rcp4 );
                    btnHolder(btnP5,"Person 5", rcp5 );
                }



            }
        });

        btnP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order rd = originalReceipt.getOrder(i);
                Double num = rd.getPrice();
                DecimalFormat df = new DecimalFormat("#.##");
                num = Double.valueOf(df.format(num));
                price2+=num;
                price2 = Double.valueOf(df.format(price2));
                count2++;
                rcp2.addOrder(rd);

                btnP2.setText("     Person 2               " + count2 + "                  " + price2 + " lv");

                btnHolder(btnP2,"Person 2", rcp2 );
                i++;
                int sum = orders-1;
                if(i <= sum){
                    foodClicker(i);
                }else{
                    Toast.makeText(Separatelly.this, "NO MORE ORDERS!", Toast.LENGTH_LONG).show();
                    clicker(btnP1);
                    clicker(btnP2);
                    clicker(btnP3);
                    clicker(btnP4);
                    clicker(btnP5);

                    btnHolder(btnP1,"Person 1", rcp1 );
                    btnHolder(btnP2,"Person 2", rcp2 );
                    btnHolder(btnP3,"Person 3", rcp3 );
                    btnHolder(btnP4,"Person 4", rcp4 );
                    btnHolder(btnP5,"Person 5", rcp5 );

                }
            }
        });

        btnP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order rd = originalReceipt.getOrder(i);
                Double num = rd.getPrice();
                DecimalFormat df = new DecimalFormat("#.##");
                num = Double.valueOf(df.format(num));
                price3+=num;
                price3 = Double.valueOf(df.format(price3));
                count3++;
                rcp3.addOrder(rd);

                btnP3.setText("     Person 3               " + count3 + "                  " + price3 + " lv");

                btnHolder(btnP3,"Person 3", rcp3 );
                i++;
                int sum = orders-1;
                if(i <= sum){
                    foodClicker(i);
                }else{
                    Toast.makeText(Separatelly.this, "NO MORE ORDERS!", Toast.LENGTH_LONG).show();
                    clicker(btnP1);
                    clicker(btnP2);
                    clicker(btnP3);
                    clicker(btnP4);
                    clicker(btnP5);

                    btnHolder(btnP1,"Person 1", rcp1 );
                    btnHolder(btnP2,"Person 2", rcp2 );
                    btnHolder(btnP3,"Person 3", rcp3 );
                    btnHolder(btnP4,"Person 4", rcp4 );
                    btnHolder(btnP5,"Person 5", rcp5 );

                }
            }
        });

        btnP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order rd = originalReceipt.getOrder(i);
                Double num = rd.getPrice();
                DecimalFormat df = new DecimalFormat("#.##");
                num = Double.valueOf(df.format(num));
                price4+=num;
                price4 = Double.valueOf(df.format(price4));
                count4++;
                rcp4.addOrder(rd);

                btnP4.setText("     Person 4               " + count4 + "                  " + price4 + " lv");

                btnHolder(btnP4,"Person 4", rcp4 );
                i++;
                int sum = orders-1;
                if(i <= sum){
                    foodClicker(i);
                }else{
                    Toast.makeText(Separatelly.this, "NO MORE ORDERS!", Toast.LENGTH_LONG).show();
                    clicker(btnP1);
                    clicker(btnP2);
                    clicker(btnP3);
                    clicker(btnP4);
                    clicker(btnP5);

                    btnHolder(btnP1,"Person 1", rcp1 );
                    btnHolder(btnP2,"Person 2", rcp2 );
                    btnHolder(btnP3,"Person 3", rcp3 );
                    btnHolder(btnP4,"Person 4", rcp4 );
                    btnHolder(btnP5,"Person 5", rcp5 );

                }
            }
        });

        btnP5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order rd = originalReceipt.getOrder(i);
                Double num = rd.getPrice();
                DecimalFormat df = new DecimalFormat("#.##");
                num = Double.valueOf(df.format(num));
                price5+=num;
                price5 = Double.valueOf(df.format(price5));
                count5++;
                rcp5.addOrder(rd);

                btnP5.setText("     Person 5               " + count5 + "                  " + price5 + " lv");

                btnHolder(btnP5,"Person 5", rcp5 );
                i++;
                int sum = orders-1;
                if(i <= sum){
                    foodClicker(i);
                }else{
                    Toast.makeText(Separatelly.this, "NO MORE ORDERS!", Toast.LENGTH_LONG).show();
                    clicker(btnP1);
                    clicker(btnP2);
                    clicker(btnP3);
                    clicker(btnP4);
                    clicker(btnP5);

                    btnHolder(btnP1,"Person 1", rcp1 );
                    btnHolder(btnP2,"Person 2", rcp2 );
                    btnHolder(btnP3,"Person 3", rcp3 );
                    btnHolder(btnP4,"Person 4", rcp4 );
                    btnHolder(btnP5,"Person 5", rcp5 );

                }
            }
        });
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

    public String convertToText(Receipt receipt){
        String text="";
        int count = receipt.countOrders();
        for (int counter = 0; counter<count;counter++){
            Order order = receipt.getOrder(counter);
            String name = order.getName();
            Double c = order.getCount();
            String cn = Double.toString(c);
            Double pr = order.getPrice();
            String price = Double.toString(pr);

            text+="\n" + name + "     " + price + "lv";

        }

        return text;
    }

    public void btnHolder(Button button, final String title, final Receipt receipt){
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Separatelly.this)
                        .setTitle(title)
                        .setMessage("Order:                  Price: \n" + convertToText(receipt))
                        .show();
                return false;
            }
        });
    }

    public boolean clicker(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return false;
    }

}
