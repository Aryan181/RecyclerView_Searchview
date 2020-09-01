package com.example.recyclerview;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ArrayList<ExampleItem> mExampleList;

    ArrayList<String> data = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;
    String address;
    private static final String TAG = "MyActivity";

    ArrayList<String> co_ordinates ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        co_ordinates = new ArrayList<>();





        co_ordinates.add("35.929673=-78.948237+2020-03-25");
        co_ordinates.add("38.889510=-77.032000+2019-01-27");
        co_ordinates.add("38.032120=-78.477510+2020-02-26");
        co_ordinates.add("36.379450=-75.830290+2020-02-26");

        /*co_ordinates.add("44.968046=-94.420307");
        co_ordinates.add("33.844843=-116.54911");
        co_ordinates.add("33.755783=-116.360066");
        co_ordinates.add("33.844847=-116.549069");
        co_ordinates.add("44.920474=-93.447851");*/
        Collections.sort(co_ordinates);



        createExampleList();
        buildRecyclerView();

        setButton();

        EditText editText = findViewById(R.id.edittext);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

    }



    private void filter ( String text)
    {
        ArrayList<ExampleItem> filteredList = new ArrayList<>();
        for (ExampleItem item : mExampleList)
        {
            if(item.getText1().toLowerCase().contains(text.toLowerCase())){
            filteredList.add(item);}
        }

        mAdapter.filterList(filteredList);

    }







    private String getCompleteAddressString(String locations) {
        Double LATITUDE = Double.parseDouble(locations.substring(0,locations.indexOf('=')));
        Double LONGITUDE = Double.parseDouble(locations.substring(locations.indexOf('=')+1));
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }



    public void removeItem(int position)
    {
        Log.e(TAG, "Item was clicked !");
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);



    }


    public void changeItem(int position, String text)
    {
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }









    public void createExampleList() {

        mExampleList = new ArrayList<>();
        for(int i =0;i<co_ordinates.size();i++) {
            mExampleList.add(new ExampleItem(R.drawable.ic_location, ""+getCompleteAddressString(co_ordinates.get(i).substring(0,co_ordinates.get(i).indexOf("+"))) , "Date visited  "+ co_ordinates.get(i).substring(co_ordinates.get(i).indexOf("+")+1)));
        }

        /*mExampleList.add(new ExampleItem(R.drawable.ic_android, "One", "Ten"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio, "Two", "Eleven"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun, "Three", "Twelve"));
        mExampleList.add(new ExampleItem(R.drawable.ic_android, "Four", "Thirteen"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio, "Five", "Fourteen"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun, "Six", "Fifteen"));
        mExampleList.add(new ExampleItem(R.drawable.ic_android, "Seven", "Sixteen"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio, "Eight", "Seventeen"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun, "Nine", "Eighteen"));*/


    }


    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter( mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);

            }
        });
    }

    public void setButton()
    {

    }


}