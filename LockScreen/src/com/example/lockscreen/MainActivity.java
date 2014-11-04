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
		//����һ����ͼ�������ǿ����豸�ĳ�������Ա
		  Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		  ComponentName cn = new ComponentName(this, MyAdmin.class);
          intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
          //Ȱ˵�û���������Ա
          intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                  "�����Ұѡ������ҾͿ��������ˣ������ͻ���");
          startActivity(intent);
	}

	public void click(View view){
		ComponentName cn = new ComponentName(this, MyAdmin.class);
		if(dpm.isAdminActive(cn)){
			//�豸����Ա��api
			dpm.resetPassword("123", 0);
			dpm.lockNow();
//			dpm.wipeData(0);
//			dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//ɾ��sdcard���� 
		}else{
//			openAdmin(null);
			Toast.makeText(this, "���ȼ������Ա", 0).show();
		}
		
	}
	
	public void uninstall(View view){
		 ComponentName cn = new ComponentName(this, MyAdmin.class);
		//�����Ƴ�����Ա
		dpm.removeActiveAdmin(cn);
		Intent intent = new Intent();
		intent.setAction("android.intent.action.UNINSTALL_PACKAGE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:"+getPackageName()));
		startActivity(intent);
		
	}

}
