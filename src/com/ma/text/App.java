package com.ma.text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.ma.text.common.K;
import com.ma.text.tools.ExitApplication;
import com.ma.text.tools.LogUtil;
import com.ma.text.tools.NetUtils;
import com.ma.text.widget.cache.UserCache;
import com.ma.text.widget.db.SQLiteDatabasePool;
import com.ma.text.widget.http.MHandler;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.SparseArray;

/**
 * Application
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public class App extends Application {
	private final static String TAG = "App";

	public static void addRequest(int taskId, MHandler handler) {
		if (requestArray.get(taskId) != null)
			handler.cancel();
		requestArray.put(taskId, handler);
		// Log.i(TAG, "addRequest -- TaskId = " + taskId);
	}

	private final static SparseArray<MHandler> requestArray = new SparseArray<MHandler>();

	public static void cancleRequest(int taskId) {
		MHandler handler = requestArray.get(taskId);
		if (handler != null) {
			handler.cancel();
			LogUtil.i(TAG, "cancleRequest -- TaskId = " + taskId);
		}
		finishRequest(taskId);
	}

	public final static void finishRequest(int taskId) {
		requestArray.delete(taskId);
		// Log.i(TAG, "finishRequest -- TaskId = " + taskId);
	}

	// ========================百度搜索=========================================
	// public String
	// GETALLSUGETION="http://api.map.baidu.com/place/v2/suggestion?region=上海&output=json&mcode=A4:59:27:02:40:B7:76:26:26:41:C8:65:BB:A5:ED:77:CB:D5:B7:2F;cn.com.parkingpass&output=json&ak=p6lMitzG1aA3g4lYKF2qAiu0&query=";
	public String GETALLSUGETION;
	// ========================捕获异常添加=======================================
	// activity对象列表,用于activity统一管理
	// 异常捕获
	protected boolean isNeedCaughtExeption = true;// 是否捕获未知异常
	// private PendingIntent restartIntent;
	private MyUncaughtExceptionHandler uncaughtExceptionHandler;
	private String packgeName;
	private static App application;

	private static final int LOG_FILE_NUM = 100;
	public static boolean isNetWork = true;

	public static App getApp() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		isNetWork = NetUtils.isConnected(this);
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(this.getPackageName(), 0);
			if (info != null)
				UserCache.saveVersion(info.versionName + "_" + info.versionCode);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		// 捕获异常add
		packgeName = getPackageName();
		if (isNeedCaughtExeption) {
			cauchException();
		}
	}

	// -------------------异常捕获-----捕获异常后重启系统-----------------//
	private void cauchException() {
		Intent intent = new Intent();
		// 参数1：包名，参数2：程序入口的activity
		intent.setClassName(packgeName, "cn.com.parkingpass.WelcomeActivity");
		// restartIntent = PendingIntent.getActivity(getApplicationContext(),
		// -1,
		// intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		// 程序崩溃时触发线程
		uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
	}

	// 创建服务用于捕获崩溃异常
	private class MyUncaughtExceptionHandler implements
			UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// 保存错误日志
			saveCatchInfo2File(ex);
			// 1秒钟后重启应用
			/*
			 * AlarmManager mgr = (AlarmManager)
			 * getSystemService(Context.ALARM_SERVICE);
			 * mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
			 * restartIntent);
			 */
			// 关闭当前应用
			ExitApplication.getInstance().exit();
		}
	};

	/**
	 * 保存错误信息到文件中
	 * 
	 * @return 返回文件名称
	 */
	private String saveCatchInfo2File(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String sb = writer.toString();
		try {
			String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss",
					Locale.getDefault()).format(new Date());
			String fileName = time + ".txt";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String filePath = Environment.getExternalStorageDirectory()
						+ K.file.CRASHLOG;
				File dir = new File(filePath);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						// 创建目录失败: 一般是因为SD卡被拔出了
						return "";
					}
				} else {
					File[] files = dir.listFiles();
					if (files != null) {
						int l = files.length;
						if (l > LOG_FILE_NUM) {
							for (int i = 0; i < 90; i++) {
								files[i].delete();
							}
						}
					}
				}
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				fos.write(sb.getBytes());
				fos.close();
				// 文件保存完了之后,在应用下次启动的时候去检查错误日志,发现新的错误日志,就发送给开发者
			}
			return fileName;
		} catch (Exception e) {
			System.out.println("an error occured while writing file..."
					+ e.getMessage());
		}
		return null;
	}

	// 结束线程,一般与finishAllActivity()一起使用
	// 例如: finishAllActivity;finishProgram();
	public void finishProgram() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private SQLiteDatabasePool mSQLiteDatabasePool;

	public SQLiteDatabasePool getSQLiteDatabasePool() {
		if (mSQLiteDatabasePool == null) {
			mSQLiteDatabasePool = SQLiteDatabasePool.getInstance(this);
			mSQLiteDatabasePool.createPool();
		}
		return mSQLiteDatabasePool;
	}

	public void setSQLiteDatabasePool(SQLiteDatabasePool sqliteDatabasePool) {
		this.mSQLiteDatabasePool = sqliteDatabasePool;
	}
}