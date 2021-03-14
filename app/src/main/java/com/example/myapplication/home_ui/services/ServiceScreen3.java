package com.example.myapplication.home_ui.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceScreen3 extends Fragment {

    private static final int REQUEST_CODE = 101;


    //Google Maps Attributes
    private Boolean mPermissionGranted;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLocation;


    //Dialog Attributes
    private ImageView imgDialog;
    private TextView[] txtDialogViews = new TextView[4];
    private Button btnDialogAdvance;


    //Network Attributes
    RetrofitConfig retrofitConfig;
    Call<DivisionTypeItem> callType;
    Call<CountyItem> callCounty;
    String fora;
    String imgDivision;
    int idUnity;
    int idService;
    int idDivision;

    //Marker Map Attributes
    int idCounty;
    String mapKey;
    HashMap<String,Integer> hashMap = new HashMap<>();


    public ServiceScreen3(int idUnity, int idService) {
        this.idUnity = idUnity;
        this.idService = idService;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            retrofitFetch();
            if (mPermissionGranted){
                DeviceLocation();
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_screen3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            RequestPermission();
            mapFragment.getMapAsync(callback);
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }




    //Fetch the Data from Server To Google Maps
    private void retrofitFetch(){
        retrofitConfig = ((MainScreen)getActivity()).retrofitConfig;
        Call<List<DivisionItem>> call = retrofitConfig.callDivisions(idUnity);
        call.enqueue(new Callback<List<DivisionItem>>(){
            @Override
            public void onResponse(Call<List<DivisionItem>> call, Response<List<DivisionItem>> response) {
                if (response.isSuccessful()){
                    onResponseSuccess(response.body(),retrofitConfig.baseUrl);
                }
                else
                    retrofitConfig.failureThread(getFragmentManager(),R.id.services_container);
            }
            @Override
            public void onFailure(Call<List<DivisionItem>> call, Throwable t) {
                retrofitConfig.failureThread(getFragmentManager(),R.id.services_container);
            }
        });
    }


    private void onResponseSuccess(final List<DivisionItem> divisionItems, final String baseUrl){
        if (divisionItems == null)
            Toast.makeText(getContext(),"Response is Successful but ResponseBody is null" , Toast.LENGTH_SHORT).show();
        else{
           //BitmapDescriptorFactory.fromResource()
            int index = 0;
            for (final DivisionItem divisionItem:divisionItems) {
                LatLng divisionPosition = new LatLng(divisionItem.getLat(),divisionItem.getLng());
                mapKey = mMap.addMarker(new MarkerOptions().position(divisionPosition).title("Repartição")).getId();
                hashMap.put(mapKey, index);
                ++index;
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {

                    final DivisionItem divisionItem = divisionItems.get(hashMap.get(marker.getId()));
                    idDivision = divisionItem.getId();
                    idCounty = divisionItem.getIdCounty();
                    callType = retrofitConfig.callDivisionType(divisionItem.getIdType());
                    callCounty = retrofitConfig.callCounty(idCounty);

                    callType.enqueue(new Callback<DivisionTypeItem>() {
                        @Override
                        public void onResponse(Call<DivisionTypeItem> call, Response<DivisionTypeItem> response) {
                            if (response.isSuccessful()){
                                final DivisionTypeItem divisionTypeItem = response.body();
                                final String divisionNameTypeLocation = divisionTypeItem.getName();
                                callCounty.enqueue(new Callback<CountyItem>() {
                                    @Override
                                    public void onResponse(@NonNull Call<CountyItem> call,@NonNull Response<CountyItem> response) {
                                        if (response.isSuccessful()){
                                            onResponseSuccessForMarker(response.body(),divisionNameTypeLocation,divisionItem,baseUrl);
                                        }
                                        else
                                            Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<CountyItem> call, Throwable t) {
                                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                            else
                                Toast.makeText(getContext(), "Response is not Sucessful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<DivisionTypeItem> call, Throwable t) {

                        }
                    });


                    return false;
                }
            });
            LatLng sydney = new LatLng(-34, 151);
            //mMap.addMarker(new MarkerOptions().position(BELAS_LARDOSPATRIOTAS_CAMIAO).title("Repartição1"));
            //mMap.addMarker(new MarkerOptions().position(BELAS_ESTRADADIREITADOCAMAMA_FIXA).title("Repartição2"));
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

            //unityAdapter = new UnityAdapter(unityItems,getContext(),getFragmentManager(),baseUrl);
            //recycler_service.setAdapter(unityAdapter);
        }
    }

    private void onResponseSuccessForMarker(CountyItem countyItem,String divisionNameTypeLocation,DivisionItem divisionItem,String baseUrl){
        assert countyItem != null;
        fora = countyItem.getTxtCounty();
        final BottomSheetDialog bottomSheetDialog =  new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.layout_bottomsheet);
        txtDialogViews[0] = bottomSheetDialog.findViewById(R.id.txtDialogTitle);
        txtDialogViews[1] = bottomSheetDialog.findViewById(R.id.txtDialogAddress);
        txtDialogViews[2] = bottomSheetDialog.findViewById(R.id.txtDialogSchedule);
        txtDialogViews[3] = bottomSheetDialog.findViewById(R.id.txtDivisionStatus);

        txtDialogViews[0].setText(fora+" - "+divisionNameTypeLocation);
        txtDialogViews[1].append(divisionItem.getAddress());
        txtDialogViews[2].append(divisionItem.getOpenTime()+"-"+divisionItem.getCloseTime());
        imgDialog = bottomSheetDialog.findViewById(R.id.imgDialog);

        imgDivision = divisionItem.getImgDivision().replace("\\","/");

        Glide.with(getContext())
                .load(baseUrl+"/storage/"+imgDivision)
                .into(imgDialog);

        btnDialogAdvance = bottomSheetDialog.findViewById(R.id.btnDialogAdvance);
        if(divisionItem.getIdStatus()==2){
            String txtStatus = "(Fechado)*";
            btnDialogAdvance.setEnabled(false);
            btnDialogAdvance.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            txtDialogViews[3].setText(txtStatus);
            txtDialogViews[3].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        else {
            String txtStatus = "(Aberto)*";
            btnDialogAdvance.setEnabled(true);
            btnDialogAdvance.setBackgroundColor(getResources().getColor(R.color.colorApp));
            txtDialogViews[3].setText(txtStatus);
            txtDialogViews[3].setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        btnDialogAdvance.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.services_container,new ServiceScreen4(idDivision,idService),
                            "serviceScreen4")
                    .addToBackStack(null)
                    .commit();
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }




    //Google Maps Device Location Steps


    //This void will Request the permission to use Device Location
    private void RequestPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            mPermissionGranted = true;
        }

    }


    //This Setting will set the Device Location
    private void DeviceLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            Task<Location> task = mFusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                   if (location != null){
                       mLocation = location;
                       LatLng mLatLng = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,11));
                   }
                }
            });
        }

    }


    //Verify if the user has permitted the device location access
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mPermissionGranted = true;
                }
        }
    }
}