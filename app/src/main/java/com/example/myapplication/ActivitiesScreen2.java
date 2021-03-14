package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.models.RequestItem;

import java.util.List;


public class ActivitiesScreen2 extends Fragment {



    RequestItem requestItem;
    public ActivitiesScreen2(RequestItem requestItem) {
        this.requestItem = requestItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities_screen2, container, false);
    }

    TextView txtActivityId;
    TextView txtActivityUnity;
    TextView txtActivityService;
    TextView txtActivityDate;
    TextView txtActivityDivisionId;
    TextView txtActivityDivisionAddress;
    TextView txtActivityDivisionType;
    TextView txtActivityStatus;
    TextView txtActivityReason;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtActivityId = view.findViewById(R.id.txtActivityId);
        txtActivityUnity = view.findViewById(R.id.txtActivityUnity);
        txtActivityService =view.findViewById(R.id.txtActivityService);
        txtActivityDate = view.findViewById(R.id.txtActivityDate);
        txtActivityDivisionId = view.findViewById(R.id.txtActivityDivisionId);
        txtActivityDivisionAddress = view.findViewById(R.id.txtActivityDivisionAddress);
        txtActivityDivisionType = view.findViewById(R.id.txtActivityDivisionType);
        txtActivityStatus = view.findViewById(R.id.txtActivityStatus);
        txtActivityReason = view.findViewById(R.id.txtActivityReason);
        fillTexts();
    }

    private void fillTexts(){
        String requestId = Integer.toString(requestItem.getActivityId());
        String requestDivisionId = Integer.toString(requestItem.getActivityDivision());
        String requestAddress = requestItem.getTxtActivityDivisionCounty()+" "+requestItem.getTxtActivityDivisionAddress();
        String requestDate = requestItem.getActivityDate();
        int index = requestDate.indexOf("T");
        int last = requestDate.lastIndexOf(".");
        String text = requestDate.substring(0,index)+" "+requestDate.substring(++index,last);

        txtActivityId.setText(requestId);
        txtActivityService.setText(requestItem.getTxtActivityService());
        txtActivityDate.setText(text);
        txtActivityDivisionId.setText(requestDivisionId);
        txtActivityDivisionAddress.setText(requestAddress);
        txtActivityDivisionType.setText(requestItem.getTxtActivityDivisionType());
        txtActivityStatus.setText(requestItem.getTxtActivityStatus());
        if (requestItem.getTxtReason()!=null)
            txtActivityReason.setText(Html.fromHtml(requestItem.getTxtReason()));
    }
}