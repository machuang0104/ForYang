/**
 * Project Name:ParkingPass
 * File Name:FileUtil.java
 * Package Name:cn.com.parkingpass.tools
 * Date:2015-8-19上午9:00:51
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.tools;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Environment;

import com.ma.text.common.K;

/**
 * ClassName:FileUtil Function: 文件操作汇总 Date: 2015-8-19 上午9:00:51
 * 
 * @author machuang
 */
public class FileUtil {
	private static final String TAG = "FileUtil";
	public static String saveText(String fileName, String text) {

		try {
			String name = fileName + ".txt";
			FileOutputStream outStreanm = new FileOutputStream(
					Environment.getExternalStorageDirectory()
							+ K.file.FILE_PATH + name, true);
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				OutputStreamWriter writer = new OutputStreamWriter(outStreanm);
				writer.write(text);
				writer.write("/n");
				writer.flush();
				writer.close();
				outStreanm.close();
			}
			return fileName;
		} catch (Exception e) {
			LogUtil.e(TAG, "File Save Error :" + e.getMessage());
		}
		return null;

	}
}
