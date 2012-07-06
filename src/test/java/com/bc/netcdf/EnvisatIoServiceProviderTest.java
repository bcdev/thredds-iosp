package com.bc.netcdf;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvisatIoServiceProviderTest {

    private EnvisatIoServiceProvider serviceProvider;

    @Before
    public void setUp() {
        serviceProvider = new EnvisatIoServiceProvider();
    }

    @Test
    public void testGteFileTypeId() {
        assertEquals("ENVISAT", serviceProvider.getFileTypeId());
    }

    @Test
    public void testGetFileTypeDescrition() {
        assertEquals("ENVISAT MERIS, AATSR and ASAR products", serviceProvider.getFileTypeDescription());
    }
}
