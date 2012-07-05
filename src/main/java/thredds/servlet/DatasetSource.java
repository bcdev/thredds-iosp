package thredds.servlet;

import ucar.nc2.NetcdfFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DatasetSource {

    public boolean isMine( HttpServletRequest req);

    public NetcdfFile getNetcdfFile( HttpServletRequest req, HttpServletResponse res) throws IOException;
}
