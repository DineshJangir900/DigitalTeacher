package com.example.digitalteacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalteacher.R;
import com.example.digitalteacher.databinding.ItemsBinding;
import com.example.digitalteacher.model.CppModelClass;
import java.util.ArrayList;
import java.util.List;

public class CppAdapter extends RecyclerView.Adapter<CppAdapter.ViewHolder>{
    private Activity activity;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> urls = new ArrayList<>();
    private List<CppModelClass> arrayList;


    public CppAdapter(Activity activity,RecyclerView recyclerView, Context context, List<CppModelClass> arrayList) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.context = context;
        this.arrayList =arrayList;
    }

    @NonNull
    @Override
    public CppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemsBinding binding = ItemsBinding.inflate(inflater,parent,false);
        CppAdapter.ViewHolder rootView = new CppAdapter.ViewHolder(binding.getRoot());
        rootView.setItemsBinding(binding);
        return rootView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       CppModelClass cppModelClass = arrayList.get(position);
       if(cppModelClass.fileType.equals("PDF")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.pdf));
       }else if(cppModelClass.fileType.equals("IMAGE")){
           holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.add));
       }else if(cppModelClass.fileType.equals("VIDEO")){
           holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.camera));
       }else if(cppModelClass.fileType.equals("TEXT")){
           holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
       }else if(cppModelClass.fileType.equals("TXT")){
           holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
       }else if(cppModelClass.fileType.equals("FILE")){
           holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
       }else{
           holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.iapp));
       }

        holder.itemsBinding.topicTV.setText("Topic: "+cppModelClass.getTopicName());
        holder.itemsBinding.fileTypeTV.setText("File Type: "+cppModelClass.getFileType());
        holder.itemsBinding.uploadUserEmailTV.setText("Uploader I'd: "+cppModelClass.getUserEmail());
        holder.itemsBinding.dateTV.setText("Date: "+cppModelClass.getDate());
        urls.add(cppModelClass.fileUrl);

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
