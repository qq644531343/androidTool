package com.example.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DevicePolicyManager dpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		
	}
	
	public void openAdmin(View view ){
		//声明一个意图，作用是开启设备的超级管理员
		  Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		  ComponentName cn = new ComponentName(this, MyAdmin.class);
          intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
          //劝说用户开启管理员
          intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                  "开启我把。开启我就可以锁屏了，开启送积分");
          startActivity(intent);
	}

	public void click(View view){
		ComponentName cn = new ComponentName(this, MyAdmin.class);
		if(dpm.isAdminActive(cn)){
			//设备管理员的api
			dpm.resetPassword("123", 0);
			dpm.lockNow();
//			dpm.wipeData(0);
//			dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//删除sdcard数据 
		}else{
//			openAdmin(null);
			Toast.makeText(this, "请先激活管理员", 0).show();
		}
		
	}
	
	public void uninstall(View view){
		 ComponentName cn = new ComponentName(this, MyAdmin.class);
		//可以移除管理员
		dpm.removeActiveAdmin(cn);
		Intent intent = new Intent();
		intent.setAction("android.intent.action.UNINSTALL_PACKAGE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:"+getPackageName()));
		startActivity(intent);
		
	}

}
