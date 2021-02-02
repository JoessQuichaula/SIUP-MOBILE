package com.example.myapplication.home_ui.services;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.example.myapplication.FileUtils;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.adapters.DocumentStatsAdapter;
import com.example.myapplication.adapters.ServiceAdapter;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.ServiceItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cdflynn.android.library.checkview.CheckView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceScreen5 extends Fragment {


    private List<DocumentItem> documentItems;
    private int idDivision;
    private int idService;
    private RecyclerView recyclerDocumentsStats;
    private DocumentStatsAdapter documentStatsAdapter;
    MainScreen mainScreen;

    public ServiceScreen5(List<DocumentItem> documentItems, int idDivision, int idService) {
        this.documentItems = documentItems;
        this.idDivision = idDivision;
        this.idService = idService;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_screen5, container, false);
    }

    Button btnSend;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnSend = view.findViewById(R.id.btnSend);
        recyclerDocumentsStats = view.findViewById(R.id.recyclerDocumentsStats);
        recyclerDocumentsStats.setLayoutManager(new LinearLayoutManager(getContext()));
        documentStatsAdapter = new DocumentStatsAdapter(documentItems,getContext());
        recyclerDocumentsStats.setAdapter(documentStatsAdapter);
        mainScreen = ((MainScreen)getActivity());
        btnSend.setOnClickListener(v -> retrofitFetch());

    }



    private void retrofitFetch() {
        RetrofitConfig retrofitConfig = ((MainScreen)getActivity()).retrofitConfig;
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (DocumentItem documentItem : documentItems){
            String documentId = Integer.toString(documentItem.getIdDocument());
            Uri documentUri = documentItem.getDocumentUri();
            MultipartBody.Part filePart = prepareFilePart(documentId,documentUri);
            parts.add(filePart);
        }

        RequestBody idDivisionPart = RequestBody.create(MultipartBody.FORM,Integer.toString(idDivision));
        RequestBody idServicePart = RequestBody.create(MultipartBody.FORM,Integer.toString(idService));
        RequestBody idUser = RequestBody.create(MultipartBody.FORM,Integer.toString(mainScreen.userItem.getId()));

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Enviando os Documentos aos nossos Servidores");
        progressDialog.setTitle("Aguarde Por Favor");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIcon(R.drawable.reallogo2);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Call<ResponseBody> call = retrofitConfig.callUploadDocuments(parts,idDivisionPart,idServicePart,idUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    onResponseSuccess();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Respondeu mas não enviou", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Envio falhou mesmo", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void onResponseSuccess() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_confirm);
        CheckView checkView = dialog.findViewById(R.id.check);
        checkView.check();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnDismissListener(dialog1 -> {

            ServiceScreen4 serviceScreen4 =
                    (ServiceScreen4)getParentFragment()
                            .getChildFragmentManager()
                            .findFragmentByTag("serviceScreen4");

            serviceScreen4.isCurrentScreenOnServiceScreen4 = false;
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavbar);
            bottomNavigationView.setSelectedItemId(R.id.homepage);
            getActivity().findViewById(R.id.appbar).setVisibility(View.VISIBLE);

        });
        dialog.show();
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String fileName,Uri fileUri){
        File file = new File(FileUtils.getPath(fileUri,getContext()));

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(fileName, file.getName(), requestFile);

        return body;
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