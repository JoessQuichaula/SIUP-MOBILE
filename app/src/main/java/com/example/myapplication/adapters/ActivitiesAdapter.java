package com.example.myapplication.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ActivitiesScreen2;
import com.example.myapplication.R;
import com.example.myapplication.models.ActivityItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.RequestItem;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder> {


    private Context context;
    private List<RequestItem> requestItems;
    private FragmentManager fragmentManager;
    private Resources resources;

    public ActivitiesAdapter(Context context, List<RequestItem> requestItems, FragmentManager fragmentManager, Resources resources) {
        this.context = context;
        this.requestItems = requestItems;
        this.fragmentManager = fragmentManager;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activities,parent,false);
        return new ActivitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position) {
        RequestItem requestItem = requestItems.get(position);
        String activityItems1 = requestItem.getActivityDate();
        int index = activityItems1.indexOf("T");
        String text = activityItems1.substring(0,index);

        String txtActivityService = requestItem.getTxtActivityService();
        String txtActivityDivision = requestItem.getTxtActivityDivision();
        String txtActivityStatus = requestItem.getTxtActivityStatus();

        holder.txtActivityDate.setText(text);
        holder.txtActivityService.setText(txtActivityService);
        holder.txtActivityDivision.setText(txtActivityDivision);
        holder.txtActivityStatus.setText(txtActivityStatus);

        if (requestItem.getActivityStatus()==1){
            holder.txtActivityStatus.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(android.R.color.holo_green_dark)));
        }
        else if (requestItem.getActivityStatus()==3){
            holder.txtActivityStatus.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(android.R.color.holo_red_dark)));
        }

        holder.itemView.setOnClickListener(v -> {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.activitiesContainer,new ActivitiesScreen2(requestItem))
                    .commit();
        });

    }

    @Override
    public int getItemCount() {
        return requestItems.size();
    }

    class ActivitiesViewHolder extends RecyclerView.ViewHolder {

        TextView txtActivityDate;
        TextView txtActivityService;
        TextView txtActivityStatus;
        TextView txtActivityDivision;
        public ActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtActivityDate = itemView.findViewById(R.id.txtActivityDate);
            txtActivityService = itemView.findViewById(R.id.txtActivityService);
            txtActivityStatus = itemView.findViewById(R.id.txtActivityStatus);
            txtActivityDivision = itemView.findViewById(R.id.txtActivityDivision);
        }
    }

}
