package com.bc.netcdf;

import thredds.servlet.DatasetSource;
import ucar.nc2.NetcdfFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EnvisatDatasetSource implements DatasetSource {

    static {
        try {
            NetcdfFile.registerIOProvider("com.bc.netcdf.EnvisatIoServiceProvider");
            System.out.println("------------------ registered EnvisatIoServiceProvider ----------------");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // we don't do anything in this class, the only important code is the static-block above that registers our IOSP to the NetCDF lib
    // tb 2012-07-03
    public boolean isMine(HttpServletRequest req) {
        //throw new IllegalStateException("!!!!!!!!!!!!!    B�NG !!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return false;
    }

    public NetcdfFile getNetcdfFile(HttpServletRequest req, HttpServletResponse res) throws IOException {
        return null;
    }
}
