package com.cskomra.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int pricePerCup = 5;
    int priceForWhip = 1;
    int priceForChocolate = 2;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view){
        if (quantity == 100){
            Context context = getApplicationContext();
            CharSequence text = "Maximum coffee order is 100.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "Minimum coffee order is 1.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //get checked state from Whipped Cream CheckBox view
        CheckBox addWhipCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = addWhipCheckBox.isChecked();

        CheckBox addChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = addChocolateCheckBox.isChecked();

        EditText nameView = (EditText) findViewById(R.id.name_text_input);
        String name = nameView.getText().toString();

        int price = calculatePrice();
        String message = createOrderSummary(price, name);

        // send order to Mail intent
        String addresses[] = {};
        String subject = "Just Java Order for " + name;
        composeEmail(addresses, subject, message);
        reinitialize();

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(Integer.toString(number));
    }

    /**
     * Calculates the price of the order.     *
     */
    private int calculatePrice() {
        int whip = hasWhippedCream ? priceForWhip : 0;
        int choc = hasChocolate ? priceForChocolate : 0;
        int basePrice = pricePerCup + whip + choc;

        return quantity * basePrice;
    }

    public void reinitialize(){
        // Name
        EditText nameView = (EditText) findViewById(R.id.name_text_input);
        nameView.setText("");

        // Whipped Cream
        CheckBox addWhipCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        addWhipCheckBox.setChecked(false);

        // Chocolate
        CheckBox addChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        addChocolateCheckBox.setChecked(false);

        //Quantity
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("0");
    }

    /**
     *  Send order in an email message
     */
    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Creates a message for the order
     */
    private String createOrderSummary(int price,
                                      String name){
        return  "Name: " + name + "\n" +
                "Add whipped cream? " + hasWhippedCream + "\n" +
                "Add chocolate? " + hasChocolate + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: $" + price + "\n" +
                "Thank you!";
    }
}
