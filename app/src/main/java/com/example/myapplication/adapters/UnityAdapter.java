package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.home_ui.services.ServiceScreen;
import com.example.myapplication.models.UnityItem;

import java.util.List;

public class UnityAdapter extends RecyclerView.Adapter<UnityAdapter.UnityViewHolder> {

    private List<UnityItem> unityItems;
    private Context context;
    private FragmentManager fragmentManager;
    private String baseUrl;
    public UnityAdapter(List<UnityItem> unityItems, Context context, FragmentManager fragmentManager,String baseUrl)
    {
        this.unityItems = unityItems;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public UnityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_unity,parent,false);
        return new UnityViewHolder(view);
    }

    String imgUnity;
    @Override
    public void onBindViewHolder(@NonNull final UnityViewHolder holder, final int position) {

        final UnityItem unityItem = unityItems.get(position);
        imgUnity = unityItem.getImgUnity().replace("\\","/");
        Glide
                .with(context)
                .load(baseUrl+"/storage/"+ imgUnity)
                .into(holder.imgUnity);

        holder.txtUnity.setText(unityItem.getTxtUnity());
        holder.btnUnity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.services_container,new ServiceScreen(unityItem.getIdUnity()),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return unityItems.size();
    }


    static class UnityViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUnity;
        TextView txtUnity;
        Button btnUnity;

        public UnityViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUnity = itemView.findViewById(R.id.imgUnity);
            txtUnity = itemView.findViewById(R.id.txtUnity);
            btnUnity = itemView.findViewById(R.id.btnService);
        }
    }

}
