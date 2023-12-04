package com.example.atlas_bio;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import org.jetbrains.annotations.NotNull;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapFicheFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_CODE_PERMISSION = 1;
    String coordonneeGPS;

    float longitude;

    float latitude;

    MapView map;

    Fiche fiche;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Context ctx = getContext().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        Bundle bundle = getArguments();
        if (bundle != null) {
            String nomCampagne = bundle.getString("nomCampagne", "");
            String espece = bundle.getString("espece", "");
            String coordonnees = bundle.getString("coordonneeGPS", "");
            String date = bundle.getString("date", "");
            String heure = bundle.getString("heure", "");
            String lieu = bundle.getString("lieu", "");
            String observation = bundle.getString("observation", "");
            String imageUrl = bundle.getString("imageUrl");
            if (imageUrl == null) imageUrl = "error"; // for fix bug

            fiche = new Fiche(espece);
            fiche.setCoordoneesGPS(coordonnees);
            fiche.setDate(date);
            fiche.setHeure(heure);
            fiche.setLieu(lieu);
            fiche.setObservation(observation);
            fiche.setImageUrl(imageUrl);
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fiche_fragment, container, false);
        this.coordonneeGPS = getArguments().getString("coordonneeGPS");
        this.map = (MapView) view.findViewById(R.id.map);

        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        if (!checkMultiplePermissions(requireContext(),permissions)) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE_PERMISSION);
            initStorage();
        } else {

            Configuration.getInstance().setDebugMode(true);
            initStorage();

        }
        return view;
    }

    public void initializeMap() {
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        setCoordonnee();
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        Marker point = new Marker(map);
        point.setPosition(startPoint);
        point.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(point);
        mapController.setCenter(startPoint);

        point.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Bundle bundle = new Bundle();
                bundle.putString("coordonneeGPS", fiche.getCoordoneesGPS());
                bundle.putString("nomCampagne", "");
                bundle.putString("espece", fiche.getEspece());
                bundle.putString("coordonnees", fiche.getCoordoneesGPS());
                bundle.putString("date", fiche.getDate());
                bundle.putString("heure", fiche.getHeure());
                bundle.putString("lieu", fiche.getLieu());
                bundle.putString("observation", fiche.getObservation());
                bundle.putString("imageUrl", fiche.getImageUrl());
                Navigation.findNavController(mapView).navigate(R.id.ficheList_To_FicheDetail,bundle);
                return false;
            }
        });
    }

    private void setCoordonnee() {
        Pattern pattern = Pattern.compile("-?\\d+\\.-?\\d+");
        Matcher matcher = pattern.matcher(this.coordonneeGPS);

        if(matcher.find()) {
            latitude = Float.parseFloat(matcher.group(0));
        }
        if(matcher.find()) {
            longitude = Float.parseFloat(matcher.group(0));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPerm = (grantResults[0] == 1 && grantResults[2] == 1) && (grantResults[1] == 1 || grantResults[3] == 1 || grantResults[4] == 1) ;

        /*if (requestCode == REQUEST_CODE_PERMISSION) {
            if(grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        isPerm = false;
                    }
                }
            } else {
                isPerm = false;
            }
        } else {
            isPerm = false;
        }*/

        if(isPerm) {
            org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
            File basePath = new File(Environment.getStorageDirectory(), "osmdroid");
            osmConf.setOsmdroidBasePath(basePath);
            File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tiles");
            osmConf.setOsmdroidTileCache(tileCache);

            /*File osmdroidTilesDirectory = new File("/storage/emulated/0/osmdroid/tiles");
            if (!osmdroidTilesDirectory.exists()) {
                osmdroidTilesDirectory.mkdir();
            }*/
            initStorage();
        }
    }

    public static boolean checkMultiplePermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void initStorage() {
        org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
        File basePath = new File(Environment.getStorageDirectory(), "osmdroid");
        osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tiles");
        osmConf.setOsmdroidTileCache(tileCache);

        initializeMap();
    }


}
