package com.example.splash;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewholder> implements Filterable {
    private Context context;
    private ArrayList<model> data;
    private ArrayList<model> backup;
    private String name,image;
    public adapter(Context context,ArrayList<model> data,String name,String image)
    {
        this.context=context;
        this.data=data;
        backup=new ArrayList<>(data);
        this.name=name;
        this.image=image;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
//        View view=inflater.inflate(R.layout.item,parent,false);
        View view=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
//        String title=data.get(position);
//        holder.texttitle.setText(title);
        final model temp=data.get(position);
        holder.texttitle.setText(data.get(position).getUsername());
//        holder.pass.setText(data.get(position).getPassword());


        holder.texttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(context,editpass.class);
                intent3.putExtra("texttitle",temp.getUsername());
                intent3.putExtra("password",temp.getPassword());
                intent3.putExtra("name",name);
                intent3.putExtra("image",image);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent3);
            }
        });


        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=(ClipData) ClipData.newPlainText("text",data.get(position).getPassword());
                clipboardManager.setPrimaryClip(clip);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<model> filterdata = new ArrayList<model>();
            if(keyword.toString().isEmpty())
                filterdata.addAll(backup);
            else {
                for(model obj:backup)
                {
                    if(obj.getUsername().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filterdata.add(obj);
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterdata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            data.clear();
            data.addAll((ArrayList<model>)results.values);
            notifyDataSetChanged();
        }
    };

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView imageicon,copy;
        TextView texttitle,pass;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            texttitle=itemView.findViewById(R.id.texttitle);

            copy=itemView.findViewById(R.id.cpy);



        }
    }


}
