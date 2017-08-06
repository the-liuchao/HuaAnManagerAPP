package com.megvii.livenesslib.help;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;

import com.megvii.livenesslib.util.Constant;
import com.megvii.livenessdetection.bean.FaceIDDataStruct;

/**
 * 代码参考
 * 
 * 这里写了一些代码帮助（仅供参考）
 */
public class CodeHelp {

	/**
	 * 获取活体检测的BestImage和Delta 注意：需要在活体检测成功后调用
	 * 
	 * 如何获取idDataStruct： （从活体检测器中获取） FaceIDDataStruct idDataStruct =
	 * detector.getFaceIDDataStruct();
	 */
	public void getBestImageAndDelta(FaceIDDataStruct idDataStruct) {
		String delta = idDataStruct.delta; // 获取delta；
		HashMap<String, byte[]> images = (HashMap<String, byte[]>) idDataStruct.images;// 获取所有图片
		for (String key : idDataStruct.images.keySet()) {
			byte[] data = idDataStruct.images.get(key);
			if (key.equals("image_best")) {
				byte[] imageBestData = data;// 这是最好的一张图片
			} else if (key.equals("image_env")) {
				byte[] imageEnvData = data;// 这是一张全景图
			} else {
				// 其余为其他图片，根据需求自取
			}
		}
	}
	
	/**
	 * 根据byte数组，生成图片
	 */
	public static String saveJPGFile(Context mContext, byte[] data, String key) {
		if (data == null)
			return null;
		File mediaStorageDir = mContext
				.getExternalFilesDir(Constant.cacheImage);
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			String jpgFileName = System.currentTimeMillis() + ""
					+ new Random().nextInt(1000000) + "_" + key + ".jpg";
			fos = new FileOutputStream(mediaStorageDir + "/" + jpgFileName);
			bos = new BufferedOutputStream(fos);
			bos.write(data);
			return mediaStorageDir.getAbsolutePath() + "/" + jpgFileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
}