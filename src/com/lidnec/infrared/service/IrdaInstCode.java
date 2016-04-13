package com.lidnec.infrared.service;

import java.util.List;

/**
 * 解析指令码
 * Created by lindec on 2016/3/28.
 */
public class IrdaInstCode {

    private int sendCount;
    private int lowCount;
    private int highCount;
    private int syncCount;
    private List<List<Boolean>> lhFlag;

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getLowCount() {
        return lowCount;
    }

    public void setLowCount(int lowCount) {
        this.lowCount = lowCount;
    }

    public int getHighCount() {
        return highCount;
    }

    public void setHighCount(int highCount) {
        this.highCount = highCount;
    }

    public int getSyncCount() {
        return syncCount;
    }

    public void setSyncCount(int syncCount) {
        this.syncCount = syncCount;
    }

    public List<List<Boolean>> getLhFlag() {
        return lhFlag;
    }

    public void setLhFlag(List<List<Boolean>> lhFlag) {
        this.lhFlag = lhFlag;
    }
}
