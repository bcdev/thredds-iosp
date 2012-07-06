package com.bc.netcdf;

import org.junit.After;
import org.junit.Test;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.util.CancelTask;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class EnvisatIoServiceProviderAcceptanceTest {

    private RandomAccessFile randomAccessFile;

    @After
    public void tearDown() throws IOException {
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }

    @Test
    public void testOpen() throws URISyntaxException, IOException {
        final File file = ThreddsIOSPTestUtils.getResourceFile_validated("ATS_NR__2PNMAP20060918_095303_000000532051_00193_23794_0001.N1");
        randomAccessFile = new RandomAccessFile(file.getAbsolutePath(), "r");

        final EnvisatIoServiceProvider provider = new EnvisatIoServiceProvider();
        final NetcdfFile ncfile = new TestNetcdfFile();
        provider.open(randomAccessFile, ncfile, new TestCancelTask());

//        final List<Attribute> globalAttributes = ncfile.getGlobalAttributes();
        // @todo 1 tb/tb continue here
    }

    static class TestNetcdfFile extends NetcdfFile {
        TestNetcdfFile() {
        }
    }

    static class TestCancelTask implements CancelTask {
        public boolean isCancel() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setError(String msg) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
