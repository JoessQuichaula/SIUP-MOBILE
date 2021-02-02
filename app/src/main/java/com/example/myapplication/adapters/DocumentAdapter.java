package com.example.myapplication.adapters;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.myapplication.R;
import com.example.myapplication.models.DocumentItem;


import java.util.List;
import java.util.regex.Pattern;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>{

    private Context context;
    private List<DocumentItem> documentItems;
    private Activity activity;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    public DocumentAdapter(Context context, List<DocumentItem> documentItems, Activity activity, Fragment fragment) {
        this.context = context;
        this.documentItems = documentItems;
        this.activity = activity;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public DocumentAdapter.DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_documents,parent,false);
        DocumentViewHolder documentViewHolder = new DocumentViewHolder(view);
        return documentViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, final int position) {
        final DocumentItem documentItem = documentItems.get(position);
        if (documentItem.getIdDocument()==5){
            String val = "*obs: a data do comprovativo tem que ser a mesma da data do envio dos outros documentos";
            AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
            awesomeValidation.setContext(context);
            awesomeValidation.addValidation(holder.editDocumentPath,"info",val);
            awesomeValidation.validate();
        }
        holder.txtDocument.setText(documentItem.getTxtDocument());
        holder.btnDocumentSearch.setOnClickListener(v -> openFileManager(position));
    }
    @Override
    public int getItemCount() {
        if (documentItems != null)
        return documentItems.size();
        else{
            return 0;
        }
    }
    private void openFileManager(int itemPosition){

        saveItemPosition(itemPosition);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        fragment.startActivityForResult(intent,102);
        //activity.startActivityForResult(intent,102);
    }
    private void saveItemPosition(int itemPosition){
        SharedPreferences uriSaver = activity.getSharedPreferences("uriSaver",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = uriSaver.edit();
        editor.putInt("itemPosition",itemPosition);
        editor.commit();
    }
    public static class DocumentViewHolder extends RecyclerView.ViewHolder {

        TextView txtDocument;
        EditText editDocumentPath;
        Button btnDocumentSearch;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocument = itemView.findViewById(R.id.txtDocument);
            editDocumentPath = itemView.findViewById(R.id.editDocumentPath);
            btnDocumentSearch = itemView.findViewById(R.id.btnDocumentSearch);

        }
    }


}
