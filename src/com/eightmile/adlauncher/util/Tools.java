package com.eightmile.adlauncher.util;

import java.io.File;

public class Tools {
	public static boolean fileIsExist(String url){
		 try{
             File f=new File(url);
             if(!f.exists()){
                     return false;
             }
             
		 }catch (Exception e) {
             // TODO: handle exception
             return false;
		 }
		 return true;
	}
	
	
	public static String getNameFromUrl(String url) {
    	
        return url.substring(url.lastIndexOf("/") + 1);
    }
	
}
