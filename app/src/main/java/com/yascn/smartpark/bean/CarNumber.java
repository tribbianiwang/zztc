package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/8/29.
 */

public class CarNumber {
    private int viewType;
    private boolean setting;
    private Userinfo.ObjectBean.NumberListBean numberListBean;
    private List<NumberList.ObjectBean.MCARDLISTBean> mcardList;

    public List<NumberList.ObjectBean.MCARDLISTBean> getMcardList() {
        return mcardList;
    }

    public void setMcardList(List<NumberList.ObjectBean.MCARDLISTBean> mcardList) {
        this.mcardList = mcardList;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isSetting() {
        return setting;
    }

    public void setSetting(boolean setting) {
        this.setting = setting;
    }

    public Userinfo.ObjectBean.NumberListBean getNumberListBean() {
        return numberListBean;
    }

    public void setNumberListBean(Userinfo.ObjectBean.NumberListBean numberListBean) {
        this.numberListBean = numberListBean;
    }

    @Override
    public String toString() {
        return "CarNumber{" +
                "viewType=" + viewType +
                ", setting=" + setting +
                ", numberListBean=" + numberListBean +
                '}';
    }
}
