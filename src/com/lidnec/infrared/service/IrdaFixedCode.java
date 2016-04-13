package com.lidnec.infrared.service;

/**
 * 解析固定码
 * Created by lindec on 2016/3/28.
 */
public class IrdaFixedCode {
    /**
     * 波特率
     */
    private String bcchno;
    /**
     * 占空比
     */
    private String dutyfactor;
    /**
     * 引导码起始电平高低 true：高 ，false：低
     */
    private boolean isLeadStartHigh;
    /**
     * 引导码长度
     */
    private int leadLength;
    /**
     * 引导码
     */
    private byte[] leadCode;
    /**
     * 0 格式起始电平高低 true：高 ，false：低
     */
    private boolean isLowStartHigh;
    /**
     * 0 格式码长度
     */
    private int lowLength;
    /**
     * 0 格式码
     */
    private byte[] lowCode;

    /**
     * 1 格式起始电平高低 true：高 ，false：低
     */
    private boolean isHighStartHigh;
    /**
     * 1 格式码长度
     */
    private int highLength;
    /**
     * 1 格式码
     */
    private byte[] highCode;

    /**
     * 停止码起始电平高低 true：高 ，false：低
     */
    private boolean isStopStartHigh;
    /**
     * 停止码长度
     */
    private int stopLength;
    /**
     * 停止码
     */
    private byte[] stopCode;

    /**
     * 同步码起始电平高低 true：高 ，false：低
     */
    private boolean isSyncStartHigh;
    /**
     * 同步码长度
     */
    private int syncLength;
    /**
     * 同步码
     */
    private byte[] syncCode;

    public String getBcchno() {
        return bcchno;
    }

    public void setBcchno(String bcchno) {
        this.bcchno = bcchno;
    }

    public String getDutyfactor() {
        return dutyfactor;
    }

    public void setDutyfactor(String dutyfactor) {
        this.dutyfactor = dutyfactor;
    }

    public boolean isLeadStartHigh() {
        return isLeadStartHigh;
    }

    public void setLeadStartHigh(boolean leadStartHigh) {
        isLeadStartHigh = leadStartHigh;
    }

    public int getLeadLength() {
        return leadLength;
    }

    public void setLeadLength(int leadLength) {
        this.leadLength = leadLength;
    }

    public byte[] getLeadCode() {
        return leadCode;
    }

    public void setLeadCode(byte[] leadCode) {
        this.leadCode = leadCode;
    }

    public boolean isLowStartHigh() {
        return isLowStartHigh;
    }

    public void setLowStartHigh(boolean lowStartHigh) {
        isLowStartHigh = lowStartHigh;
    }

    public int getLowLength() {
        return lowLength;
    }

    public void setLowLength(int lowLength) {
        this.lowLength = lowLength;
    }

    public byte[] getLowCode() {
        return lowCode;
    }

    public void setLowCode(byte[] lowCode) {
        this.lowCode = lowCode;
    }

    public boolean isHighStartHigh() {
        return isHighStartHigh;
    }

    public void setHighStartHigh(boolean highStartHigh) {
        isHighStartHigh = highStartHigh;
    }

    public int getHighLength() {
        return highLength;
    }

    public void setHighLength(int highLength) {
        this.highLength = highLength;
    }

    public byte[] getHighCode() {
        return highCode;
    }

    public void setHighCode(byte[] highCode) {
        this.highCode = highCode;
    }

    public boolean isStopStartHigh() {
        return isStopStartHigh;
    }

    public void setStopStartHigh(boolean stopStartHigh) {
        isStopStartHigh = stopStartHigh;
    }

    public int getStopLength() {
        return stopLength;
    }

    public void setStopLength(int stopLength) {
        this.stopLength = stopLength;
    }

    public byte[] getStopCode() {
        return stopCode;
    }

    public void setStopCode(byte[] stopCode) {
        this.stopCode = stopCode;
    }

    public boolean isSyncStartHigh() {
        return isSyncStartHigh;
    }

    public void setSyncStartHigh(boolean syncStartHigh) {
        isSyncStartHigh = syncStartHigh;
    }

    public int getSyncLength() {
        return syncLength;
    }

    public void setSyncLength(int syncLength) {
        this.syncLength = syncLength;
    }

    public byte[] getSyncCode() {
        return syncCode;
    }

    public void setSyncCode(byte[] syncCode) {
        this.syncCode = syncCode;
    }
}
