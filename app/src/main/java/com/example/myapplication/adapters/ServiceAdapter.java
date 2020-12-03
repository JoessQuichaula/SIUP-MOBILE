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
    public static String txtSubService = "txtSubService";
    public static String txtDesc = "txtDesc";
    private FragmentManager fragmentManager;
    private Activity mView;
    private String baseUrl;
    int idUnity;
    int idService;

    public ServiceAdapter(List<ServiceItem> serviceItems, Context context, FragmentManager fragmentManager, Activity mView, String baseUrl,int idUnity) {
        this.serviceItems = serviceItems;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.mView = mView;
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
        Glide
                .with(context)
                .load(baseUrl+"/storage/"+ imgService)
                .into(holder.imgService);

        holder.txtService.setText(txtService);
        holder.txtDescService.setText(txtDescService);
        holder.btnEnter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                idService = serviceItem.getIdService();
                String v1 = holder.txtService.getText().toString();
                String v2 = holder.txtDescService.getText().toString();
                startNewService(v1,v2);
            }
        });

    }

    private void startNewService(String v1, String v2){
        Bundle bundle = new Bundle();
        bundle.putString(txtSubService,v1);
        bundle.putString(txtDesc,v2);
        //bundle.putString("imgService",);
        TabLayout homeTab = mView.findViewById(R.id.home_navbar);;
        ImageView imgLogo = mView.findViewById(R.id.app_logo);
        imgLogo.setVisibility(View.GONE);
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
