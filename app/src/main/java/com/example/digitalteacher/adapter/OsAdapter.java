package com.example.digitalteacher.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.system.Os;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalteacher.R;
import com.example.digitalteacher.databinding.ItemsBinding;
import com.example.digitalteacher.model.CppModelClass;
import com.example.digitalteacher.model.OsModelClass;

import java.util.ArrayList;
import java.util.List;

public class OsAdapter extends RecyclerView.Adapter<OsAdapter.ViewHolder>{
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> urls = new ArrayList<>();
    private List<OsModelClass> arrayList;
    private Activity activity;

    public OsAdapter(Activity activity,RecyclerView recyclerView, Context context, List<OsModelClass> arrayList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemsBinding binding = ItemsBinding.inflate(inflater,parent,false);
        OsAdapter.ViewHolder rootView = new OsAdapter.ViewHolder(binding.getRoot());
        rootView.setItemsBinding(binding);
        return rootView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OsModelClass osModelClass = arrayList.get(position);
        if(osModelClass.fileType.equals("PDF")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.pdf));
        }else if(osModelClass.fileType.equals("IMAGE")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.add));
        }else if(osModelClass.fileType.equals("VIDEO")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.camera));
        }else if(osModelClass.fileType.equals("TEXT")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
        }else if(osModelClass.fileType.equals("TXT")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
        }else if(osModelClass.fileType.equals("FILE")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
        }else{
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.iapp));
        }
        holder.itemsBinding.topicTV.setText("Topic: "+osModelClass.getTopicName());
        holder.itemsBinding.fileTypeTV.setText("File Type: "+osModelClass.getFileType());
        holder.itemsBinding.uploadUserEmailTV.setText("Uploader I'd: "+osModelClass.getUserEmail());
        holder.itemsBinding.dateTV.setText("Date: "+osModelClass.getDate());
        urls.add(osModelClass.fileUrl);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemsBinding itemsBinding;
        public ViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildLayoutPosition(view);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urls.get(position)));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        public void setItemsBinding(ItemsBinding itemsBinding) {
            this.itemsBinding = itemsBinding;
        }

    }
}
