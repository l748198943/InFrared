package com.lidnec.infrared;

import java.util.HashMap;
import java.util.Map;

import com.example.httppost.JsonPost;
import com.lidnec.infrared.service.IrCodeService;
import com.lidnec.infrared.service.IrCodeServiceImpl;
import com.lidnec.infrared.service.IrdaFixedCode;
import com.lidnec.infrared.service.IrdaInstCode;
import com.lindec.androidsqlite.InfraredDB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button but_check;
	private Button but_send;
	private Button but_pp;
	private Button but_getInst;
	private Button but_ac;
	private ConsumerIrManager ir;
	
    private IrCodeService irCodeService; 
    
   // private InfraredDB infraredDB;


	private static final int SAMPLE_FREQ = 38400;
	private static final int[] IR_SIGNAL_PULSE_COUNT = { 171, 171, 22, 64, 22, 64, 22, 64, 22, 21, 22, 21, 22, 21, 22,
			21, 22, 21, 22, 64, 22, 64, 22, 64, 22, 21, 22, 21, 22, 21, 22, 21, 22, 21, 22, 21, 22, 21, 22, 64, 22, 21,
			22, 21, 22, 21, 22, 21, 22, 21, 22, 64, 22, 64, 22, 21, 22, 64, 22, 64, 22, 64, 22, 64, 22, 64, 22, 876 };

	private static final int[] IR_SIGNAL_TIME_LENGTH = { 4499, 4499, 578, 1683, 578, 1683, 578, 1683, 578, 552, 578,
			552, 578, 552, 578, 552, 578, 552, 578, 1683, 578, 1683, 578, 1683, 578, 552, 578, 552, 578, 552, 578, 552,
			578, 552, 578, 552, 578, 552, 578, 1683, 578, 552, 578, 552, 578, 552, 578, 552, 578, 552, 578, 1683, 578,
			1683, 578, 552, 578, 1683, 578, 1683, 578, 1683, 578, 1683, 578, 1683, 578, 23047 };

	// private static final int[] rawCodes_ac_open[100] =
	// {3602,4400,4450,550,1600,550,550,550,1650,500,1650,550,550,550,550,500,1650,550,550,550,550,500,1650,550,550,550,550,500,1650,550,1650,500,550,550,1650,550,550,500,1650,550,1650,500,1650,550,1650,500,550,550,1650,550,1600,550,1650,550,550,550,500,550,550,550,550,550,1600,550,550,550,550,550,1600,550,1650,550,1600,550,550,550,550,550,550,550,500,550,550,550,550,550,550,550,500,550,1650,550,1600,550,1650,550,1600,550,1650,550};
	private static final int[] rawCodes_ac_open = { 4397, 4368, 561, 1578, 560, 537, 557, 1581, 584, 1582, 584, 486,
			558, 537, 584, 1554, 560, 538, 558, 510, 558, 1607, 557, 513, 585, 511, 556, 1585, 581, 1583, 583, 486, 557,
			1609, 556, 513, 558, 538, 557, 512, 558, 1607, 558, 1583, 557, 1608, 558, 1608, 557, 1580, 559, 1606, 559,
			1581, 556, 1609, 557, 512, 559, 537, 558, 512, 557, 539, 557, 512, 558, 1607, 558, 1582, 558, 537, 584,
			1555, 558, 1607, 556, 514, 559, 537, 556, 513, 558, 537, 556, 514, 558, 1606, 559, 511, 583, 513, 558, 1582,
			557, 1608, 558, 1580, 585, 5154, 4419, 4347, 584, 1555, 558, 539, 557, 1581, 584, 1581, 571, 501, 577, 516,
			571, 1568, 557, 541, 581, 487, 557, 1608, 557, 513, 558, 538, 557, 1582, 583, 1582, 584, 487, 557, 1608,
			557, 511, 611, 486, 558, 510, 558, 1607, 557, 1609, 556, 1583, 583, 1582, 583, 1555, 558, 1607, 558, 1607,
			559, 1582, 557, 537, 585, 486, 558, 510, 558, 540, 558, 509, 557, 1608, 558, 1608, 557, 512, 559, 1606, 559,
			1581, 558, 537, 581, 490, 558, 510, 587, 511, 557, 511, 559, 1606, 559, 510, 569, 527, 558, 1582, 560, 1604,
			561, 1605, 561 };
	private static final int[] rawCodes_ac_close = { 25524, 4400, 4400, 550, 1650, 550, 550, 550, 1600, 550, 1650, 550,
			500, 550, 550, 550, 1650, 550, 500, 550, 550, 550, 1650, 550, 500, 550, 550, 550, 1650, 550, 1600, 550, 550,
			550, 1600, 550, 1650, 550, 550, 550, 1600, 550, 1650, 550, 1600, 550, 1650, 550, 1600, 550, 1650, 550, 550,
			500, 1650, 550, 550, 550, 550, 500, 550, 550, 550, 550, 550, 550, 550, 500, 550, 550, 1650, 550, 550, 500,
			550, 550, 550, 550, 550, 550, 550, 500, 550, 550, 1650, 550, 550, 500, 1650, 550, 1650, 500, 1650, 550,
			1650, 500, 1650, 550, 1650, 550 };

	private static String testCode = "560edc05940224049402240494024a0194024a0194024a019402240494024a0194024a01940224049402240494024a019402240494024a"
			+ "0194024a01940224049402240494024a01940224049402240494024a0194024a019402240494024a0194024a019402240494024a0194024a0194024a0194024a"
			+ "0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a019402240494024a0194024a"
			+ "019402240494024a0194024a01940224049402240494024a0194024a0194024a0194024a0194024a0194024a019402240494024a019402240494024a0194024a"
			+ "0194024a0194024a0194024a0194024a0194024a0194024a01940224049402240494024a01940224049402240494024a0194024a0194024a0194024a0194024a"
			+ "0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a"
			+ "0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a0194024a019402240494024a0194024a0194022404940224"
			+ "0494024a0194024a0194024a0194024a019402204e";
	
	private static String glCode = "9411fe1046022c064602460246022c0646022c06460246024602460246022c06460246024602460246022c06460246024602460246022c0646022c064602460246022c064602460246024602460246024602460246022c0646022c0646022c0646022c0646022c0646022c0646022c0646022c064602460246024602460246024602460246022c0646022c0646022c06460246024602460246024602460246024602460246024602460246024602460246022c0646022c0646022c0646022c0646022c0646028813";

	private static int[] param = { 3742, 1482, 721, 1064, 704, 1059, 704, 335, 700, 335, 700, 335, 700, 1064, 700, 335,
			699, 335, 699, 1063, 704, 1063, 700, 335, 700, 1063, 700, 335, 700, 335, 700, 1064, 704, 1059, 704, 335,
			700, 1064, 700, 1064, 700, 335, 700, 335, 700, 1063, 704, 330, 704, 330, 704, 1064, 700, 335, 700, 335, 700,
			335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 334, 700, 335, 700, 335, 700, 335, 700, 335,
			700, 335, 700, 335, 700, 335, 700, 335, 700, 1063, 700, 335, 700, 335, 699, 1063, 705, 330, 704, 331, 704,
			1060, 704, 1064, 700, 335, 700, 335, 700, 339, 700, 335, 700, 334, 700, 335, 700, 1063, 700, 335, 700, 1064,
			704, 330, 700, 335, 700, 339, 704, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1064, 704, 1059, 704,
			335, 700, 1063, 700, 1063, 704, 331, 704, 330, 704, 330, 704, 331, 705, 330, 700, 335, 700, 335, 700, 335,
			700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 699, 335, 700, 335, 700,
			330, 704, 331, 704, 330, 704, 331, 704, 331, 704, 330, 704, 331, 704, 335, 700, 335, 700, 335, 700, 335,
			700, 335, 700, 335, 700, 335, 700, 1063, 700, 335, 700, 335, 700, 1063, 705, 1059, 704, 331, 704, 331, 704,
			331, 704, 330, 704, 99860 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		but_check = (Button) findViewById(R.id.check);
		but_send = (Button) findViewById(R.id.send);
		but_pp = (Button) findViewById(R.id.pp);
		but_getInst = (Button) findViewById(R.id.getInst);
		but_ac = (Button) findViewById(R.id.ac);

		but_check.setOnClickListener(this);
		but_send.setOnClickListener(this);
		but_pp.setOnClickListener(this);
		but_getInst.setOnClickListener(this);
		but_ac.setOnClickListener(this);

		ir = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
		irCodeService = new IrCodeServiceImpl();
		//infraredDB = new InfraredDB(this);
	}

	@Override
	public void onClick(View v) {

		if (v.equals(but_check)) {
			boolean is = ir.hasIrEmitter();
			if (is) {
				Toast.makeText(this, "有红外设备", 2000).show();
			} else {
				Toast.makeText(this, "没有红外设备", 2000).show();
			}
		} else if (v.equals(but_send)) {
			Toast.makeText(this, "发送红外码", 2000).show();
			
//			 ir.transmit(SAMPLE_FREQ,
//			 ConversionTools.Tool().leHexStr2Array("79037903790379037903790379037903f206f206f2067903790379037903f206f2067903790379037903b3c8"));
			//固定码和变化码
			String result = irCodeService.combineIrCode("2601029411fe1002460246020246022c06024602881300", "0230004DB2F80712ED0000");
			System.out.println("----result----:"+result);
			 ir.transmit(SAMPLE_FREQ,
					 ConversionTools.Tool().leHexStr2Array(result));
			 //数据过滤，并发送
//			ir.transmit(SAMPLE_FREQ, FilteringTools.Tool().fixedAarry(param));
		} else if (v.equals(but_pp)) {
			String iRDD = "{\"action\":\"IRA0\",\"type\":\"LE\",\"code\":\"64009411fe1046022c064602460246022c0646022c06460246024602460246022c06460246024602460246022c06460246024602460246022c0646022c064602460246022c064602460246024602460246024602460246022c0646022c0646022c0646022c0646022c0646022c0646022c0646022c064602460246024602460246024602460246022c0646022c0646022c06460246024602460246024602460246024602460246024602460246024602460246022c0646022c0646022c0646022c0646022c0646028813\"}";
			new JsonPost(IRConstants.URL, iRDD, this).persist();
		} else if (v.equals(but_getInst)) {
			String iRDD = "{\"action\":\"IRB0\",\"typeId\":\"100005\"}";
			new JsonPost(IRConstants.URL, iRDD, this).persist();
		} else if (v.equals(but_ac)) {
			Intent intent = new Intent(MainActivity.this, ACActivity.class);
			startActivity(intent);
		}

	}



}
