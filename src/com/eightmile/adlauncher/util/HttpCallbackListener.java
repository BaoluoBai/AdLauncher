package com.eightmile.adlauncher.util;

public interface HttpCallbackListener {
	void onFinish(String response);
	
	void onError(Exception e);
}
