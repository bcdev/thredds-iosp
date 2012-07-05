package com.bc.netcdf;

import org.esa.beam.dataio.envisat.EnvisatProductReaderPlugIn;
import org.esa.beam.framework.dataio.DecodeQualification;
import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Section;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.iosp.AbstractIOServiceProvider;
import ucar.nc2.util.CancelTask;
import ucar.unidata.io.RandomAccessFile;

import java.io.IOException;

public class EnvisatIoServiceProvider extends AbstractIOServiceProvider {

    private EnvisatProductReaderPlugIn readerPlugin;

    public EnvisatIoServiceProvider() {
        readerPlugin = new EnvisatProductReaderPlugIn();
        throw new IllegalStateException("!!!!!!!!!!!!!    BÄNG-AGAIN  !!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public boolean isValidFile(RandomAccessFile randomAccessFile) throws IOException {
        final String location = randomAccessFile.getLocation();

        return readerPlugin.getDecodeQualification(location) == DecodeQualification.INTENDED;
    }

    @Override
    public void open(RandomAccessFile raf, NetcdfFile ncfile, CancelTask cancelTask) throws IOException {
        super.open(raf, ncfile, cancelTask);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws IOException {
        super.close();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Array readData(Variable variable, Section section) throws IOException, InvalidRangeException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFileTypeId() {
        final String[] formatNames = readerPlugin.getFormatNames();
        return formatNames[0];
    }

    public String getFileTypeDescription() {
        return readerPlugin.getDescription(null);
    }
}
