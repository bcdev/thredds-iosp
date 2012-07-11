package com.bc.netcdf;

import org.esa.beam.dataio.envisat.EnvisatProductReaderPlugIn;
import org.esa.beam.dataio.netcdf.ProfileWriteContext;
import org.esa.beam.dataio.netcdf.ProfileWriteContextImpl;
import org.esa.beam.dataio.netcdf.metadata.profiles.beam.BeamMetadataPart;
import org.esa.beam.dataio.netcdf.util.DataTypeUtils;
import org.esa.beam.framework.dataio.DecodeQualification;
import org.esa.beam.framework.dataio.ProductReader;
import org.esa.beam.framework.datamodel.Band;
import org.esa.beam.framework.datamodel.Product;
import org.esa.beam.framework.datamodel.RasterDataNode;
import org.esa.beam.framework.datamodel.TiePointGrid;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Section;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.iosp.AbstractIOServiceProvider;
import ucar.nc2.util.CancelTask;
import ucar.unidata.io.RandomAccessFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EnvisatIoServiceProvider extends AbstractIOServiceProvider {

    private EnvisatProductReaderPlugIn readerPlugin;
    private Product product;
    private HashMap<String, RasterDimension> dimensionMap;


    public EnvisatIoServiceProvider() {
        readerPlugin = new EnvisatProductReaderPlugIn();
        dimensionMap = new HashMap<String, RasterDimension>();
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

        if (mustCancel(cancelTask)) {
            return;
        }

        final BeamMetadataPart metadataPart = new BeamMetadataPart();
        final ProfileWriteContext writeContext = new ProfileWriteContextImpl(new NetCdfMemoryWriteable(ncfile));
        metadataPart.preEncode(writeContext, product);

        if (mustCancel(cancelTask)) {
            return;
        }

        final Band[] bands = product.getBands();
        int index = 0;
        for (final Band band : bands) {
            final int width = band.getSceneRasterWidth();
            final int height = band.getSceneRasterHeight();
            final String rasterDimensionName = width + "_" + height;
            if (!dimensionMap.containsKey(rasterDimensionName)) {
                final RasterDimension rasterDimension = new RasterDimension();
                String dimName = createDimensionName("x", index);
                rasterDimension.setXDimension(new Dimension(dimName, width));
                dimName = createDimensionName("y", index);
                rasterDimension.setYDimension(new Dimension(dimName, height));
                dimensionMap.put(rasterDimensionName, rasterDimension);
            }
        }

        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        index = 0;
        for (int i = 0; i < tiePointGrids.length; i++) {
            final TiePointGrid tiePointGrid = tiePointGrids[i];
            final int rasterWidth = tiePointGrid.getRasterWidth();
            final int rasterHeight = tiePointGrid.getRasterHeight();

        }

        addBandsAndDimensions(ncfile);
        addTiePointGrids(ncfile);

        ncfile.finish();
    }

    private void addTiePointGrids(NetcdfFile ncfile) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();

        final int rasterWidth = tiePointGrids[0].getRasterWidth();
        final int rasterHeight = tiePointGrids[0].getRasterHeight();
        final ArrayList<Dimension> dimensions = createDimensionsList(rasterWidth, rasterHeight, "tp_x", "tp_y");

        addDimensions(ncfile, dimensions);

        for (final TiePointGrid tiePointGrid : tiePointGrids) {
            final Variable variable = new Variable(ncfile, null, null, tiePointGrid.getName());
            addDescription(variable, tiePointGrid.getDescription());
            addDataType(tiePointGrid, variable);
            variable.setDimensions(dimensions);
            ncfile.addVariable(null, variable);
        }
    }

    private void addDimensions(NetcdfFile ncfile, ArrayList<Dimension> dimensions) {
        for (Dimension dimension : dimensions) {
            ncfile.addDimension(null, dimension);
        }
    }

    private void addBandsAndDimensions(NetcdfFile ncfile) {
        final int width = product.getSceneRasterWidth();
        final int height = product.getSceneRasterHeight();

        final ArrayList<Dimension> dimensions = createDimensionsList(width, height, "x", "y");

        addDimensions(ncfile, dimensions);

        final Band[] bands = product.getBands();
        for (final Band band : bands) {
            final Variable variable = new Variable(ncfile, null, null, band.getName());
            addDescription(variable, band.getDescription());
            addDataType(band, variable);
            variable.setDimensions(dimensions);

            ncfile.addVariable(null, variable);
        }
    }

    static ArrayList<Dimension> createDimensionsList(int width, int height, String x, String y) {
        final Dimension widthDim = new Dimension(x, width);
        final Dimension heightDim = new Dimension(y, height);
        final ArrayList<Dimension> dimensions = new ArrayList<Dimension>();
        dimensions.add(widthDim);
        dimensions.add(heightDim);
        return dimensions;
    }

    private void addDataType(RasterDataNode node, Variable variable) {
        final DataType netcdfDataType = DataTypeUtils.getNetcdfDataType(node.getGeophysicalDataType());
        variable.setDataType(netcdfDataType);
    }

    private void addDescription(Variable variable, String description) {
        variable.addAttribute(new Attribute("description", description));
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

    static boolean mustCancel(CancelTask cancelTask) {
        return cancelTask != null && cancelTask.isCancel();
    }

    static String createDimensionName(String dimensionPrefix, int index) {
        if (index > 0) {
            return dimensionPrefix + index;
        }
        return dimensionPrefix;
    }
}
