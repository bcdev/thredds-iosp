package com.bc.netcdf;

import org.junit.Before;
import org.junit.Test;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        final File validVFile = getResourceFile_validated("ATS_NR__2PNMAP20060918_095303_000000532051_00193_23794_0001.N1");

        RandomAccessFile validRAF = null;
        try {
            validRAF = new RandomAccessFile(validVFile.getAbsolutePath(), "r");
            assertTrue(serviceProvider.isValidFile(validRAF));
        } finally {
            if (validRAF != null) {
                validRAF.close();
            }
        }

        final File invalidVFile = getResourceFile_validated("not_a_valid.file");

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

    private File getResourceFile_validated(String name1) throws URISyntaxException {
        final File invalidVFile = new File(getClass().getResource(name1).toURI());
        assertTrue(invalidVFile.isFile());
        return invalidVFile;
    }
}
