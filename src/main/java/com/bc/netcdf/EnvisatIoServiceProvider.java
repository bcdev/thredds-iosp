package com.bc.netcdf;

import org.esa.beam.dataio.envisat.EnvisatProductReaderPlugIn;
import org.esa.beam.framework.dataio.DecodeQualification;
import org.esa.beam.framework.dataio.ProductReader;
import org.esa.beam.framework.datamodel.Product;
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
    private Product product;


    public EnvisatIoServiceProvider() {
        readerPlugin = new EnvisatProductReaderPlugIn();
    }

    public boolean isValidFile(RandomAccessFile randomAccessFile) throws IOException {
        final String location = randomAccessFile.getLocation();

        return readerPlugin.getDecodeQualification(location) == DecodeQualification.INTENDED;
    }

    @Override
    public void open(RandomAccessFile raf, NetcdfFile ncfile, CancelTask cancelTask) throws IOException {
        super.open(raf, ncfile, cancelTask);

        final String location = raf.getLocation();
        final ProductReader productReader = readerPlugin.createReaderInstance();
        product = productReader.readProductNodes(location, null);

        if (cancelTask.isCancel()) {
            return;
        }


    }

    @Override
    public void close() throws IOException {
        if (product != null) {
            product.dispose();
            product = null;
        }
        super.close();
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
