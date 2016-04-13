package com.lidnec.infrared.service;



import java.util.List;

/**
 * Created by lindec on 2016/3/14.
 */
public interface IrCodeService {

    /**
     * 红外匹配
     *
     * @param cyCode 采样红外码
     * @return 返回红外编码字段
     */
    boolean irMatching(String cyCode);


    boolean irMatching(byte[] cyCode);

    /**
     * 红外编码规整
     * 如：range = 100 ，则两数相近在100范围内进行规整 ｛500,504,300｝-> ｛502,502,300｝
     *
     * @param cyCode 红外采样码
     * @param range  比较范围
     * @return 返回规整后的红外编码
     */
    int[] adjustIrCode(int[] cyCode, int range);

    /**
     * 红外编码规整后的特征码
     *
     * @param adjCode 规整后的红外编码
     * @return 特征码
     */
    int[] ruleCode(int[] adjCode);

    /**
     * 固定码
     *
     * @param adjCode 规整后的红外编码
     * @return 固定码
     */
    int[] irCode2fixedCode(int[] adjCode);

    /**
     * 红外码编码
     *
     * @param ruleCode  规整后的红外编码特征值
     * @param fixedCode 固定码
     * @return 红外码
     */
    int[] fixedCode2irCode(int[] ruleCode, int[] fixedCode);

    /**
     * 判断电平是否从高开始
     *
     * @param level 电平起始标志
     * @return true：开始为高电平 false：开始为低电平
     */
    boolean isElevelStartHigh(int level);

    /**
     * 解析固定码
     *
     * @param fixedcode 固定码
     * @return 固定码解析内容
     */
    IrdaFixedCode parserFixedCode(String fixedcode);

    /**
     * 解析变化码
     *
     * @param instcode 变化码
     * @return 变化码解析内容
     */
    IrdaInstCode parserInstCode(String instcode);

    /**
     * 固定码和指令码换算成红外编码
     *
     * @param fiedCode 固定码
     * @param instCode 指令码
     * @return 返回红外码
     */
    String combineIrCode(String fiedCode, String instCode);


}
