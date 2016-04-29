package com.lidnec.infrared.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lidnec.infrared.ConversionTools;

import static java.lang.Math.abs;

/**
 * Created by lindec on 2016/3/14.
 */

public class IrCodeServiceImpl implements IrCodeService {



    @Override
    public boolean irMatching(String code) {
        return false;
    }


    @Override
    public boolean irMatching(byte[] code) {
        return false;
    }


    @Override
    public int[] adjustIrCode(int[] cyCode, int range) {
        int[] adjArray = new int[cyCode.length];
        for (int i = 0; i < cyCode.length; i++) {
            long sum = cyCode[i];
            int count = 1;
            for (int j = 0; j < cyCode.length; j++) {
                if (i != j) {
                    if (abs(cyCode[i] - cyCode[j]) < range) {
                        sum += cyCode[j];
                        count++;
                    }
                }
            }
            adjArray[i] = (int) (sum / count);
        }
        return adjArray;
    }


    @Override
    public int[] ruleCode(int[] adjCode) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(adjCode[0]);
        for (int i = 0; i < adjCode.length; i++) {
            if (!list.contains(adjCode[i])) list.add(adjCode[i]);
        }
        Integer[] ccArray = list.toArray(new Integer[list.size()]);
        Arrays.sort(ccArray);
        int[] result = new int[ccArray.length];
        for (int i = 0; i < ccArray.length; i++) {
            result[i] = ccArray[i];
        }
        return result;
    }


    @Override
    public int[] irCode2fixedCode(int[] adjCode) {
        int[] rule = ruleCode(adjCode);
        int[] fixedArray = new int[adjCode.length];
        for (int m = 0; m < adjCode.length; m++) {
            for (int n = 0; n < rule.length; n++) {
                if (adjCode[m] == rule[n]) {
                    fixedArray[m] = n + 1;
                }
            }
        }
        return fixedArray;
    }

    @Override
    public int[] fixedCode2irCode(int[] ruleCode, int[] fixedCode) {
        int[] code = new int[fixedCode.length];
        for (int i = 0; i < fixedCode.length; i++) {
            code[i] = ruleCode[fixedCode[i]];
        }
        return code;
    }


    @Override
    public boolean isElevelStartHigh(int level) {
        return (level & 0x80) != 0x80;
    }

    @Override
    public IrdaFixedCode parserFixedCode(String fixedcode) {
        IrdaFixedCode irdaFixedCode = new IrdaFixedCode();
        byte[] fixedCode = ConversionTools.Tool().hexStr2Bytes(fixedcode, true);
        int fixedLength = fixedCode.length;
        if (fixedLength != 0) {
            //引导码字节数量
            int leadCodeLength = (fixedCode[2] & 0x7f) * 2;
            byte[] leadCode = new byte[leadCodeLength];
            //有引导码
            if (leadCodeLength != 0 && leadCodeLength < fixedLength) {
                for (int i = 0; i < leadCodeLength; i++) {
                    leadCode[i] = fixedCode[3 + i];
                }
                irdaFixedCode.setLeadCode(leadCode);
            }
            irdaFixedCode.setLeadLength(leadCodeLength);
            //0码字节数量
            int lowCodeLength = (fixedCode[3 + leadCodeLength] & 0x7f) * 2;
            byte[] lowCode = new byte[lowCodeLength];
            if (lowCodeLength != 0 && lowCodeLength < fixedLength) {
                for (int i = 0; i < lowCodeLength; i++) {
                    lowCode[i] = fixedCode[leadCodeLength + 4 + i];
                }
                irdaFixedCode.setLowLength(lowCodeLength);
                irdaFixedCode.setLowCode(lowCode);
            }
            //1码字节数量
            int highCodeLength = (fixedCode[4 + leadCodeLength + lowCodeLength] & 0x7f) * 2;
            byte[] highCode = new byte[highCodeLength];
            if (highCodeLength != 0 && highCodeLength < fixedLength) {
                for (int i = 0; i < highCodeLength; i++) {
                    highCode[i] = fixedCode[leadCodeLength + lowCodeLength + 5 + i];
                }
                irdaFixedCode.setHighLength(highCodeLength);
                irdaFixedCode.setHighCode(highCode);
            }
            //停止码字节数量
            int stopCodeLength = (fixedCode[5 + leadCodeLength + lowCodeLength + highCodeLength] & 0x7f) * 2;
            byte[] stopCode = new byte[stopCodeLength];
            if (stopCodeLength != 0 && stopCodeLength < fixedLength) {
                for (int i = 0; i < stopCodeLength; i++) {
                    stopCode[i] = fixedCode[leadCodeLength + lowCodeLength + highCodeLength + 6 + i];
                }
                irdaFixedCode.setStopCode(stopCode);
            }
            irdaFixedCode.setStopLength(stopCodeLength);

            //同步码字节数量
            int syncCodeLength = (fixedCode[6 + leadCodeLength + lowCodeLength + highCodeLength + stopCodeLength] & 0x7f) * 2;
            byte[] syncCode = new byte[syncCodeLength];
            if (syncCodeLength != 0 && syncCodeLength < fixedLength) {
                for (int i = 0; i < syncCodeLength; i++) {
                    syncCode[i] = fixedCode[leadCodeLength + lowCodeLength + highCodeLength + stopCodeLength + 7 + i];
                }
                irdaFixedCode.setSyncCode(syncCode);
            }
            irdaFixedCode.setSyncLength(syncCodeLength);
        }
        return irdaFixedCode;
    }


    @Override
    public IrdaInstCode parserInstCode(String instcode) {
        IrdaInstCode irdaInstCode = new IrdaInstCode();
        byte[] instCode = ConversionTools.Tool().hexStr2Bytes(instcode, true);
        if (instCode[instCode.length - 1] == 0 && instCode[instCode.length - 2] == 0) {
            int lowCount = 0;
            int highCount = 0;
            int syncCount = 0;
            List<List<Boolean>> lists = new ArrayList<List<Boolean>>();
            int instCodeLength = 0;
            int instCodeLengthIndex = 1;
            int instCodeIndex = 3;
            for (int i = 0; i < 10; i++) {
                instCodeLength = ((int) instCode[instCodeLengthIndex + 1] << 8) + instCode[instCodeLengthIndex];
                List<Boolean> list = new ArrayList<Boolean>();
                for (int m = 0; m < instCodeLength; m++) {
                    byte code = instCode[instCodeIndex + (m / 8)];
                    if (((code >> (m % 8)) & 0x01) != 0) {
                        list.add(true);
                        highCount++;
                    } else {
                        list.add(false);
                        lowCount++;
                    }
                }
                lists.add(list);
                if ((instCodeLength % 8) != 0) {
                    instCodeLengthIndex += (instCodeLength / 8 + 1) + 2;
                    instCodeIndex += (instCodeLength / 8 + 1) + 2;
                } else {
                    instCodeLengthIndex += (instCodeLength / 8) + 2;
                    instCodeIndex += (instCodeLength / 8) + 2;
                }
                if ((instCodeLengthIndex + 2) >= instCode.length) break;
                syncCount++;
            }
            //发送次数
            irdaInstCode.setSendCount(instCode[0]);
            irdaInstCode.setLowCount(lowCount);
            irdaInstCode.setHighCount(highCount);
            irdaInstCode.setSyncCount(syncCount);
            irdaInstCode.setLhFlag(lists);
        }
        return irdaInstCode;
    }

    @Override
    public String combineIrCode(String fixedcode, String instcode) {
        String result = "";
//        IrBasicInfo irBasicInfo = irBasicInfoRepository.findByTypeId(typeId);
//        IrCode irCode = irCodeRepository.findByTypeIdAndInst(typeId, inst);
//        String instcode = irCode.getInstCode();
//        String fixedcode = irBasicInfo.getFixedCode();

        IrdaFixedCode irdaFixedCode = parserFixedCode(fixedcode);
        IrdaInstCode irdaInstCode = parserInstCode(instcode);

        byte[] instCode = ConversionTools.Tool().hexStr2Bytes(instcode, true);

        int codeLength = irdaFixedCode.getLeadLength() + irdaInstCode.getLowCount() * irdaFixedCode.getLowLength() +
                irdaInstCode.getHighCount() * irdaFixedCode.getHighLength() + irdaInstCode.getSyncCount() * irdaFixedCode.getSyncLength()
                + irdaFixedCode.getStopLength();
        byte[] resultCode = new byte[codeLength];
//        resultCode[0] = instCode[0];
//        resultCode[1] = (byte) (codeLength / 2);
//        resultCode[2] = (byte) ((codeLength / 2) >> 8);

        if (irdaFixedCode.getLeadLength() != 0) {
            for (int i = 0; i < irdaFixedCode.getLeadLength(); i++) {
                resultCode[i] = irdaFixedCode.getLeadCode()[i];
            }
        }

        int syncCount = irdaInstCode.getSyncCount();
        List<List<Boolean>> lists = irdaInstCode.getLhFlag();
        int lhIndex = irdaFixedCode.getLeadLength();

        for (int m = 0; m < lists.size(); m++) {
            List<Boolean> list = lists.get(m);
            for (int n = 0; n < list.size(); n++) {
                if (list.get(n)) {
                    for (byte highcode : irdaFixedCode.getHighCode()) {
                        resultCode[lhIndex] = highcode;
                        lhIndex++;
                    }
                } else {
                    for (byte lowcode : irdaFixedCode.getLowCode()) {
                        resultCode[lhIndex] = lowcode;
                        lhIndex++;
                    }
                }
            }
            if (syncCount > 0) {
                for (int nn = 0; nn < irdaFixedCode.getSyncLength(); nn++) {
                    resultCode[lhIndex] = irdaFixedCode.getSyncCode()[nn];
                    lhIndex++;
                }
                syncCount--;
            }
        }
        if (irdaFixedCode.getStopLength() != 0) {
            for (int nn = 0; nn < irdaFixedCode.getStopLength(); nn++) {
                resultCode[lhIndex] = irdaFixedCode.getStopCode()[nn];
                lhIndex++;
            }
        }
        for(int i = 0 ;i< irdaInstCode.getSendCount();i++){
            result = result + ConversionTools.Tool().bytes2HexStr(resultCode, false);
        }
        return result;
    }
}
