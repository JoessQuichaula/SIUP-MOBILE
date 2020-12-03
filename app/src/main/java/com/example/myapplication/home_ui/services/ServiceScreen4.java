package com.example.myapplication.home_ui.services;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathUtils;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.adapters.DocumentAdapter;
import com.example.myapplication.adapters.ServiceAdapter;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.ServiceItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
    TextView txtDocument;
    ArrayList<Uri> documentUries;
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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        documentUries = new ArrayList<>();
        uriSaver = getActivity().getSharedPreferences("uriSaver",Context.MODE_PRIVATE);
        hashMap = new HashMap<>();
        btnNext = view.findViewById(R.id.btnNext);
        recyclerDocuments = view.findViewById(R.id.recyclerDocuments);
        recyclerDocuments.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitFetch();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashMap.size()==documentItemsTeste.size()){
                    for (Map.Entry<Integer, Uri> entry : hashMap.entrySet()) {
                        documentUries.add(entry.getValue());
                    }
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.services_container,new ServiceScreen5(documentUries,idDivision,idService))
                            .commit();

                }else{
                    Snackbar.make(getView(),"Despacho de Documentos em Falta",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    private void retrofitFetch() {
        final RetrofitConfig retrofitConfig = new RetrofitConfig();
        retrofitConfig.initRetrofit();
        Call<List<DocumentItem>> call = retrofitConfig.callDocument(idService);
        call.enqueue(new Callback<List<DocumentItem>>() {
            @Override
            public void onResponse(Call<List<DocumentItem>> call, Response<List<DocumentItem>> response) {
                if (response.isSuccessful()) {
                    onResponseSuccess(response.body(), retrofitConfig.baseUrl);
                } else
                    Toast.makeText(getContext(), "Response is not Sucessful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<DocumentItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
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
                                Snackbar snackbar = Snackbar.make(getView(),"O Documento escolhido já foi disposto no(a) "+documentItemsTeste.get(entry.getKey()).getTxtDocument(),Snackbar.LENGTH_INDEFINITE);
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

                        hashMap.put(documentItemPosition,uri);

                        editDocumentPath = recyclerDocuments
                                .findViewHolderForAdapterPosition(documentItemPosition)
                                .itemView
                                .findViewById(R.id.editDocumentPath);
                        editDocumentPath.setText(getPath(uri,getContext()));




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


    private static Uri contentUri;
    //@SuppressLint("NewApi")
    public static String getPath( final Uri uri, Context context) {
        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String selection = null;
        String[] selectionArgs = null;
        // DocumentProvider
        if (isKitKat) {

            // ExternalStorageProvider
           if (isExternalStorageDocument(uri)) {
                Toast.makeText(context, "isExternalStorageUri", Toast.LENGTH_SHORT).show();
                /*
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                String fullPath = getPathFromExtSD(split);
                if (!fullPath.equals("")) {
                    return fullPath;
                } else {
                    return null;
                }
                */
            }

            // DownloadsProvider
            if (isDownloadsDocument(uri)) {
                Toast.makeText(context, "isDownloadDocument", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isGooglePhotosUri(uri)) {
                        return uri.getLastPathSegment();
                    }
                    if (isGoogleDriveUri(uri)) {
                        return getDriveFilePath(uri,context);
                    }
                    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {

                        // return getFilePathFromURI(context,uri);
                        return copyFileToInternalStorage(uri,"userfiles",context);
                        // return getRealPathFromURI(context,uri);
                    }

                    //This Comment is for Build Version M
                    /*
                    final String id;
                    Cursor cursor = null;
                    try {
                        cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String fileName = cursor.getString(0);
                            String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            if (!TextUtils.isEmpty(path)) {
                                return path;
                            }
                        }
                    }
                    finally {
                        if (cursor != null)
                            cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                final Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));


                                return getDataColumn(context, contentUri, null, null);
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }


                    }

                 */
                }

                else {

                    final String id = DocumentsContract.getDocumentId(uri);


                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }

                    try {
                        contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (contentUri != null) {
                        String filePath = getDataColumn(context, contentUri, null, null);
                        String newFilePath;
                        Toast.makeText(context, "FilePath: "+filePath, Toast.LENGTH_SHORT).show();
                        if (filePath.contains("sdcard0")){
                            newFilePath = filePath.replace("sdcard0","sdcard1");
                            return newFilePath;
                        }
                        return filePath;
                    }
                }
            }

            // MediaProvider
            if (isMediaDocument(uri)) {
                Toast.makeText(context, "isMediaDocument", Toast.LENGTH_SHORT).show();
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
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }

            if (isGoogleDriveUri(uri)) {
                Toast.makeText(context, "isGoogleDriveUri", Toast.LENGTH_SHORT).show();
                return getDriveFilePath(uri,context);
            }

            if(isWhatsAppFile(uri)){
                Toast.makeText(context, "isWhatsappFile", Toast.LENGTH_SHORT).show();
                return getFilePathForWhatsApp(uri,context);
            }


            if ("content".equalsIgnoreCase(uri.getScheme())) {
                Toast.makeText(context, "isContent", Toast.LENGTH_SHORT).show();
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                if (isGoogleDriveUri(uri)) {
                    return getDriveFilePath(uri,context);
                }
                if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {

                    // return getFilePathFromURI(context,uri);
                    return copyFileToInternalStorage(uri,"userfiles",context);
                    // return getRealPathFromURI(context,uri);
                }
                else
                {
                    return getDataColumn(context, uri, null, null);
                }

            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                Toast.makeText(context, "isFile", Toast.LENGTH_SHORT).show();
                return uri.getPath();
            }
        }
        else {

            if(isWhatsAppFile(uri)){
                return getFilePathForWhatsApp(uri,context);
            }

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
    private static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    private static String getPathFromExtSD(String[] pathData) {
        final String type = pathData[0];
        final String relativePath = "/" + pathData[1];
        String fullPath = "";

        // on my Sony devices (4.4.4 & 5.1.1), `type` is a dynamic string
        // something like "71F8-2C0A", some kind of unique id per storage
        // don't know any API that can get the root path of that storage based on its id.
        //
        // so no "primary" type, but let the check here for other devices
        if ("primary".equalsIgnoreCase(type)) {
            fullPath = Environment.getExternalStorageDirectory() + relativePath;
            if (fileExists(fullPath)) {
                return fullPath;
            }
        }

        // Environment.isExternalStorageRemovable() is `true` for external and internal storage
        // so we cannot relay on it.
        //
        // instead, for each possible path, check if file exists
        // we'll start with secondary storage as this could be our (physically) removable sd card
        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        return fullPath;
    }
    private static String getDriveFilePath(Uri uri,Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    /*
    /***
     * Used for Android Q+
     * @param uri
     * @param newDirName if you want to create a directory, you can set this variable
     * @return
     */


    private static String copyFileToInternalStorage(Uri uri,String newDirName,Context context) {
        Uri returnUri = uri;

        Cursor returnCursor = context.getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE
        }, null, null, null);


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if(!newDirName.equals("")) {
            File dir = new File(context.getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(context.getFilesDir() + "/" + newDirName + "/" + name);
        }
        else{
            output = new File(context.getFilesDir() + "/" + name);
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();

        }
        catch (Exception e) {

            Log.e("Exception", e.getMessage());
        }

        return output.getPath();
    }
    private static String getFilePathForWhatsApp(Uri uri,Context context){
        return  copyFileToInternalStorage(uri,"whatsapp",context);
    }
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    private  static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    public static boolean isWhatsAppFile(Uri uri){
        return "com.whatsapp.provider.media".equals(uri.getAuthority());
    }
    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

}