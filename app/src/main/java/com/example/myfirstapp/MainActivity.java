package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    protected int numMediciones;
    protected int tmpAcum;
    protected int tmpMin;
    protected int tmpMax;
    protected int tmpMean;
    protected int LAUNCH_SECOND_ACTIVITY = 1;
    protected List<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmpMax = -99999;
        tmpMin = 99999;
        datos = new ArrayList<String>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            //process your onClick here
            Intent intent = new Intent(this, DisplayAllData.class);
            List<String> datos2 = new ArrayList<String>(datos);
            intent.putStringArrayListExtra("datos", (ArrayList<String>) datos2);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called to update the apps values **/
    public void update(int aux){
        datos.add(Integer.toString(aux));
        numMediciones++;
        // Capture the layout's TextView and set the string as its text
        TextView textViewNumMed = findViewById(R.id.textViewNumMed);
        textViewNumMed.setText(Integer.toString(numMediciones));
        if (aux > tmpMax) {
            tmpMax = aux;

            // Capture the layout's TextView and set the string as its text
            TextView textViewTmpMax = findViewById(R.id.textViewTmpMax);
            textViewTmpMax.setText(Integer.toString(tmpMax));
        } else if (aux < tmpMin) {
            tmpMin = aux;
            // Capture the layout's TextView and set the string as its text
            TextView textViewTmpMin = findViewById(R.id.textViewTmpMin);
            textViewTmpMin.setText(Integer.toString(tmpMin));
        }
        tmpAcum += aux;
        tmpMean = tmpAcum / numMediciones;
        // Capture the layout's TextView and set the string as its text
        TextView textViewTmpMean = findViewById(R.id.textViewTmpMean);
        textViewTmpMean.setText(Integer.toString(tmpMean));
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {

        // Get string written
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();

        int aux;
        if (message != null) {
            aux = Integer.parseInt(message);

            // Check if the value is in range
            if(aux < 5 || aux > 35) {
                Intent intent = new Intent(this, DisplayMessageActivity.class);

                intent.putExtra(EXTRA_MESSAGE, message);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            } else {
                update(aux);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                int aux = Integer.parseInt(result);
                update(aux);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}