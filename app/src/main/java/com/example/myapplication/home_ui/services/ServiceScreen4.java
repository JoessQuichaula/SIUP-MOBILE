package com.example.myapplication.home_ui.services;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.myapplication.FileUtils;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.adapters.DocumentAdapter;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.DocumentStatsItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceScreen4 extends Fragment {


    private int idService;
    private int idDivision;
    private int documentItemPosition;

    EditText editDocumentPath;
    ArrayList<DocumentStatsItem> documentStatsItem;
    List<DocumentItem> documentItemsTeste;
    SharedPreferences uriSaver;


    public ServiceScreen4(int idDivision,int idService){
        this.idDivision = idDivision;
        this.idService = idService;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_screen4, container, false);
    }

    private RecyclerView recyclerDocuments;
    private DocumentAdapter documentAdapter;
    private FloatingActionButton btnNext;
    private HashMap<Integer,Uri> hashMap;
    private View mainScreenView;
    public boolean isCurrentScreenOnServiceScreen4 = false;

    public void exitToSelectedFragmentOrBackStack(int bottomNavItemId){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnAlertCancel = dialog.findViewById(R.id.btnAlertCancel);
        Button btnAlertConfirm = dialog.findViewById(R.id.btnAlertConfirm);
        dialog.setCanceledOnTouchOutside(false);
        btnAlertCancel.setOnClickListener(v -> dialog.dismiss());
        btnAlertConfirm.setOnClickListener(v -> {
                dialog.dismiss();
                isCurrentScreenOnServiceScreen4 = false;
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavbar);
                bottomNavigationView.setSelectedItemId(bottomNavItemId);
                getActivity().findViewById(R.id.app_logo).setVisibility(View.VISIBLE);
            });

        dialog.show();
    }
    public void exitToSelectedFragmentOrBackStack(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnAlertCancel = dialog.findViewById(R.id.btnAlertCancel);
        Button btnAlertConfirm = dialog.findViewById(R.id.btnAlertConfirm);
        dialog.setCanceledOnTouchOutside(false);
        btnAlertCancel.setOnClickListener(v -> dialog.dismiss());
        btnAlertConfirm.setOnClickListener(v -> {
                dialog.dismiss();
                isCurrentScreenOnServiceScreen4 = false;
                getFragmentManager().popBackStack();
            });
        dialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //TODO: REMEMBER TO PUT A ANOTATION HERE
        isCurrentScreenOnServiceScreen4 = true;


        //ServiceScreen4 Real Code
        mainScreenView = getActivity().findViewById(android.R.id.content);
        documentStatsItem = new ArrayList<>();
        uriSaver = getActivity().getSharedPreferences("uriSaver",Context.MODE_PRIVATE);
        hashMap = new HashMap<>();
        btnNext = view.findViewById(R.id.btnNext);
        recyclerDocuments = view.findViewById(R.id.recyclerDocuments);
        recyclerDocuments.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitFetch();

        btnNext.setOnClickListener(v -> {
            if (hashMap.size()==documentItemsTeste.size()){
                for (int i = 0; i < documentItemsTeste.size(); ++i){
                    Uri uri = hashMap.get(i);
                    File file = new File(FileUtils.getPath(uri,getContext()));
                    documentItemsTeste.get(i).setDocumentUri(uri);
                    documentItemsTeste.get(i).setTxtFileName(file.getName());
                    documentItemsTeste.get(i).setTxtFileSize(Formatter.formatFileSize(getContext(),file.length()));
                    documentItemsTeste.get(i).setTxtFileExt("PDF");
                }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.services_container,new ServiceScreen5(documentItemsTeste,idDivision,idService))
                        .commit();

            }
            else{
                Snackbar.make(mainScreenView,"Despacho de Documentos em Falta",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void retrofitFetch() {
        RetrofitConfig retrofitConfig = ((MainScreen)getActivity()).retrofitConfig;
        Call<List<DocumentItem>> call = retrofitConfig.callDocument(idService);
        call.enqueue(new Callback<List<DocumentItem>>() {
            @Override
            public void onResponse(Call<List<DocumentItem>> call, Response<List<DocumentItem>> response) {
                if (response.isSuccessful()) {
                    onResponseSuccess(response.body(), retrofitConfig.baseUrl);
                } else
                    retrofitConfig.failureThread(getFragmentManager(),R.id.services_container);
            }

            @Override
            public void onFailure(Call<List<DocumentItem>> call, Throwable t) {
                retrofitConfig.failureThread(getFragmentManager(),R.id.services_container);
            }
        });
    }


    private void onResponseSuccess(List<DocumentItem> documentItems, String baseUrl) {
        if (documentItems == null)
            Toast.makeText(getContext(), "Response is Successful but ResponseBody is null", Toast.LENGTH_SHORT).show();
        else {
            documentItemsTeste = documentItems;
            documentAdapter = new DocumentAdapter(getContext(),documentItems,getActivity(),this);
            recyclerDocuments.setAdapter(documentAdapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 102:
                if (resultCode == getActivity().RESULT_OK && data != null && data.getData() != null){
                    SharedPreferences uriSaver = getActivity().getSharedPreferences("uriSaver",getActivity().MODE_PRIVATE);
                    documentItemPosition = uriSaver.getInt("itemPosition",100);
                    Uri uri = data.getData();
                    if (hashMap.containsValue(uri)) {
                        for (Map.Entry<Integer, Uri> entry : hashMap.entrySet()) {
                            if (entry.getValue().getPath().equals(uri.getPath())) {
                                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),"O Documento escolhido já foi disposto no(a) "+documentItemsTeste.get(entry.getKey()).getTxtDocument(),Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Fechar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        snackbar.dismiss();
                                    }
                                }).show();
                                break;
                            } else {
                                Toast.makeText(getContext(), "Not", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        try {
                            if (new File(FileUtils.getPath(uri,getContext())).length()/(1024*1024) <= 2){
                                hashMap.put(documentItemPosition,uri);
                                editDocumentPath =
                                        recyclerDocuments
                                                .findViewHolderForAdapterPosition(documentItemPosition)
                                                .itemView
                                                .findViewById(R.id.editDocumentPath);

                                editDocumentPath.setText(FileUtils.getPath(uri,getContext()));

                            }else {
                                Snackbar snackbar = Snackbar.make(mainScreenView,"Tamanho do Documento escolhido excedeu o limite - escolha um documento que tenha menos de 3M",Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Fechar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        snackbar.dismiss();
                                    }
                                });
                                View snackbarView = snackbar.getView();
                                TextView snackTextView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                snackTextView.setMaxLines(5);
                                snackbar.show();
                        }}catch (NullPointerException ex){
                            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }

    }

    /*
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
*/



}