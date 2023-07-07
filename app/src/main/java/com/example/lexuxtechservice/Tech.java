package com.example.lexuxtechservice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lexuxtechservice.Response.RegisterResponse;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tech extends AppCompatActivity {

    private Spinner serviceSpinner, demarrageSpinner;
    private EditText societeEditText,  simEditText, voitureEditText,
            kilometrageEditText, gpsEditText,imeiEditText,matriculeEditText;

    private TextView AddressText,DateText,HoraireText;

    private Button btnValider,btnCapture1,btnCapture2;

    private int  buttonIdentifier=0;

    private static final int REQUEST_CAMERA_CODE = 100;

    private LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_layout);



        societeEditText = findViewById(R.id.nom_de_societe);
        imeiEditText = findViewById(R.id.imei);
        simEditText = findViewById(R.id.carte_sim);
        voitureEditText = findViewById(R.id.marque_voiture);
        matriculeEditText= findViewById(R.id.matricule);
        kilometrageEditText = findViewById(R.id.kilometrage);
        gpsEditText = findViewById(R.id.marque_gps);
        btnValider = findViewById(R.id.valider);
        btnCapture1 = findViewById(R.id.capture1);
        btnCapture2= findViewById(R.id.capture2);
        serviceSpinner = findViewById(R.id.type_de_service);
        demarrageSpinner = findViewById(R.id.anti_demarrage);
        AddressText= findViewById(R.id.adresse);
        imeiEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        kilometrageEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        simEditText.setInputType(InputType.TYPE_CLASS_NUMBER);




        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_de_service, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.anti_demarrage, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        demarrageSpinner.setAdapter(adapter2);


        if(ContextCompat.checkSelfPermission(Tech.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Tech.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        getCurrentLocation();
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(societeEditText.getText().toString()) || TextUtils.isEmpty(serviceSpinner.getSelectedItem().toString())
                        || TextUtils.isEmpty(simEditText.getText().toString())||TextUtils.isEmpty(voitureEditText.getText().toString()) ||
                TextUtils.isEmpty(gpsEditText.getText().toString())||TextUtils.isEmpty(kilometrageEditText.getText().toString()) ||
                        TextUtils.isEmpty(demarrageSpinner.getSelectedItem().toString())){
                    Toast.makeText(Tech.this,"Champs non remplis", Toast.LENGTH_LONG).show();
                }else{

                    register();
                    openWhatsApp();
                    societeEditText.setText("");
                    matriculeEditText.setText("");
                    gpsEditText.setText("");
                    voitureEditText.setText("");
                    kilometrageEditText.setText("");
                    simEditText.setText("");
                    imeiEditText.setText("");



                }
            }
        });




        btnCapture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      buttonIdentifier = 1;
                     CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(Tech.this);
                }

        });


        btnCapture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonIdentifier = 2;
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(Tech.this);

            }
        });
    }


    public void register() {

        String societe = societeEditText.getText().toString();
        String sim = simEditText.getText().toString();
        String voiture = voitureEditText.getText().toString();
        String kilometrage = kilometrageEditText.getText().toString();
        String gps = gpsEditText.getText().toString();
        String imei=imeiEditText.getText().toString();
        String matricule=matriculeEditText.getText().toString();
        String localisation=AddressText.getText().toString();
        String service=serviceSpinner.getSelectedItem().toString();
        String demarrage=demarrageSpinner.getSelectedItem().toString();

        Calendar calendar = Calendar.getInstance();
        Date selectedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(selectedDate);
        Calendar selectedTimeCalendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String selectedTime = timeFormat.format(selectedTimeCalendar.getTime());



        Call<RegisterResponse> call = RetrofitClientRegister.getInstance().getApi().register(societe, service, imei, sim,
                voiture, matricule, kilometrage, gps, demarrage, localisation,dateString,selectedTime);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getError().equals(000) ){

                    Toast.makeText(Tech.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Tech.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Tech.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(resultUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    switch (buttonIdentifier) {
                        case 1:
                            getTextFromImage(bitmap);
                            break;
                        case 2:
                            getTextFromImage1(bitmap);
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                if (resultCode == Activity.RESULT_OK) {

                    getCurrentLocation();
                }
            }
        }
    }

    private void getTextFromImage(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()){
            Toast.makeText(Tech.this,"Error", Toast.LENGTH_SHORT).show();
        }else{
            Frame frame  = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            imeiEditText.setText(stringBuilder.toString());

        }

    }
    private void getTextFromImage1(Bitmap bitmap){

        String mat,matricule="";
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()){
            Toast.makeText(Tech.this,"Error", Toast.LENGTH_SHORT).show();
        }else{
            Frame frame  = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
           mat=stringBuilder.toString();


            for (int i = 0; i < mat.length(); i++) {
                char caractereCourant= mat.charAt(i);
                char caractereAssocie= association(caractereCourant);
                matricule+=caractereAssocie;
            }



            matriculeEditText.setText(matricule);
            Toast.makeText(Tech.this, matricule, Toast.LENGTH_SHORT).show();

        }

    }


    public static char association(char caractere) {

        if (caractere=='أ' ) {
            return 'A';
        } else if (caractere== 'ب') {
            return 'B';
        } else if (caractere == 'ت') {
            return 'T';
        } else if (caractere == 'ث') {
            return 't';
        } else if (caractere == 'ج') {
            return 'J';
        } else if (caractere== 'ح') {
            return 'H';
        } else if (caractere == 'خ') {
            return 'k';
        } else if (caractere == 'د') {
            return 'D';
        } else if (caractere== 'ذ') {
            return 'd';
        } else if (caractere == 'ر') {
            return 'R';
        } else if (caractere == 'ز') {
            return 'Z';
        } else if (caractere == 'س') {
            return 'S';
        } else if (caractere == 'ش') {
            return 's';
        } else if (caractere == 'ص') {
            return 's';
        } else if (caractere == 'ض') {
            return 'd';
        } else if (caractere== 'ط') {
            return 'T';
        } else if (caractere== 'ظ') {
            return 'z';
        } else if (caractere == 'ع') {
            return 'a';
        } else if (caractere == 'ف') {
            return 'F';
        } else if (caractere == 'ق') {
            return 'Q';
        } else if (caractere == 'ك') {
            return 'K';
        } else if (caractere == 'ل') {
            return 'L';
        } else if (caractere == 'م') {
            return 'M';
        } else if (caractere== 'ن') {
            return 'N';
        } else if (caractere == 'ه') {
            return 'H';
        } else if (caractere == 'و') {
            return 'W';
        } else if (caractere== 'ي' ) {
            return 'Y';
        }

        return caractere;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Tech.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(Tech.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(Tech.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();


                                        AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);

                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(Tech.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Tech.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }


    public void openWhatsApp(){
        PackageManager pm=getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            String societe = societeEditText.getText().toString();
            String sim = simEditText.getText().toString();
            String voiture = voitureEditText.getText().toString();
            String kilometrage = kilometrageEditText.getText().toString();
            String gps = gpsEditText.getText().toString();
            String imei=imeiEditText.getText().toString();
            String matricule=matriculeEditText.getText().toString();
            String service=serviceSpinner.getSelectedItem().toString();
            String demarrage=demarrageSpinner.getSelectedItem().toString();


            String text = "Nom de société: "+societe+"\n"+"Type de service: "+service+"\n"+"IMEI: "+imei+"\n"+
                    "Carte Sim: "+sim+"\n"+"Marque de voiture: "+voiture+"\n"+"Matricule: "+matricule+"\n"+
                    "Kilométrage: "+kilometrage+"\n"+"Marque de GPS: "+gps+"\n"+"Anti-Démarrage: "+demarrage+"\n";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp non installé", Toast.LENGTH_SHORT)
                    .show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }



}
