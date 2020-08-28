package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        co_ordinates.add("35.929673=-78.948237");
        co_ordinates.add("38.889510=-77.032000");
        co_ordinates.add("38.032120=-78.477510");
        co_ordinates.add("36.379450=-75.830290");
        co_ordinates.add("44.968046=-94.420307");
        co_ordinates.add("33.844843=-116.54911");
        co_ordinates.add("33.755783=-116.360066");
        co_ordinates.add("33.844847=-116.549069");
        co_ordinates.add("44.920474=-93.447851");




        createExampleList();
        buildRecyclerView();

        setButton();



    }

    public String getAddress(String locations) {
        Double latitude = Double.parseDouble(locations.substring(0,locations.indexOf('=')));
        Double longitude = Double.parseDouble(locations.substring(locations.indexOf('=')+1));

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        return address;
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


    public void insertItem(int position)
    {
        mExampleList.add(position,new ExampleItem(R.drawable.ic_audio,"New data inserted", "Extra edit"));
        mAdapter.notifyItemChanged(position);
    }

     public void removeItem(int position)
     {

         mExampleList.remove(position);
         mAdapter.notifyItemChanged(position);



     }


     public void changeItem(int position, String text)
     {
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
     }









    public void createExampleList() {

        mExampleList = new ArrayList<>();
        for(int i =0;i<co_ordinates.size();i++) {
            mExampleList.add(new ExampleItem(R.drawable.ic_location, ""+getCompleteAddressString(co_ordinates.get(i)) , "Co-ordinates = "+ co_ordinates.get(i).replace("="," ")));
        }


       /* ArrayList<String> data = LatLon();
        for(int i = 0;i<data.size();i++)
        {
            Double Lat = Double.parseDouble(data.get(i).substring(0,data.get(i).indexOf('-')));
            Double Lon = Double.parseDouble(data.get(i).substring(data.get(i).indexOf('-')+1));
            try {
                  address = addressGenerator(Lat, Lon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mExampleList.add(new ExampleItem(R.drawable.ic_location, ""+address, Lat+" "+Lon));
        }*/

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
               changeItem(position, "Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

        public void setButton()
        {
            /*buttonInsert = findViewById(R.id.button_insert);
            buttonRemove = findViewById(R.id.button_remove);
            editTextInsert = findViewById(R.id.edittext_insert);
            editTextRemove = findViewById(R.id.edittext_remove);


            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.parseInt(editTextInsert.getText().toString());
                    insertItem(position);
                }
            });



            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // int position = Integer.parseInt(editTextRemove.getText().toString());
                    //removeItem(position);

                    int position;
                    try {
                        position = Integer.parseInt(editTextRemove.getText().toString());
                        removeItem(position);
                    } catch (NumberFormatException e) {

                    }




                }
            });
        }*/
}}