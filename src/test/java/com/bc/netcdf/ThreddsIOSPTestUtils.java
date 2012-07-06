package com.bc.netcdf;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class ThreddsIOSPTestUtils {
    static File getResourceFile_validated(String name1) throws URISyntaxException {
        final File invalidVFile = new File(ThreddsIOSPTestUtils.class.getResource(name1).toURI());
        assertTrue(invalidVFile.isFile());
        return invalidVFile;
    }
}
