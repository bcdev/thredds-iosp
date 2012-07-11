package com.bc.netcdf;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class RasterDimensionMapTest {

    @Test
    public void testCreateKey() {
        assertEquals("12_56", RasterDimensionMap.createKey(12, 56));
    }

    @Test
    public void testAddAndGet() {
        final RasterDimension rasterDimension = new RasterDimension("iks", 23, "yps", 44);

        final RasterDimensionMap rasterDimensionMap = new RasterDimensionMap();
        rasterDimensionMap.add(rasterDimension);

        final RasterDimension result = rasterDimensionMap.get(23, 44);
        assertNotNull(result);
        assertEquals("iks", result.getXDimension().getName());
        assertEquals("yps", result.getYDimension().getName());
    }
}
