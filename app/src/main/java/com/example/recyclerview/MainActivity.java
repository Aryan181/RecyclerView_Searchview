package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();

        setButton();

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




     public ArrayList LatLon()
     {

         data.add("47519-122036");
         data.add("41783-88168");
         data.add("47622-122162");
         return data;

     }

     public String addressGenerator(double latitude, double longitude) throws IOException {
         Geocoder geocoder;
         geocoder = new Geocoder(this, Locale.getDefault());

         List<Address> addresses; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
         addresses = geocoder.getFromLocation(longitude, latitude, 1);

         String address = addresses.get(0).getAddressLine(0);
         return address;
     }


    public void createExampleList()
    {

        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.ic_location, ""+Math.random()+""+Math.random(), ""+Math.random()));
        mExampleList.add(new ExampleItem(R.drawable.ic_location, "Line 1", "Line 2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_location, "Line 1", "Line 2"));


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