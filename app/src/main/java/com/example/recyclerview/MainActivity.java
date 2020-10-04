package com.example.recyclerview;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.recyclerview.R.drawable.ic_location;

public class MainActivity extends AppCompatActivity {
    private Button dataEditNoButton;
    private Button dataEditListViewButton;
    private Button dataEditMapViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataEditNoButton = (Button) findViewById(R.id.noEditButton);
        dataEditListViewButton = (Button) findViewById(R.id.yesEditListButton);
        dataEditMapViewButton = (Button) findViewById(R.id.yesEditMapButton);

        dataEditListViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListViewActivity();
            }
        });

       dataEditMapViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapViewActivity();
                //openLocationComponentActivity();
            }
        });

    }

    public void openListViewActivity() {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    public void openMapViewActivity() {
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }

    





}