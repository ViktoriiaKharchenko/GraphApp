package com.project.graphapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        Intent intent ;
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_info:
                intent = new Intent(HelpActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_help:
                intent = new Intent(HelpActivity.this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_main:
                intent = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}