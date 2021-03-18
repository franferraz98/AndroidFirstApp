package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        result = message;

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView3);
        message = "¡Aviso! Valor fuera del rango detectado: " +
                message + "ºC. ¿Desea añadirlo o ignorarlo?";
        textView.setText(message);
    }

    public void anadir(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void ignorar(View view){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}