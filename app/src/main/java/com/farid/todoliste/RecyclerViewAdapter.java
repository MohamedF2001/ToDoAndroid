package com.farid.todoliste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Data> data= null;
    Data current;
    int currentPos=0;

    // create constructor to initialize context and data sent from MainActivity
    public RecyclerViewAdapter(Context context, List<Data> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.todoitem, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Data current=data.get(position);
        myHolder.todo.setText(current.todo);
        myHolder.todoDate.setText(current.todoDate);
        //myHolder.prix.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView todo;
        TextView todoDate;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            todo= (TextView) itemView.findViewById(R.id.todo);
            todoDate = (TextView) itemView.findViewById(R.id.tododate);

            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

        }

    }

}