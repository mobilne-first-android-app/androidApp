package com.example.piotrek.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.message);
        String message = editText.getText().toString();
        intent.putExtra("Show", false);
        intent.putExtra("Message", message);
        startActivity(intent);
    }
    public void showMessages(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra("Show", true);
        startActivity(intent);
    }
}
