package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "MainActivity";
    //declare your variables for the widgets
    TextView totalSpin;
    DecimalFormat dec;
    TextView tv_Amount;
    TextView tip;
    TextView totalAmount;
    EditText billAmount;
    SeekBar sBar;
    double percent;
    double bill_amountDouble;
    String amountString;
    String shareButton;
    String radioText;
    RadioGroup radioGroup;
    Spinner spinner;
    int spinNumber;
    ArrayAdapter<CharSequence> adapter;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "inside of onCreate method");

        widgetsReferences();

        radioGroup.check(R.id.no);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton checkedRadioButton = findViewById(checkedId);

            String text = checkedRadioButton.getText().toString();
            radioText = "" + text;

        });

        billAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence k, int begin, int count, int after) {

            }
       /* Note:   int i, int i1, and int i2
       represent start, before, count respectively
       The charSequence is converted to a String and parsed to a double for you  */

            @Override
            public void onTextChanged(CharSequence k, int begin, int before, int count) {
                Log.d(TAG, "inside of TextChange method");

                try {

                    amountString = k.toString();
                    bill_amountDouble = Double.parseDouble(k.toString());
                }

                catch (NumberFormatException e){ // Exempt from errors if input value is empty

                }
                calculate(); //perform tip and total calculation and update UI by calling calculate
                Log.d(TAG, "end of TextChange method");
            }
            @Override
            public void afterTextChanged(Editable k) {

            }
        });

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tv_Amount.setText(progress + "" + "%");
                percent = progress; //calculate percent based on seeker value
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

    }

    private void widgetsReferences() {

        Log.d(TAG, "inside of widgetsReferences method");

        Objects.requireNonNull(getSupportActionBar()).setTitle("Tip Calculator");

        toolbar =findViewById(R.id.toolbar);
        radioGroup = findViewById(R.id.radioGroup);
        spinner =findViewById(R.id.numSpin);
        totalSpin = findViewById(R.id.resultSpinTv);
        adapter = ArrayAdapter.createFromResource(this, R.array.numbers, R.layout.activity_info);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        dec = new DecimalFormat("0.00");
        sBar = findViewById(R.id.seekBar);
        billAmount = findViewById(R.id.EnterAmount);
        tv_Amount = findViewById(R.id.PercentageTv);


        tip = findViewById(R.id.TipTotalTv);
        totalAmount = findViewById(R.id.TotalAmountTv);

        Log.d(TAG, "End of widgetsReferences method");
    }

    // calculate and display tip and total amounts
    @SuppressLint("SetTextI18n")
    public void calculate(){
        Log.d(TAG, "inside of calculate method");
        try{
            switch (radioText.toLowerCase()) {
                case "total"://switch case

                    try {

                        if (amountString.isEmpty() || amountString.equals("0") || amountString.startsWith("0")){

                            tip.setText("$0.00");
                            totalAmount.setText("$0.00");
                            bill_amountDouble = 0.00;
                            sBar.setProgress(0);
                        }
                        double tipTotal;
                        tipTotal = ((bill_amountDouble * percent) / 100);

                        Log.e("", "How much is it?" + bill_amountDouble);

                        tip.setText("$" + dec.format(tipTotal));
                        double totalString = Math.ceil(bill_amountDouble + tipTotal);
                        totalAmount.setText("$" + dec.format(totalString));
                        totalSpin.setText("$" + dec.format(totalString/spinNumber));

                        shareButton = shareMsg(dec.format(bill_amountDouble), dec.format(tipTotal), dec.format(totalString),
                                dec.format(totalString / spinNumber));
                    }
                    catch (NullPointerException e){
                        sBar.setProgress(0);
                    }

                    break;

                case "tip":

                    try {

                        if (amountString.isEmpty() || amountString.equals("0") || amountString.startsWith("0")){

                            tip.setText("$0.00");
                            totalAmount.setText("$0.00");
                            bill_amountDouble = 0.00;
                            sBar.setProgress(0);
                        }
                        double tipTotal;
                        //The ceil() method rounds a number UPWARDS to the nearest integer,
                        tipTotal = Math.ceil((bill_amountDouble * percent) / 100);

                        Log.e("", "How much is it?" + bill_amountDouble);

                        tip.setText("$" + dec.format(tipTotal));
                        double totalString = bill_amountDouble + tipTotal;
                        totalAmount.setText("$" + dec.format(totalString));
                        totalSpin.setText("$" + dec.format(totalString/spinNumber));

                        shareButton = shareMsg(dec.format(bill_amountDouble), dec.format(tipTotal), dec.format(totalString),
                                dec.format(totalString / spinNumber));
                    }
                    catch (NullPointerException e){
                        sBar.setProgress(0);
                    }

                    break;
                case "no": //no
                    try {


                        if (amountString.isEmpty() || amountString.equals("0") || amountString.startsWith("0")) {

                            tip.setText("$0.00");
                            totalAmount.setText("$0.00");
                            bill_amountDouble = 0.00;
                            sBar.setProgress(0);
                        }
                        double tipTotal;
                        tipTotal = (bill_amountDouble * percent) / 100;

                        Log.e("", "What is it?" + bill_amountDouble);

                        tip.setText("$" + dec.format(tipTotal));
                        double totalString = bill_amountDouble + tipTotal;
                        totalAmount.setText("$" + dec.format(totalString));
                        totalSpin.setText("$" + dec.format(totalString / spinNumber));

                        //private string shareMsg

                        shareButton = shareMsg(dec.format(bill_amountDouble), dec.format(tipTotal), dec.format(totalString),
                                dec.format(totalString / spinNumber));


                    } catch (NullPointerException e) {
                        sBar.setProgress(0);
                    }
                    break;
            }
        }catch (NullPointerException e) {
            radioText = "no";
        }
        Log.d(TAG, "end of calculate method");
    }

public void checkButton(View view){
        //checkButton for radio calculation
        calculate();
}

public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    Log.d(TAG, "inside of onItemSelected method");

    String item = parent.getItemAtPosition(position).toString();
    spinNumber = Integer.parseInt(item);
    calculate();
    Log.d("TAG", " " + spinNumber);
    Log.d(TAG, "end of onItemSelected method");

}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create a menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "inside of onOptionsItemSelected method");

        switch(item.getItemId()){
            case R.id.info://info switch
                openDialog();//create a method and call it
                break;
            case R.id.share:
                sendText();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void sendText() {
        Log.d(TAG, "inside of sendText method");

        String theText = shareButton;
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("pick an app")
                .setText(theText)
                .startChooser();
        Log.d(TAG, "end of sendText method");
    }

    public void openDialog(){
        Log.d(TAG, "inside of dialog method");

        info dialog = new info();
        dialog.show(getSupportFragmentManager(), "dialog");

        Log.d(TAG, "end of dialog method");
    }

    private String shareMsg(String bill, String tip, String total , String perPerson){

        return "Bill = $" + bill +  "\n" + "Tip = $" + tip + "\nTotal = $" + total +
                "\nPer Person = $" + perPerson;


    }
    }


