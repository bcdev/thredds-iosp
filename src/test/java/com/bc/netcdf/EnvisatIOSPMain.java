package com.bc.netcdf;

import ucar.nc2.NetcdfFile;

import java.io.IOException;

public class EnvisatIOSPMain {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        NetcdfFile.registerIOProvider("com.bc.netcdf.EnvisatIoServiceProvider");

        if (args.length != 1) {
            System.err.println("ERROR: Must provide path to ENVISAT file");
            return;
        }

        final NetcdfFile netcdfFile = NetcdfFile.open(args[0], null);

        netcdfFile.close();
    }
}
