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

import androidx.activity.result.ActivityResultLauncher;
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
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
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

    private Spinner serviceSpinner, demarrageSpinner,gpsSpinner;
    private EditText societeEditText, voitureEditText,
            kilometrageEditText, matriculeEditText;

    private TextView AddressText,imei_txt,sim_txt;

    private Button btnValider,btnCapture1,btnCapture2,btncapture3;


    private static final int REQUEST_CAMERA_CODE = 100;

    private LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_layout);



        societeEditText = findViewById(R.id.nom_de_societe);
        imei_txt = findViewById(R.id.imei);
        sim_txt = findViewById(R.id.sim);
        voitureEditText = findViewById(R.id.marque_voiture);
        matriculeEditText= findViewById(R.id.matricule);
        kilometrageEditText = findViewById(R.id.kilometrage);
        gpsSpinner = findViewById(R.id.marque_gps);
        btnValider = findViewById(R.id.valider);
        btnCapture1 = findViewById(R.id.capture1);
        btnCapture2= findViewById(R.id.capture2);
        btncapture3=findViewById(R.id.capture3);
        serviceSpinner = findViewById(R.id.type_de_service);
        demarrageSpinner = findViewById(R.id.anti_demarrage);
        AddressText= findViewById(R.id.adresse);


        // Définir le type d'entrée des champs de texte comme étant de type téléphone
        kilometrageEditText.setInputType(InputType.TYPE_CLASS_PHONE);




        // Créer une demande de localisation
        locationRequest = LocationRequest.create();
        // Définir la priorité de la demande de localisation sur une haute précision
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Définir l'intervalle de mise à jour de la localisation à 5000 millisecondes
        locationRequest.setInterval(5000);
        // Définir l'intervalle de mise à jour de la localisation le plus rapide à 2000 millisecondes
        locationRequest.setFastestInterval(2000);


        // Créer un ArrayAdapter à partir de la ressource "R.array.type_de_service"
        // La ressource "R.array.type_de_service" est une liste des service qu un technicien peut realiser
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_de_service, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Associer l'adaptateur (ArrayAdapter) au Spinner (serviceSpinner)
        // Cela permet d'afficher les éléments du spinner en utilisant l'adaptateur
        serviceSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.anti_demarrage, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        demarrageSpinner.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.marque_gps, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gpsSpinner.setAdapter(adapter3);

        // Verifier si la permission CAMERA n'a pas encore été accordée à l'activité "Tech"
        if(ContextCompat.checkSelfPermission(Tech.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            // Si la permission n'a pas été accordée, demande à l'utilisateur de la fournir
            ActivityCompat.requestPermissions(Tech.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        getCurrentLocation();

        //button pour enregistrer les donnees et envoyer un message vers whatsapp
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verifier que tous les champs sont remplis
                if(TextUtils.isEmpty(societeEditText.getText().toString()) || TextUtils.isEmpty(serviceSpinner.getSelectedItem().toString())
                        || TextUtils.isEmpty(sim_txt.getText().toString())||TextUtils.isEmpty(voitureEditText.getText().toString()) ||
                TextUtils.isEmpty(kilometrageEditText.getText().toString()) ||
                        TextUtils.isEmpty(demarrageSpinner.getSelectedItem().toString())|| TextUtils.isEmpty(matriculeEditText.getText().toString())
                        || TextUtils.isEmpty(imei_txt.getText().toString())){
                    Toast.makeText(Tech.this,"Champs non remplis", Toast.LENGTH_LONG).show();
                }else{

                    register();
                    openWhatsApp();
                    // Réinitialise les champs de texte à des valeurs vides ou par défaut
                    societeEditText.setText("");
                    matriculeEditText.setText("");
                    voitureEditText.setText("");
                    kilometrageEditText.setText("");
                    sim_txt.setText("");
                    imei_txt.setText("");
                    // Réinitialise les sélections des spinners à la première option
                    serviceSpinner.setSelection(0);
                    demarrageSpinner.setSelection(0);
                    gpsSpinner.setSelection(0);



                }
            }
        });



        //button pour scanner le code QR de IMEI
        btnCapture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
                }

        });

        //button pour scanner la matricule
        btnCapture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(Tech.this);

            }
        });

        //button pour scanner la carte sim
        btncapture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode1();
            }

        });


    }


    public void register() {

        String societe = societeEditText.getText().toString();
        String sim = sim_txt.getText().toString();
        String voiture = voitureEditText.getText().toString();
        String kilometrage = kilometrageEditText.getText().toString();
        String gps = gpsSpinner.getSelectedItem().toString();
        String imei=imei_txt.getText().toString();
        String matricule=matriculeEditText.getText().toString();
        String localisation=AddressText.getText().toString();
        String service=serviceSpinner.getSelectedItem().toString();
        String demarrage=demarrageSpinner.getSelectedItem().toString();

        // Récupèrer les extras passés à l'intent qui a démarré cette activité
        Bundle b=getIntent().getExtras();
        // Récupèrer la valeur de la clé "technicien" du bundle comme une chaîne de caractères
        String user=b.getString("technicien");

        //Detecter la date lorsque les donnees sont enregistrees
        Calendar calendar = Calendar.getInstance();
        Date selectedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(selectedDate);

        //Detecter le temps lorsque les donnees sont enregistrees
        Calendar selectedTimeCalendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String selectedTime = timeFormat.format(selectedTimeCalendar.getTime());


        // Créer un appel à l'API avec les paramètres requis pour l'enregistrement
        Call<RegisterResponse> call = RetrofitClientRegister.getInstance().getApi().register(societe, service, imei, sim,
                voiture, matricule, kilometrage, gps, demarrage, localisation,dateString,selectedTime,user);

        // Exécuter l'appel de manière asynchrone
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                // Récupèrer la réponse de l'appel
                RegisterResponse registerResponse = response.body();

                //Au cas ou l'erreur a pour valeur 000 , l enregistrement est effectue avec succe
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
            // Vérifier si le résultat provient de l'activité de recadrage d'image
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(resultUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    getTextFromImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                //Si le résultat n'est pas OK, vérifie si le code de résultat correspond à RESULT_OK de l activite de localisation
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


            matriculeEditText.setText(stringBuilder.toString());

        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Vérifier si la demande de permission correspond au code de requête spécifié
        if (requestCode == 1){
            // Vérifier si la permission a été accordée
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Vérifier si le GPS est activé
                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {
                    // Activer le GPS
                    turnOnGPS();
                }
            }
        }


    }
    //Determiner la position actuelle(longitude et latitude)
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
    //Activer le GPS
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

            // Créer une intent pour envoyer un message via WhatsApp
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            String societe = societeEditText.getText().toString();
            String sim = sim_txt.getText().toString();
            String voiture = voitureEditText.getText().toString();
            String kilometrage = kilometrageEditText.getText().toString();
            String gps = gpsSpinner.getSelectedItem().toString();
            String imei=imei_txt.getText().toString();
            String matricule=matriculeEditText.getText().toString();
            String service=serviceSpinner.getSelectedItem().toString();
            String demarrage=demarrageSpinner.getSelectedItem().toString();

            // Créer le texte à partager via WhatsApp en utilisant les valeurs récupérées
            String text = "Nom de société: "+societe+"\n"+"Type de service: "+service+"\n"+"IMEI: "+imei+"\n"+
                    "Carte Sim: "+sim+"\n"+"Marque de voiture: "+voiture+"\n"+"Matricule: "+matricule+
                    "Kilométrage: "+kilometrage+"\n"+"Marque de GPS: "+gps+"\n"+"Anti-Démarrage: "+demarrage+"\n";

            // Vérifier si l'application WhatsApp est installée sur l'appareil
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

            waIntent.setPackage("com.whatsapp");

            // Lance l'intent en ouvrant le sélecteur d'applications
            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp non installé", Toast.LENGTH_SHORT)
                    .show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);

    }
    ActivityResultLauncher<ScanOptions> barLauncher =registerForActivityResult(new ScanContract(),result -> {
        // Récupère le contenu du code scanné et l'affiche dans le TextView "imei_txt"
        imei_txt.setText(result.getContents());

    });
    private void scanCode1(){
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher1.launch(options);

    }
    ActivityResultLauncher<ScanOptions> barLauncher1 =registerForActivityResult(new ScanContract(),result -> {
        // Récupèrer le contenu du code scanné
        String resultat = result.getContents();
        //eliminer le premier cartere de la chaine puis concatener "00212"avec le resultatl'affiche dans le TextView "sim_txt"

        sim_txt.setText("00212"+resultat.substring(1));

    });



}
