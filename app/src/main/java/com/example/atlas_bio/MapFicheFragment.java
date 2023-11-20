package com.example.atlas_bio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.NotNull;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapFicheFragment extends Fragment {

    String coordonneeGPS;

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
        //CreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.map_fiche_fragment, container, false);
        this.coordonneeGPS = getArguments().getString("coordonneeGPS");
        this.map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        float latitude,longitude;
        latitude = getLatitude();
        longitude = getLongitude();
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        mapController.setCenter(startPoint);
        return view;
    }

    private float getLongitude() {
        String label = "Longitude: ";
        String part = this.coordonneeGPS.split(",")[1].split(label)[0];
        return Float.parseFloat(part);
    }

    private float getLatitude() {
        String label = "Latitude: ";
        String part = this.coordonneeGPS.split(",")[0].split(label)[0];
        return Float.parseFloat(part);
    }
}
