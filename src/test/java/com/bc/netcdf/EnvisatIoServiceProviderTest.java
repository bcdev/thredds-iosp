package com.bc.netcdf;

import org.junit.Before;
import org.junit.Test;
import ucar.nc2.Dimension;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void testIsValidFile() throws URISyntaxException, IOException {
        final File validVFile = ThreddsIOSPTestUtils.getResourceFile_validated("ATS_NR__2PNMAP20060918_095303_000000532051_00193_23794_0001.N1");

        RandomAccessFile validRAF = null;
        try {
            validRAF = new RandomAccessFile(validVFile.getAbsolutePath(), "r");
            assertTrue(serviceProvider.isValidFile(validRAF));
        } finally {
            if (validRAF != null) {
                validRAF.close();
            }
        }

        final File invalidVFile = ThreddsIOSPTestUtils.getResourceFile_validated("not_a_valid.file");

        RandomAccessFile invalidRAF = null;
        try {
            invalidRAF = new RandomAccessFile(invalidVFile.getAbsolutePath(), "r");
            assertFalse(serviceProvider.isValidFile(invalidRAF));
        } finally {
            if (invalidRAF != null) {
                invalidRAF.close();
            }
        }
    }

    @Test
    public void testMustCancel() {
        assertFalse(EnvisatIoServiceProvider.mustCancel(null));

        final TestCancelTask cancelTask = new TestCancelTask();
        cancelTask.setCancel(false);
        assertFalse(EnvisatIoServiceProvider.mustCancel(cancelTask));

        cancelTask.setCancel(true);
        assertTrue(EnvisatIoServiceProvider.mustCancel(cancelTask));
    }

    @Test
    public void testCreateDimensionsList() {
        List<Dimension> dimensionList = EnvisatIoServiceProvider.createDimensionsList(12, 15, "x", "y");
        assertEquals(2, dimensionList.size());

        final Dimension xDim = dimensionList.get(0);
        assertEquals("x", xDim.getName());
        assertEquals(12, xDim.getLength());

        final Dimension yDim = dimensionList.get(1);
        assertEquals("y", yDim.getName());
        assertEquals(15, yDim.getLength());
    }

    @Test
    public void testCreateDimensionName() {
         assertEquals("x", EnvisatIoServiceProvider.createDimensionName("x", 0));
         assertEquals("y1", EnvisatIoServiceProvider.createDimensionName("y", 1));
         assertEquals("tp_x6", EnvisatIoServiceProvider.createDimensionName("tp_x", 6));
    }
}
