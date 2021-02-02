package com.example.myapplication.registerScreens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.myapplication.ApiInterface;
import com.example.myapplication.FileUtils;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.SplashScreen;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterScreen3 extends AppCompatActivity {

    EditText editEmail;
    EditText editName;
    private EditText editIdentityPass;
    private EditText editPassword;
    private EditText editDocumentPath;
    private Button btnDocumentSearch;
    private SharedPreferences appDataSaver;
    Button btnBack;
    Button btnNext;
    Uri lastUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen3);

        appDataSaver = getSharedPreferences(SplashScreen.APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        editEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editIdentityPass = findViewById(R.id.editIdentityPass);
        editPassword = findViewById(R.id.editPassword);
        editDocumentPath = findViewById(R.id.editDocumentPath);
        btnDocumentSearch = findViewById(R.id.btnDocumentSearch);

        btnBack = findViewById(R.id.btnBack3);
        btnNext = findViewById(R.id.btnNext3);

        btnDocumentSearch.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent,102);
        });

        nextBack();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 102:
                if (resultCode == RESULT_OK && data != null && data.getData() != null){
                    Uri uri = data.getData();
                    if (lastUri != null && lastUri.equals(uri)) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"O Documento escolhido já foi disposto",Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("Fechar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        }).show();
                    }
                    else{
                        try {
                            File file = new File(FileUtils.getPath(uri,this));
                            if (file.length()/(1024*1024) <= 2){
                                lastUri = uri;
                                editDocumentPath.setText(file.getName());
                            }
                            else {
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Escolha um documento que tenha menos de 3MB",Snackbar.LENGTH_INDEFINITE);
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
                            }
                        }
                        catch (NullPointerException ex){
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
                }


    }



    private void nextBack(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen3.this, RegisterScreen.class);
                startActivity(intent);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }



    private boolean isFormValidated(){
        String regexForEditIdentityPass = "^[0-9]{9}[A-Z]{2}[0-9]{3}$";
        String regexForEditPassword = "^.{6,}$";
        String regexForEditName = "^([A-Z]+\\s)*[A-Z]+$";
        String nameErrorReport = "Nome não foi corretamente inserido, verifique se deixou demasiado Espaço";

        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(editName,regexForEditName,nameErrorReport);
        awesomeValidation.addValidation(editEmail,Patterns.EMAIL_ADDRESS,"Endereço de email não foi corretamente inserido");
        awesomeValidation.addValidation(editIdentityPass,regexForEditIdentityPass,"Nº de Bilhete foi inserido de forma incorreta");
        awesomeValidation.addValidation(editPassword,regexForEditPassword,"Palavra-Passe tem que conter 6 ou mais caracteres");


        AwesomeValidation awesomeValidation1 = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation1.setContext(RegisterScreen3.this);
        awesomeValidation1.addValidation(editDocumentPath,"info","Selecione o seu Bilhete de Identidade");
        if (lastUri == null){
            awesomeValidation1.validate();
        }

        return awesomeValidation.validate() && lastUri != null;
    }

    private void registerNewUser(){
        if (isFormValidated()){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SplashScreen.APP_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String identityPass = editIdentityPass.getText().toString();
            String password = editPassword.getText().toString();
            String phoneNumber = getIntent().getStringExtra("phoneNumber");
            //String phoneNumber = "945623998";

            RequestBody namePart = RequestBody.create(MultipartBody.FORM,name);
            RequestBody emailPart = RequestBody.create(MultipartBody.FORM,email);
            RequestBody phoneNumberPart = RequestBody.create(MultipartBody.FORM,phoneNumber);
            RequestBody passwordPart = RequestBody.create(MultipartBody.FORM,password);
            RequestBody identityPassPart = RequestBody.create(MultipartBody.FORM,identityPass);
            MultipartBody.Part filePart = prepareFilePart();

            Call<ResponseBody> call = apiInterface.createNewUser(namePart,emailPart,phoneNumberPart,passwordPart,identityPassPart,filePart);

            ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(RegisterScreen3.this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Por Favor Aguarde");
            progressDialog.setTitle("Carregando...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(RegisterScreen3.this, "Enviou", Toast.LENGTH_SHORT).show();
                        String base = phoneNumber+":"+password;
                        String authHeader = "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
                        appDataSaver.edit().putString("Auth",authHeader).apply();
                        appDataSaver.edit().putBoolean("isUserLogged",true).apply();

                        Gson gson = new Gson();
                        String userJson = gson.toJson(response.body());
                        appDataSaver.edit().putString("user",userJson).apply();
                        Intent intent = new Intent(RegisterScreen3.this, MainScreen.class);
                        startActivity(intent);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterScreen3.this, "response is not sucessfull", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterScreen3.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterScreen3.this, "response failured", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(){
        File file = new File(FileUtils.getPath(lastUri,RegisterScreen3.this));

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("bilheteIdentidade", file.getName(), requestFile);

        return body;
    }


}