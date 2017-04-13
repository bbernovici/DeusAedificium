package com.deusbuilding.util;

public class Functions {

    public static Double transformFromPixelsToMeters(Double value) {
        return value/Constants.PIXELS_PER_METER;
    }

    public static Double transformFromMetersToPixels(Double value) {
        return value*Constants.PIXELS_PER_METER;
    }
}
