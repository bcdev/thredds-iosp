package com.bc.netcdf;

import ucar.nc2.Dimension;

import java.util.HashMap;

class RasterDimensionMap {

    private HashMap<String, RasterDimension> dimensionMap;

    RasterDimensionMap() {
        dimensionMap = new HashMap<String, RasterDimension>();
    }

    void add(RasterDimension rasterDimension) {
        final Dimension xDimension = rasterDimension.getXDimension();
        final Dimension yDimension = rasterDimension.getYDimension();
        final String key = createKey(xDimension.getLength(),yDimension.getLength());
        dimensionMap.put(key, rasterDimension);
    }

    RasterDimension get(int x, int y) {
        final String key = createKey(x, y);
        return dimensionMap.get(key);
    }

    static String createKey(int x, int y) {
        return x + "_" + y;
    }
}
