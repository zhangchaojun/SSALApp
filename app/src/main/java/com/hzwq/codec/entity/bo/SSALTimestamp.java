package com.hzwq.codec.entity.bo;

import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;

public class SSALTimestamp {
    private String timestampHex;
    private String timestampStr;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;

    public SSALTimestamp() {
    }

    public static SSALTimestamp giveMeOne() {
        SSALTimestamp instance = new SSALTimestamp();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        instance.setYear(StringUtils.leftPad(String.valueOf(year), 4, '0'));
        instance.setMonth(StringUtils.leftPad(String.valueOf(month), 2, '0'));
        instance.setDay(StringUtils.leftPad(String.valueOf(day), 2, '0'));
        instance.setHour(StringUtils.leftPad(String.valueOf(hour), 2, '0'));
        instance.setMinute(StringUtils.leftPad(String.valueOf(minute), 2, '0'));
        instance.setSecond(StringUtils.leftPad(String.valueOf(second), 2, '0'));
        instance.setTimestampStr(
                instance.getYear()
            + instance.getMonth()
            + instance.getDay()
            + instance.getHour()
            + instance.getMinute()
            + instance.getSecond());

        instance.setTimestampHex(
                StringUtils.leftPad(ByteTools.int2HexStr(year), 4, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(month), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(day), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(hour), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(minute), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(second), 2, '0')
        );
        return instance;
    }

//            6.4.8 时间标签TP
//            时间标签TP，标识报文的产生时间，由报文生成者填写，由7个字节组成。
    public SSALTimestamp(String timestampHex) {
        this.setTimestampHex(timestampHex);
        int yearInt = ByteTools.hexStr2ByteArr(timestampHex.substring(0,2))[0] & 0xFF;
        int yearInt2 = ByteTools.hexStr2ByteArr(timestampHex.substring(2,4))[0] & 0xFF;
        byte [] byteArr4Year = new byte[]{0x00,0x00, (byte)yearInt, (byte)yearInt2};
        int yearIntTmp = ByteTools.byteArr2Int(byteArr4Year);
        String year = StringUtils.leftPad(yearIntTmp + "",2, '0');

        int monthInt = ByteTools.hexStr2ByteArr(timestampHex.substring(4,6))[0] & 0xFF;
        String month = StringUtils.leftPad(monthInt + "",2, '0');

        int dayInt = ByteTools.hexStr2ByteArr(timestampHex.substring(6,8))[0] & 0xFF;
        String day = StringUtils.leftPad(dayInt+"",2, '0');

        int hourInt = ByteTools.hexStr2ByteArr(timestampHex.substring(8,10))[0] & 0xFF;
        String hour = StringUtils.leftPad(hourInt+"",2, '0');

        int minuteInt = ByteTools.hexStr2ByteArr(timestampHex.substring(10,12))[0] & 0xFF;
        String minute = StringUtils.leftPad(minuteInt+"",2, '0');

        int secondInt = ByteTools.hexStr2ByteArr(timestampHex.substring(12,14))[0] & 0xFF;
        String second = StringUtils.leftPad(secondInt+"",2, '0');

        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
        this.setHour(hour);
        this.setMinute(minute);
        this.setSecond(second);
        this.setTimestampStr(year + month + day + hour + minute + second);
    }

    public String getTimestampHex() {
        return timestampHex;
    }

    public void setTimestampHex(String timestampHex) {
        this.timestampHex = timestampHex;
    }

    public String getTimestampStr() {
        return timestampStr;
    }

    public void setTimestampStr(String timestampStr) {
        this.timestampStr = timestampStr;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "SSALTimestamp{" +
                "timestampHex='" + timestampHex + '\'' +
                ", timestampStr='" + timestampStr + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}
