package com.cskomra.justjava;

import android.content.Context;
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
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
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
        displayMessage(message);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(Integer.toString(number));
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        int whip = hasWhippedCream ? priceForWhip : 0;
        int choc = hasChocolate ? priceForChocolate : 0;
        int basePrice = pricePerCup + whip + choc;

        return quantity * basePrice;
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
