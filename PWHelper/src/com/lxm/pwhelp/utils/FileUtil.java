package com.lxm.pwhelp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.os.Environment;
/**
 * file utility
 * @author lianxiao
 * @version 2015-5-29 15:39:06
 */
public class FileUtil {

	/**
	 * @param director
	 * @return
	 */
	public static boolean isFileExist(String director) {
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + director);
		return file.exists();
	}

	/**
	 * create multiple directory
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createDirectory(String director) {
		if (isFileExist(director)) {
			return true;
		} else {
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + director);
			if (!file.mkdirs()) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * create new file
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createFile(String fileName) throws IOException {
		if (isFileExist(fileName)) {
			return true;
		} else {
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + fileName);
			if (!file.createNewFile()) {
				return false;
			}
			return true;
		}
	}
	
	/**
     * 保存文件到sdcard
     * @param filename
     * @param content
     * @throws Exception
     */ 
    public static void saveFileSdcard(String filename,String content,boolean isAppend) throws FileNotFoundException,UnsupportedEncodingException,IOException { 
        File f = new File(Environment.getExternalStorageDirectory(),filename); 
        FileOutputStream out = new FileOutputStream(f,isAppend);
		out.write(content.getBytes("UTF-8"));
        out.close(); 
    }
    
    /**
     * 从sdcard读取文件 
     * @param context
     * @param filename
     * @return
     * @throws Exception
     */ 
    public static String loadFileFromSdcard(Context context,String filename) throws FileNotFoundException,IOException { 
       // FileInputStream in = context.openFileInput(filename); 
        FileInputStream fis = new FileInputStream(filename);
        byte[]data = read2byte(fis); 
        return new String(data); 
    }
    
    /**
	 * 
	 * @param director
	 *            (you don't need to begin with
	 *            Environment.getExternalStorageDirectory()+File.separator)
	 * @param fileName
	 * @param content
	 * @param encoding
	 *            (UTF-8...)
	 * @param isAppend
	 *            : Context.MODE_APPEND
	 * @return
	 */
	public static File writeToSDCardFile(String directory, String fileName,
			String content, String encoding, boolean isAppend) {
		// mobile SD card path +path
		File file = null;
		OutputStream os = null;
		try {
			if (!createFile(directory)) {
				return file;
			}
			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + directory + File.separator + fileName);
			os = new FileOutputStream(file, isAppend);
			if (encoding.equals("")) {
				os.write(content.getBytes());
			} else {
				os.write(content.getBytes(encoding));
			}
			os.flush();
		} catch (IOException e) {
			LogUtil.e("FileUtil", "writeToSDCardFile:" + e.getMessage());
		} finally {
			try {
				if(os != null){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
    
    private static byte[] read2byte(InputStream in) throws IOException { 
        byte[] data; 
        ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
        byte[]buf = new byte[1024]; 
        int len = 0; 
        while((len = in.read(buf))!=-1){ 
            bout.write(buf, 0, len); 
        } 
        data = bout.toByteArray(); 
        return data; 
    }
}
