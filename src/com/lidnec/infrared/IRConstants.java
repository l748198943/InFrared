package com.lidnec.infrared;

/**
 * 红外常量 Created by lindec on 2016/2/22.
 */
public final class IRConstants {

	/**
	 * 红外码库服务器地址
	 */
	public static final String URL = "http://123.59.74.10:8086/third-party-service/irdainfo";
	/**
	 * 令牌号
	 */
	public static final String TOKEN = "";
	
	

	public static final int AC_POWER = 1000;
	public static final int AC_MODE = 2000;
	public static final int AC_TEMPERATURE = 3000;
	public static final int AC_WIND_SPEED = 4000;
	public static final int AC_WIND_DIRECT = 5000;

	// 电源开关
	public static final int AC_POWER_ON = AC_POWER + 1;
	public static final int AC_POWER_OFF = AC_POWER + 2;

	// 模式
	public static final int AC_MODE_AUTO = AC_MODE + 1;
	public static final int AC_MODE_COOL = AC_MODE + 2;
	public static final int AC_MODE_HEAT = AC_MODE + 3;
	public static final int AC_MODE_FAN = AC_MODE + 4;
	public static final int AC_MODE_DRY = AC_MODE + 5;

	// 风速
	public static final int AC_WIND_SPEED_AUTO = AC_WIND_SPEED + 1;
	public static final int AC_WIND_SPEED_HIGH = AC_WIND_SPEED + 2;
	public static final int AC_WIND_SPEED_LOW = AC_WIND_SPEED + 3;
	public static final int AC_WIND_SPEED_MEDIUM = AC_WIND_SPEED + 4;

	// 风向
	public static final int AC_WIND_DIRECT_MANUAL = AC_WIND_DIRECT + 1;
	public static final int AC_WIND_DIRECT_AUTO = AC_WIND_DIRECT + 2;
	public static final int AC_WIND_DIRECT_HIGH = AC_WIND_DIRECT + 3;
	public static final int AC_WIND_DIRECT_LOW = AC_WIND_DIRECT + 4;
	public static final int AC_WIND_DIRECT_MEDIUM = AC_WIND_DIRECT + 5;

}
