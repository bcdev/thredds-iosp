package com.bc.netcdf;

import org.junit.Test;
import ucar.nc2.Dimension;

import static org.junit.Assert.assertEquals;

public class RasterDimensionTest {

    @Test
    public void testSetGetXDimension() {
        Dimension dimX = new Dimension("x", 23);

        final RasterDimension rasterDimension = new RasterDimension();
        rasterDimension.setXDimension(dimX);

        final Dimension result = rasterDimension.getXDimension();
        assertEquals("x", result.getName());
        assertEquals(23, result.getLength());
    }

    @Test
    public void testSetGetYDimension() {
        Dimension dimY = new Dimension("y", 109);

        final RasterDimension rasterDimension = new RasterDimension();
        rasterDimension.setYDimension(dimY);

        final Dimension result = rasterDimension.getYDimension();
        assertEquals("y", result.getName());
        assertEquals(109, result.getLength());
    }

    @Test
    public void testConstruction() {
        final RasterDimension rasterDimension = new RasterDimension("x", 23, "y", 56);

        final Dimension xDim = rasterDimension.getXDimension();
        assertEquals("x", xDim.getName());
        assertEquals(23, xDim.getLength());

        final Dimension yDim = rasterDimension.getYDimension();
        assertEquals("y", yDim.getName());
        assertEquals(56, yDim.getLength());
    }
}
