package com.example.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.home_ui.services.ServiceScreen2;
import com.example.myapplication.models.ServiceItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<ServiceItem> serviceItems;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activityInstance;
    private String baseUrl;
    int idUnity;
    int idService;

    public ServiceAdapter(List<ServiceItem> serviceItems, Context context, FragmentManager fragmentManager, Activity activityInstance, String baseUrl, int idUnity) {
        this.serviceItems = serviceItems;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activityInstance = activityInstance;
        this.baseUrl = baseUrl;
        this.idUnity = idUnity;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_service,parent,false);
        return new ServiceViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder holder, int position) {

        final ServiceItem serviceItem = serviceItems.get(position);

        CharSequence txtService = Html.fromHtml(serviceItem.getTxtService());
        CharSequence txtDescService = Html.fromHtml(serviceItem.getTxtDescService());
        String imgService = serviceItem.getImgService().replace("\\","/");
        String FullImgPath = baseUrl+"/storage/"+imgService;

        Glide
                .with(context)
                .load(FullImgPath)
                .into(holder.imgService);

        holder.txtService.setText(txtService);
        if (txtDescService.length()>100){
            holder.txtDescService.setText(txtDescService.subSequence(0,100));
            holder.txtDescService.append("...");
        }
        else{
            holder.txtDescService.setText(txtDescService);
        }



        holder.btnEnter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                idService = serviceItem.getIdService();
                CharSequence v1 = txtService;
                CharSequence v2 = txtDescService;
                startNewService(v1,v2,FullImgPath);
            }
        });

    }

    private void startNewService(CharSequence v1, CharSequence v2,String v3){
        Bundle bundle = new Bundle();
        bundle.putCharSequence("txtService",v1);
        bundle.putCharSequence("txtDesc",v2);
        bundle.putString("FullImgPath",v3);
        TabLayout homeTab = activityInstance.findViewById(R.id.home_navbar);;
        ImageView imgLogo = activityInstance.findViewById(R.id.app_logo);
        //imgLogo.setVisibility(View.GONE);
        homeTab.setVisibility(View.GONE);

        ServiceScreen2 serviceScreen2 = new ServiceScreen2(idUnity,idService);
        serviceScreen2.setArguments(bundle);

        fragmentManager
                .beginTransaction()
                .replace(R.id.services_container, serviceScreen2,null)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public int getItemCount() {
        return serviceItems.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {

        ImageView imgService;
        TextView txtService;
        TextView txtDescService;
        Button btnEnter;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);


            imgService = itemView.findViewById(R.id.imgService);
            txtService = itemView.findViewById(R.id.txtService);
            txtDescService = itemView.findViewById(R.id.txtDescService);
            btnEnter = itemView.findViewById(R.id.btnEnter);

        }
    }

}
