package com.example.atlas_bio;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.NotNull;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapFicheFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSION = 1;
    String coordonneeGPS;

    float longitude;

    float latitude;

    MapView map;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Manifest.permission.INTERNET
        };

        if (!checkMultiplePermissions(requireContext(),permissions)) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE_PERMISSION);
        } else {

            Configuration.getInstance().setDebugMode(true);
            initializeMap();

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
        mapController.setCenter(startPoint);
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
        boolean isPerm = true;

        if (requestCode == REQUEST_CODE_PERMISSION) {
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
        }

        if(isPerm) {
            File osmdroidTilesDirectory = new File("/storage/emulated/0/osmdroid/tiles");
            if (!osmdroidTilesDirectory.exists()) {
                osmdroidTilesDirectory.mkdirs();
            }
            initializeMap();
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


}
