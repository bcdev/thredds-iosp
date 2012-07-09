package com.bc.netcdf;

import org.esa.beam.dataio.netcdf.nc.N3Variable;
import org.esa.beam.dataio.netcdf.nc.NFileWriteable;
import org.esa.beam.dataio.netcdf.nc.NVariable;
import ucar.ma2.DataType;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.awt.*;
import java.io.IOException;

public class NetCdfMemoryWriteable implements NFileWriteable {

    private final NetcdfFile ncfile;

    public NetCdfMemoryWriteable(NetcdfFile ncfile) {
        this.ncfile = ncfile;
    }

    public void addDimension(String name, int length) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getDimensions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addGlobalAttribute(String name, String value) throws IOException {
        System.out.println("GlobalAttribute: " + name + " - " + value);
    }

    public NVariable addScalarVariable(String name, DataType dataType) throws IOException {
        final Variable variable = new Variable(ncfile, null, null, name);

        ncfile.addVariable(null, variable);
        return new N3Variable(variable, null);
    }

    public NVariable addVariable(String name, DataType dataType, Dimension tileSize, String dims) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public NVariable findVariable(String variableName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void create() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
