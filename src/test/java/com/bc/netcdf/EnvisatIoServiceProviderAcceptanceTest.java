package com.bc.netcdf;

import org.junit.After;
import org.junit.Test;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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

        final List<Variable> variables = ncfile.getVariables();
        assertNotNull(variables);
        assertEquals(23, variables.size());

        final Variable metadata = ncfile.findVariable("metadata");
        assertNotNull(metadata);
        testCorrectMPH(metadata);
        testCorrectSPH(metadata);

        final Variable lst = ncfile.findVariable("lst");
        assertNotNull(lst);
        assertEquals("lst", lst.getName());
        assertEquals("Land surface temperature", lst.getDescription());
        assertEquals(DataType.FLOAT, lst.getDataType());
        assertEquals(512, lst.getDimension(0).getLength());
        assertEquals(353, lst.getDimension(1).getLength());
        assertEquals("x", lst.getDimension(0).getName());
        assertEquals("y", lst.getDimension(1).getName());

        final Variable view_elev_fward = ncfile.findVariable("view_elev_fward");
        assertNotNull(view_elev_fward);
        assertEquals("view_elev_fward", view_elev_fward.getName());
        assertEquals("Satellite elevation forward view", view_elev_fward.getDescription());
        assertEquals(DataType.FLOAT, view_elev_fward.getDataType());
        // @todo 1 tb/tb continue here
//        assertEquals(11, view_elev_fward.getDimension(0).getLength());
//        assertEquals(12, view_elev_fward.getDimension(1).getLength());
//        assertEquals("tp_x", view_elev_fward.getDimension(0).getName());
//        assertEquals("tp_y", view_elev_fward.getDimension(1).getName());
    }

    private void testCorrectSPH(Variable metadata) {
        final Attribute sph_descriptor = metadata.findAttribute("SPH:SPH_DESCRIPTOR");
        assertNotNull(sph_descriptor);
        assertEquals("GSST                        ", sph_descriptor.getStringValue());

        final Attribute last_lat = metadata.findAttribute("SPH:LAST_LAST_LAT");
        assertNotNull(last_lat);
        assertEquals(66775815, last_lat.getNumericValue());

        final Attribute lat_lon_tp = metadata.findAttribute("SPH:LAT_LONG_TIE_POINTS");
        assertNotNull(lat_lon_tp);
        final Array values = lat_lon_tp.getValues();
        assertEquals(23, values.getSize());
        assertEquals(-125, values.getInt(6));
    }

    private void testCorrectMPH(Variable metadata) {
        final Attribute mph_product = metadata.findAttribute("MPH:PRODUCT");
        assertNotNull(mph_product);
        assertEquals("ATS_NR__2PNMAP20060918_095303_000000532051_00193_23794_0001.N1", mph_product.getStringValue());

        final Attribute rel_orbit = metadata.findAttribute("MPH:REL_ORBIT");
        assertNotNull(rel_orbit);
        assertEquals(193, rel_orbit.getNumericValue());

        final Attribute num_dsd = metadata.findAttribute("MPH:NUM_DSD");
        assertNotNull(num_dsd);
        assertEquals(13, num_dsd.getNumericValue());
    }

    static class TestNetcdfFile extends NetcdfFile {
        TestNetcdfFile() {
            empty();
        }
    }
}
