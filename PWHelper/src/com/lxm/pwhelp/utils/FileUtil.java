package com.lxm.pwhelp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	 * create multiple director
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createFile(String director) {
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
     * 保存文件到sdcard
     * @param filename
     * @param content
     * @throws Exception
     */ 
    public static void saveFileSdcard(String filename,String content) throws Exception{ 
        try{ 
            File f = new File(Environment.getExternalStorageDirectory(),filename); 
            FileOutputStream out = new FileOutputStream(f); 
            out.write(content.getBytes("UTF-8")); 
            out.close(); 
        } 
        catch(Exception e){ 
            throw new Exception(); 
        } 
    }
    
    /**
     * 从sdcard读取文件 
     * @param context
     * @param filename
     * @return
     * @throws Exception
     */ 
    public static String loadFileFromSdcard(Context context,String filename) throws Exception{ 
        try{ 
            FileInputStream in = context.openFileInput(filename); 
            byte[]data = read2byte(in); 
            return new String(data,"UTF-8"); 
        } 
        catch(Exception e){ 
            throw new Exception(); 
        } 
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
