package com.example.digitalteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.digitalteacher.databinding.NotepadBinding;
import com.example.digitalteacher.model.NotesModelClass;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private RecyclerView recyclerView;
    private Context context;
    private List<NotesModelClass> arrayList;

    public NotesAdapter(RecyclerView recyclerView, Context context, List<NotesModelClass> arrayList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.arrayList =arrayList;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        NotepadBinding binding = NotepadBinding.inflate(inflater,parent,false);
        NotesAdapter.ViewHolder rootView = new NotesAdapter.ViewHolder(binding.getRoot());
        rootView.setItemsBinding(binding);
        return rootView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotesModelClass notesModelClass = arrayList.get(position);
        holder.itemsBinding.notesTitleTV.setText(notesModelClass.getNoteHanding());
        holder.itemsBinding.noteTV.setText(notesModelClass.getNote());
        holder.itemsBinding.dateTV.setText("Date: "+notesModelClass.getDate());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private NotepadBinding itemsBinding;
        public ViewHolder(View itemView){
            super(itemView);
        }

        public void setItemsBinding(NotepadBinding itemsBinding) {
            this.itemsBinding = itemsBinding;
        }
    }
}
