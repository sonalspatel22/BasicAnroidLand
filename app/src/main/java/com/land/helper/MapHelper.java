package com.land.helper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

/**
 * Created by Dashrath on 12/26/2017.
 */

public class MapHelper {

    public static LatLng getPolygonCenterPoint(List<LatLng> polygonPointsList) {
        LatLng centerLatLng = null;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < polygonPointsList.size(); i++) {
            builder.include(polygonPointsList.get(i));
        }
        LatLngBounds bounds = builder.build();
        centerLatLng = bounds.getCenter();

        return centerLatLng;
    }

}
