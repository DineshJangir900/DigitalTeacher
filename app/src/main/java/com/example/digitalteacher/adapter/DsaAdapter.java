package com.example.digitalteacher.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.digitalteacher.R;
import com.example.digitalteacher.databinding.ItemsBinding;
import com.example.digitalteacher.model.DsaModelClass;

import java.util.ArrayList;
import java.util.List;

public class DsaAdapter extends RecyclerView.Adapter<DsaAdapter.ViewHolder>{
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> urls = new ArrayList<>();
    private List<DsaModelClass> arrayList;
    private Activity activity;

    public DsaAdapter(Activity activity,RecyclerView recyclerView, Context context, List<DsaModelClass> arrayList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.arrayList =arrayList;
        this.activity =activity;
    }

    @NonNull
    @Override
    public DsaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemsBinding binding = ItemsBinding.inflate(inflater,parent,false);
        DsaAdapter.ViewHolder rootView = new DsaAdapter.ViewHolder(binding.getRoot());
        rootView.setItemsBinding(binding);
        return rootView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DsaModelClass dsaModelClass = arrayList.get(position);
        if(dsaModelClass.fileType.equals("PDF")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.pdf));
        }else if(dsaModelClass.fileType.equals("IMAGE")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.add));
        }else if(dsaModelClass.fileType.equals("VIDEO")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.camera));
        }else if(dsaModelClass.fileType.equals("TEXT")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
        }else if(dsaModelClass.fileType.equals("TXT")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
        }else if(dsaModelClass.fileType.equals("FILE")){
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.note));
        }else{
            holder.itemsBinding.pdfIcon.setImageDrawable(activity.getDrawable(R.drawable.iapp));
        }

        holder.itemsBinding.topicTV.setText("Topic: "+dsaModelClass.getTopicName());
        holder.itemsBinding.fileTypeTV.setText("File Type: "+dsaModelClass.getFileType());
        holder.itemsBinding.uploadUserEmailTV.setText("Uploader I'd: "+dsaModelClass.getUserEmail());
        holder.itemsBinding.dateTV.setText("Date: "+dsaModelClass.getDate());
        urls.add(dsaModelClass.fileUrl);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemsBinding  itemsBinding;
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
