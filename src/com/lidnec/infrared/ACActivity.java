package com.lidnec.infrared;

import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ACActivity extends Activity implements OnClickListener {
	private TextView mScreen_Model;
	private TextView mScreen_Degree;
	private TextView mScreen_WindDirection_Auto;
	private TextView mScreen_WindSpeed;
	private TextView mScreen_WindDirection;
	private TextView mDragreeTextView;
	private TextView mModelBt;
	private TextView mPowerBt;
	private TextView mWindSpeedBt;
	private TextView mHeatModelBt;
	private TextView mCoolModelBt;
	private TextView mTempUpBt;
	private TextView mTempDownBt;
	private TextView mWindDirectBt;
	private View mScreen_ACScreenLine;
	private View mScreen_DragreeView;
	// private KKACManagerV2 mKKACManager = new KKACManagerV2();
	private int direction;
	private TextView mWindSweepBt;

	private TextView mAutoModelBt, mDryModelBt, mFanModelBt;

	private TextView mTemp10Bt, mTemp27Bt, mTemp20Bt;

	private TextView mSpeedLowBt, mSpeedMediaBt, mSpeedHighBt, mSpeedAutoBt;

	private AcStatus acStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ac);
		acStatus = new AcStatus(IRConstants.AC_POWER_ON, IRConstants.AC_MODE_AUTO, 17, IRConstants.AC_WIND_SPEED_HIGH,
				IRConstants.AC_WIND_DIRECT_AUTO);
				// screen
				/** 模式 **/
		mScreen_Model = (TextView) findViewById(R.id.ac_state_model);
		/** 温度 **/
		mScreen_Degree = (TextView) findViewById(R.id.ac_state_degree);
		/** 扫风风向 **/
		mScreen_WindDirection_Auto = (TextView) findViewById(R.id.ac_state_sweep_wind);
		/** 风向 **/
		mScreen_WindDirection = (TextView) findViewById(R.id.ac_state_put_wind);

		/** 风量 **/
		mScreen_WindSpeed = (TextView) findViewById(R.id.ac_state_wind_speed);
		/** 温度面板 **/
		mScreen_DragreeView = findViewById(R.id.ac_dragree_view);
		/** 横线 **/
		mScreen_ACScreenLine = findViewById(R.id.ac_screen_line);

		// 电源
		mPowerBt = (TextView) findViewById(R.id.ac_command_power);
		// 模式
		mModelBt = (TextView) findViewById(R.id.ac_command_model);
		mHeatModelBt = (TextView) findViewById(R.id.ac_command_warm);
		mCoolModelBt = (TextView) findViewById(R.id.ac_command_cold);

		mAutoModelBt = (TextView) findViewById(R.id.ac_command_auto);
		mFanModelBt = (TextView) findViewById(R.id.ac_command_fan);
		mDryModelBt = (TextView) findViewById(R.id.ac_command_dry);

		mTemp10Bt = (TextView) findViewById(R.id.ac_command_17);
		mTemp20Bt = (TextView) findViewById(R.id.ac_command_20);
		mTemp27Bt = (TextView) findViewById(R.id.ac_command_27);

		mSpeedLowBt = (TextView) findViewById(R.id.ac_command_low);
		mSpeedMediaBt = (TextView) findViewById(R.id.ac_command_media);
		mSpeedHighBt = (TextView) findViewById(R.id.ac_command_high);
		mSpeedAutoBt = (TextView) findViewById(R.id.ac_command_auto_speed);

		// 温度
		mTempUpBt = (TextView) findViewById(R.id.ac_command_heat_up);
		mTempDownBt = (TextView) findViewById(R.id.ac_command_heat_down);
		// 风速
		mWindSpeedBt = (TextView) findViewById(R.id.ac_command_wind_speed);
		mWindDirectBt = (TextView) findViewById(R.id.ac_command_wind_direct);
		mWindSweepBt = (TextView) findViewById(R.id.ac_command_sweep_wind);
		initData();
	}

	private void initData() {
		// 从云端获取
		// 初始化空调状态
		initACPannel();
	}

	private void initACPannel() {
		updatePowerScreen();
		updateModes();
		updateWindDirect();
	}

	private void updatePowerScreen() {
		int power = acStatus.getPower();
		if (power == IRConstants.AC_POWER_OFF) {
			mScreen_Model.setVisibility(View.INVISIBLE);
			mScreen_Degree.setVisibility(View.INVISIBLE);
			mScreen_WindDirection_Auto.setVisibility(View.INVISIBLE);
			mScreen_WindSpeed.setVisibility(View.INVISIBLE);
			mScreen_WindDirection.setVisibility(View.INVISIBLE);
			mScreen_ACScreenLine.setVisibility(View.INVISIBLE);
			mScreen_DragreeView.setVisibility(View.INVISIBLE);

		} else {
			mScreen_Model.setVisibility(View.VISIBLE);
			mScreen_Degree.setVisibility(View.VISIBLE);
			mScreen_ACScreenLine.setVisibility(View.VISIBLE);
			mScreen_DragreeView.setVisibility(View.VISIBLE);
			mScreen_WindDirection_Auto.setVisibility(View.VISIBLE);
			updateTemptures();
			// 这两个需要做特殊处理,在开机的时候也可能不显示!
			updateWindSpeed();// 这俩需要判断power
			updateWindDirect();
		}

	}

	private void updateModes() {
		// 模式处理
		int modeState = acStatus.getMode();
		switch (modeState) {
		case IRConstants.AC_MODE_AUTO:
			mScreen_Model.setText("自动");
			break;
		case IRConstants.AC_MODE_COOL:
			mScreen_Model.setText("制冷");
			break;
		case IRConstants.AC_MODE_HEAT:
			mScreen_Model.setText("制热");
			break;
		case IRConstants.AC_MODE_FAN:
			mScreen_Model.setText("送风");
			break;
		case IRConstants.AC_MODE_DRY:
			mScreen_Model.setText("除湿");
			break;
		}
		updateTemptures();

	}

	private void updateTemptures() {
		// 温度显示
		// TODO:
		int mode = acStatus.getMode();
		if (mode == IRConstants.AC_MODE_COOL || mode == IRConstants.AC_MODE_HEAT) {
			int temperature = acStatus.getTemperature();
			mScreen_Degree.setText(temperature + "");
			mTempUpBt.setEnabled(true);
			mTempDownBt.setEnabled(true);
		} else {
			mScreen_Degree.setText("NA");
			mTempUpBt.setEnabled(true);
			mTempDownBt.setEnabled(true);
			mTempUpBt.setEnabled(false);
			mTempDownBt.setEnabled(false);
		}
	}

	/* 风速显示处理 */
	private void updateWindSpeed() {
		int power = acStatus.getPower();
		if (power == IRConstants.AC_POWER_ON) {
			mScreen_WindSpeed.setVisibility(View.VISIBLE);
			int speed = acStatus.getWindSpeed();
			switch (speed) {
			case IRConstants.AC_WIND_SPEED_AUTO:
				mScreen_WindSpeed.setText("自动风量");
				break;
			case IRConstants.AC_WIND_SPEED_HIGH:
				mScreen_WindSpeed.setText("高");
				break;
			case IRConstants.AC_WIND_SPEED_LOW:
				mScreen_WindSpeed.setText("低");
				break;
			case IRConstants.AC_WIND_SPEED_MEDIUM:
				mScreen_WindSpeed.setText("中");
				break;
			}
		} else {
			mScreen_WindSpeed.setVisibility(View.INVISIBLE);
		}

	}

	/* 扫风显示处理 */
	private void updateWindDirect() {
		int power = acStatus.getPower();
		if (power == IRConstants.AC_POWER_ON) {
			int directType = acStatus.getWindDirect();
			switch (directType) {
			case IRConstants.AC_WIND_DIRECT_MANUAL:
				mScreen_WindDirection_Auto.setText("手动风向");
				break;
			case IRConstants.AC_WIND_DIRECT_AUTO:
				// 如果是自动,就不要显示 上 中 下 屏幕
				mScreen_WindDirection_Auto.setText("自动风向");
				mScreen_WindDirection.setVisibility(View.INVISIBLE);
				break;
			case IRConstants.AC_WIND_DIRECT_HIGH:
				mScreen_WindDirection.setVisibility(View.VISIBLE);
				mScreen_WindDirection_Auto.setText("手动风向");
				mScreen_WindDirection.setText("上");
				break;
			case IRConstants.AC_WIND_DIRECT_LOW:
				mScreen_WindDirection.setVisibility(View.VISIBLE);
				mScreen_WindDirection_Auto.setText("手动风向");
				mScreen_WindDirection.setText("下");
				break;
			case IRConstants.AC_WIND_DIRECT_MEDIUM:
				mScreen_WindDirection.setVisibility(View.VISIBLE);
				mScreen_WindDirection_Auto.setText("手动风向");
				mScreen_WindDirection.setText("中");
				break;
			}
		} else {
			mScreen_WindDirection_Auto.setVisibility(View.INVISIBLE);
			mScreen_WindDirection.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		// 按了其他按键,把空调的开关状态置为true
		// if (v.getId() != R.id.ac_command_power &&
		// mKKACManager.getPowerState() == IRConstants.AC_POWER_OFF) {
		// mKKACManager.changePowerState();
		// updatePowerScreen();
		// }

		switch (v.getId()) {
		case R.id.ac_command_power: // 开关
			if (acStatus.getPower() == IRConstants.AC_POWER_ON)
				acStatus.setPower(IRConstants.AC_POWER_OFF);
			else if (acStatus.getPower() == IRConstants.AC_POWER_OFF)
				acStatus.setPower(IRConstants.AC_POWER_ON);
			updatePowerScreen();
			break;
		case R.id.ac_command_model:// 模式
			if (acStatus.getMode() == IRConstants.AC_MODE_AUTO)
				acStatus.setMode(IRConstants.AC_MODE_COOL);
			else if (acStatus.getMode() == IRConstants.AC_MODE_COOL)
				acStatus.setMode(IRConstants.AC_MODE_HEAT);
			else if (acStatus.getMode() == IRConstants.AC_MODE_HEAT)
				acStatus.setMode(IRConstants.AC_MODE_FAN);
			else if (acStatus.getMode() == IRConstants.AC_MODE_FAN)
				acStatus.setMode(IRConstants.AC_MODE_DRY);
			else if (acStatus.getMode() == IRConstants.AC_MODE_DRY)
				acStatus.setMode(IRConstants.AC_MODE_AUTO);
			updateModes();
			updateTemptures();
			updateWindSpeed();
			break;

		case R.id.ac_command_warm:// 制热
			acStatus.setMode(IRConstants.AC_MODE_HEAT);
			updateModes();
			break;
		case R.id.ac_command_cold:// 制冷
			acStatus.setMode(IRConstants.AC_MODE_COOL);
			updateModes();
			break;
		case R.id.ac_command_auto: //
			acStatus.setMode(IRConstants.AC_MODE_AUTO);
			updateModes();
			break;
		case R.id.ac_command_dry:
			acStatus.setMode(IRConstants.AC_MODE_DRY);
			updateModes();
			break;
		case R.id.ac_command_fan:
			acStatus.setMode(IRConstants.AC_MODE_FAN);
			updateModes();
			break;

		case R.id.ac_command_wind_speed:// 风量
			if (acStatus.getWindSpeed() == IRConstants.AC_WIND_SPEED_AUTO)
				acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_LOW);
			else if (acStatus.getWindSpeed() == IRConstants.AC_WIND_SPEED_LOW)
				acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_MEDIUM);
			else if (acStatus.getWindSpeed() == IRConstants.AC_WIND_SPEED_MEDIUM)
				acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_HIGH);
			else if (acStatus.getWindSpeed() == IRConstants.AC_WIND_SPEED_HIGH)
				acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_AUTO);
			updateWindSpeed();
			break;

		case R.id.ac_command_low:// 设置风速模式，如果返回false，有两种结果：1该模式下风速不可调(可先调用此方法判断mKKACManager.getCurrentACModel().isWindSpeedCanControl())，2该模式下不具备要设置的风速模式
			acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_LOW);
			updateWindSpeed();
			break;
		case R.id.ac_command_media:
			acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_MEDIUM);
			updateWindSpeed();
			break;
		case R.id.ac_command_high:
			acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_HIGH);
			updateWindSpeed();
			break;
		case R.id.ac_command_auto_speed:
			acStatus.setWindSpeed(IRConstants.AC_WIND_SPEED_AUTO);
			updateWindSpeed();
			break;

		case R.id.ac_command_wind_direct:// 风向
			if (acStatus.getWindDirect() == IRConstants.AC_WIND_DIRECT_MANUAL)
				acStatus.setWindDirect(IRConstants.AC_WIND_DIRECT_LOW);
			else if (acStatus.getWindDirect() == IRConstants.AC_WIND_DIRECT_LOW)
				acStatus.setWindDirect(IRConstants.AC_WIND_DIRECT_MEDIUM);
			else if (acStatus.getWindDirect() == IRConstants.AC_WIND_DIRECT_MEDIUM)
				acStatus.setWindDirect(IRConstants.AC_WIND_DIRECT_HIGH);
			else if (acStatus.getWindDirect() == IRConstants.AC_WIND_DIRECT_HIGH)
				acStatus.setWindDirect(IRConstants.AC_WIND_DIRECT_LOW);
			updateWindDirect();

			break;
		case R.id.ac_command_sweep_wind:// 扫风
			if (acStatus.getWindDirect() == IRConstants.AC_WIND_DIRECT_AUTO)
				acStatus.setWindDirect(IRConstants.AC_WIND_DIRECT_MANUAL);
			else
				acStatus.setWindDirect(IRConstants.AC_WIND_DIRECT_AUTO);
			updateWindDirect();

			break;

		case R.id.ac_command_heat_up:// 温度+
			// mKKACManager.getCurrentACModel().increaseTmp();
			if (acStatus.getTemperature() >= 30)
				acStatus.setTemperature(30);
			else
				acStatus.setTemperature(acStatus.getTemperature() + 1);
			updateTemptures();

			break;
		case R.id.ac_command_heat_down:// 温度-
			if (acStatus.getTemperature() <= 16)
				acStatus.setTemperature(16);
			else
				acStatus.setTemperature(acStatus.getTemperature() - 1);
			updateTemptures();
			break;

		case R.id.ac_command_17:// 设置指定的温度，返回false两种结果:1该模式下温度不可增减 (可先调用此方法判断
								// mKKACManager.getCurrentACModel().isTempCanControl())2设置的温度不在空调的可控温度范围内
			acStatus.setTemperature(17);
			updateTemptures();
			// setTemperature(17);
			break;
		case R.id.ac_command_20:
			acStatus.setTemperature(20);
			updateTemptures();
			// setTemperature(20);
			break;
		case R.id.ac_command_27:
			acStatus.setTemperature(27);
			updateTemptures();
			// setTemperature(27);
			break;

		}
		// Logger.d("IRCode is " +
		// mKKACManager.getACIRPattern());//这是manager解码为int数组之后, 拼接起来的字符串
		// int[] patternsInArray =
		// mKKACManager.getACIRPatternIntArray();//这些码可以直接给ConsumerIR发送出去
		// Logger.d("IRCode array is " + Arrays.toString(patternsInArray));
	}

	// private void setModel(int modelType) {
	// // result:-1代表不具备该模式，1模式切换成功
	// // TODO:要改
	// // if (mKKACManager.getAcStateV2().changeToTargetModel(modelType) == 1)
	//
	// {
	// updateModes();
	// updateTemptures();
	// updateWindSpeed();
	// }
	// }

	// private void setTemperature(int temperature) {
	// // int result =
	// // mKKACManager.getCurrentACModel().setTemperature(temperature);
	// // TODO: 取值要改
	// int result = 0;
	// // int 0 温度不可以调节 -1 温度不在控制范围内, 1 设置温度成功
	// if (result == 1) {
	// updateTemptures();
	// } else if (result == 0) {
	// Toast.makeText(this, "该模式下，温度不能调节", Toast.LENGTH_SHORT).show();
	//
	// } else if (result == -1) {
	// Toast.makeText(this, "设置的温度不在空调的可控范围内", Toast.LENGTH_SHORT).show();
	//
	// }
	// }

	// private void setWindSpeed(byte acWindSpeedMedium) {
	// // int result =
	// //
	// mKKACManager.getCurrentACModel().setTargetWindSpeed(acWindSpeedMedium);
	// // TODO: 取值要改
	// int result = 0;
	//
	// if (result == 1) {
	// updateWindSpeed();
	// }
	// if (result == 0) {
	// Toast.makeText(this, "该模式下， 风速不能调节", Toast.LENGTH_SHORT).show();
	//
	// }
	//
	// if (result == -1) {
	// Toast.makeText(this, "该模式下不具备此风速", Toast.LENGTH_SHORT).show();
	// }
	//
	// }

	@Override
	protected void onResume() {
		// mKKACManager.onResume();
		super.onResume();
	};

	@Override
	protected void onPause() {
		// mKKACManager.onPause();
		super.onPause();
	}

	@Override
	protected void onStop() {
		// 获取当前AC状态的字符串
		// String acState = mKKACManager.getACStateV2InString();
		// Logger.d("Save AC state: " + acState);
		// DataStoreUtil.i().putString("ACV2", acState);
		super.onStop();
	}

}
