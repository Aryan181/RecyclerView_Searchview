package com.example.recyclerview;
import android.content.DialogInterface;
import android.net.sip.SipSession;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    public List<ExampleItem> filteredList;
    private List<ExampleItem> mExampleList;
    private OnItemClickListener mListener;
    private ArrayList<ExampleItem> mExampleListFull;
    private ExampleAdapter Adapter;
    private static final String TAG = "MyActivity";


    public void filterList(ArrayList<ExampleItem> filteredList) {




        mExampleList = filteredList;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener
    {
        void onItemClicked(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }



    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClicked(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onDeleteClick(position);
                            Log.d(TAG,"Delete Button Clicked");

                        }
                    }
                }
            });



        }




    }

    ExampleAdapter(List<ExampleItem> mExampleList)
    {
        this.mExampleList = mExampleList;
        mExampleListFull = new ArrayList<>(mExampleList);
    }











    @Override
    public ExampleViewHolder onCreateViewHolder(  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener );
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);


        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }



    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mExampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ExampleItem item : mExampleListFull) {
                    if (item.getText1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mExampleList.clear();
            mExampleList.addAll((List)results.values);

            notifyDataSetChanged();









        }
    };
}