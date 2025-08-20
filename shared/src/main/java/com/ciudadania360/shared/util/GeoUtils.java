package com.ciudadania360.shared.util;

public final class GeoUtils {
    private GeoUtils(){}
    public static boolean isValidLat(double lat){ return lat >= -90 && lat <= 90; }
    public static boolean isValidLon(double lon){ return lon >= -180 && lon <= 180; }
}
