package com.view.kevin.myview.activity.util;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import com.yqtec.toy.service.IPresenterService;
import com.yqtec.toy.service.IPresenterServiceCallback;

import java.util.ArrayList;
import java.util.List;

public class PresenterManager {

	private static final String TAG = "PresenterManager";

	private static PresenterManager s_intance;

	private Context mContext;
	private List<PresenterCallback> mCallbacks;
	private IPresenterService mPresenterService;
	private IPresenterServiceCallback mServiceCallback = new IPresenterServiceCallback.Stub() {

		@Override
		public void onHabitReminding(String signal) throws RemoteException {
			Log.d(TAG, "[onHabitReminding] signal=" + signal);
			notifyHabitReminding(signal);
		}

		@Override
		public void onAntiAddiction() throws RemoteException {
			Log.d(TAG, "[onAntiAddiction]");
			notifyAntiAddiction();
		}

		@Override
		public void onCartoonTimeOver() throws RemoteException {
			Log.d(TAG, "[onCartoonTimeOver]");
			notifyCartoonOver();
		}
	};

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e(TAG, "onServiceDisconnected");
			try {
				mPresenterService.unregisterCallback(mServiceCallback);
				mPresenterService = null;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e(TAG, "onServiceConnected");
			try {
				mPresenterService = IPresenterService.Stub.asInterface(service);
				mPresenterService.registerCallback(mServiceCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	private PresenterManager(Context context) {
		mContext = context;
		mCallbacks = new ArrayList<>();
	}

	public static PresenterManager instance(Context context) {
		if (s_intance == null) {
			s_intance = new PresenterManager(context);
		}
		return s_intance;
	}

	public void setupService() {
		Log.e(TAG, "setupService");
		Intent service = new Intent();
//		service.setPackage("com.yqtec.model");
//		service.setAction("com.yqtec.model.action.service.PresenterService");
//		mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
		service.setComponent(new ComponentName("com.yqtec.model", "com.yqtec.toy.service.PresenterService"));
//		mContext.startService(service);//启动服务
		mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
	}

	public void releaseService() {
		Log.e(TAG, "releaseService");
		mContext.unbindService(conn);
	}

	public boolean isServiceEnable() {
		return mPresenterService != null;
	}

	public void resumeDetect() {
		if (mPresenterService != null) {
			try {
				mPresenterService.resumeDetect();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void pauseDetect() {
		if (mPresenterService != null)
			try {
				mPresenterService.pauseDetect();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public long sqlIntert(String tableName, String nullColumnHack, ContentValues values) {
		if (mPresenterService != null)
			try {
				return mPresenterService.sqlIntert(tableName, nullColumnHack, values);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public void sendVideoLog(ContentValues values) {
		if (mPresenterService != null) {
			try {
				mPresenterService.sendVideoLog(values);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void setInTencentSDK(boolean flag) {
		if (mPresenterService != null)
			try {
				mPresenterService.setInTencentSDK(flag);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public boolean isDebugVersion() {
		if (mPresenterService != null)
			try {
				mPresenterService.isDebugVersion();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		return false;
	}
	public String getPetAttrs() {
		String attrs = null;
		if (mPresenterService != null){
			try {
				attrs = mPresenterService.getPetAttrs();

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return attrs;
	}

	public void onAntiItemTimeIn(String item) {
		if (mPresenterService != null)
			try {
				Log.e(TAG, "onAntiItemTimeIn");
				mPresenterService.onAntiItemTimeIn(item);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public void onAntiItemTimeOut(String item) {
		if (mPresenterService != null)
			try {
				Log.e(TAG, "onAntiItemTimeOut");
				mPresenterService.onAntiItemTimeOut(item);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public void showAntiAddictionWarn(String item, boolean showPop) {
		if (mPresenterService != null)
			try {
				Log.e(TAG, "showAntiAddictionWarn");
				mPresenterService.showAntiAddictionWarn(item, showPop);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public void onCartoonIn(ContentValues values) {
		if (mPresenterService != null)
			try {
				Log.e(TAG, "onCartoonIn");
				mPresenterService.onCartoonIn(values);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public void onCartoonOut() {
		if (mPresenterService != null)
			try {
				Log.e(TAG, "onCartoonOut");
				mPresenterService.onCartoonOut();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public void addPresenterCallback(PresenterCallback cb) {
		if (!mCallbacks.contains(cb)) {
			mCallbacks.add(cb);
		}
	}

	public void removePresenterCallback(PresenterCallback cb) {
		mCallbacks.remove(cb);
	}

	private void notifyHabitReminding(String signal) {
		for (int i = 0; i < mCallbacks.size(); i++) {
			mCallbacks.get(i).onHabitReminding(signal);
		}
	}

	private void notifyAntiAddiction() {
		for (int i = 0; i < mCallbacks.size(); i++) {
			mCallbacks.get(i).onAntiAddiction();
		}
	}

	private void notifyCartoonOver() {
		for (int i = 0; i < mCallbacks.size(); i++) {
			mCallbacks.get(i).onCartoonTimeOver();
		}
	}
}
