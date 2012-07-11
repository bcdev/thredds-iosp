package com.bc.netcdf;

import ucar.nc2.Dimension;

class RasterDimension {
    private Dimension xDimension;
    private Dimension yDimension;

    RasterDimension(String xName, int x, String yName, int y) {
        xDimension = new Dimension(xName, x);
        yDimension = new Dimension(yName, y);
    }

    RasterDimension() {
    }

    void setXDimension(Dimension xDimension) {
        this.xDimension = xDimension;
    }

    Dimension getXDimension() {
        return xDimension;
    }

    void setYDimension(Dimension yDimension) {
        this.yDimension = yDimension;
    }

    Dimension getYDimension() {
        return yDimension;
    }
}
