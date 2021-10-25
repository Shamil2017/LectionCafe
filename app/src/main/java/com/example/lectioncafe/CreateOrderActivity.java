package com.example.lectioncafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAddition;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;
    private StringBuilder builderAddition;

    private String name;
    private  String drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if (intent.hasExtra("name"))
            name = intent.getStringExtra("name");
        else
            name = getString(R.string.defaultName);

        drink = getString(R.string.tea);

        textViewHello = findViewById(R.id.textViewHello);

        String hello = String.format("Привет, %s! Что будете заказывать?", name);
        textViewHello.setText(hello);


        textViewAddition = findViewById(R.id.textViewAdditions);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        builderAddition = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonTea)
        {
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
        }
        else
        if (id == R.id.radioButtonCoffee) {
            drink = getString(R.string.coffee);
            spinnerCoffee.setVisibility(View.VISIBLE);
            spinnerTea.setVisibility(View.INVISIBLE);
        }

        String additions = String.format("Что добавить в ваш %s",drink);
        textViewAddition.setText(additions);
    }

    public void onClickSendOrder(View view) {
        builderAddition.setLength(0);
        if (checkBoxLemon.isChecked())
        {
            builderAddition.append(getString(R.string.lemon)).append(" ");
        }
        if (checkBoxMilk.isChecked())
        {
            builderAddition.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked() && drink.equals(getString(R.string.tea)))
        {
            builderAddition.append(getString(R.string.sugar)).append(" ");
        }
        String optionOfDrink = "";
        if (drink.equals(getString(R.string.tea))) {
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        } else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }

        String order = String.format("Имя: %s Напиток: %s Вид напитка: %s", name,  drink, optionOfDrink);
        String additions;
        if (builderAddition.length() > 0) {
            additions = "\n"+getString(R.string.need_additions) + builderAddition.toString();
        } else {
            additions = "";
        }
        String fullOrder = order + additions;

        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}