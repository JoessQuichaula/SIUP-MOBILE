package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.DocumentItem;

import java.util.List;

public class DocumentStatsAdapter extends RecyclerView.Adapter<DocumentStatsAdapter.DocumentStatsViewHolder> {

    private Context context;
    private List<DocumentItem> documentItems;
    public DocumentStatsAdapter(List<DocumentItem> documentItems, Context context) {
        this.documentItems = documentItems;
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stade,parent,false);
        return new DocumentStatsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DocumentStatsViewHolder holder, int position) {
        DocumentItem documentItem = documentItems.get(position);
        holder.txtDocument.setText(documentItem.getTxtDocument());
        holder.txtFileName.setText(documentItem.getTxtFileName());
        holder.txtFileSize.setText(documentItem.getTxtFileSize());
        holder.txtFileExt.setText(documentItem.getTxtFileExt());

    }

    @Override
    public int getItemCount() {
        return documentItems.size();
    }

    public class DocumentStatsViewHolder extends RecyclerView.ViewHolder {
        TextView txtDocument;
        TextView txtFileName;
        TextView txtFileSize;
        TextView txtFileExt;

        public DocumentStatsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocument = itemView.findViewById(R.id.txtDocument);
            txtFileName = itemView.findViewById(R.id.txtFileName);
            txtFileSize = itemView.findViewById(R.id.txtFileSize);
            txtFileExt = itemView.findViewById(R.id.txtFileExt);
        }
    }
}
