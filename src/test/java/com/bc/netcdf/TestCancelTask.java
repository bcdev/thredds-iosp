package com.bc.netcdf;

import ucar.nc2.util.CancelTask;

class TestCancelTask implements CancelTask {

    private boolean cancel = false;

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void setError(String msg) {
    }
}
