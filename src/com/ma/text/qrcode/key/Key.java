package com.ma.text.qrcode.key;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.crypto.SecretKey;

import android.app.Application;
import android.util.Log;

public class Key extends Application {
//	private static final String SECREKEY = "qrcode";
	private static Key mApp;
	private SecretKey key;

	public static Key getApp() {
		return mApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;

		key = getSecretKey();
		if (key != null)
			Log.i("way", key.toString());
	}

	/**
	 * 用来生成一个通用的加密密钥
	 */
//	private void initSecreKey() {
//		try {
//			FileOutputStream fos = openFileOutput("qrcode.des",
//					Context.MODE_PRIVATE);
//			SecretKey key = DesEncrypt.getKey(SECREKEY);// 生成密匙
//			ObjectOutputStream keyFile = new ObjectOutputStream(fos);
//			keyFile.writeObject(key);
//			keyFile.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public SecretKey getSecretKey() {
		if (key != null)
			return key;
		ObjectInputStream ois = null;
		try {
			InputStream is = getAssets().open("qrcode.des");
			// FileInputStream fis = openFileInput("qrcode.des");
			ois = new ObjectInputStream(is);
			SecretKey key = (SecretKey) ois.readObject();
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
