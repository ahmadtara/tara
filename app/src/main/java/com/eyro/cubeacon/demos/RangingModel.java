package com.eyro.cubeacon.demos;

public class RangingModel {
    private String mtitle;
    private String msubtitle;
    private String mstatus;

    public RangingModel(String title, String subtitle, String status) {
        mtitle = title;
        msubtitle = subtitle;
        mstatus = status;
    }

    public String getMstatus() {
        return mstatus;
    }

    public String getMsubtitle() {
        return msubtitle;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public void setMsubtitle(String msubtitle) {
        this.msubtitle = msubtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }
}
