package com.eightmile.adlauncher.util;

public class WebImage {
	
	public WebImage(){
		this.width = 0;
		this.height = 0;
		this.pleft = 0;
		this.ptop = 0;
		this.background = "";
		this.url = "";
	}
	private int width;
	
	private int height;
	
	private int pleft;
	
	private int ptop;
	
	private String background;
	
	private String url;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPleft() {
		return pleft;
	}

	public void setPleft(int pleft) {
		this.pleft = pleft;
	}

	public int getPtop() {
		return ptop;
	}

	public void setPtop(int ptop) {
		this.ptop = ptop;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
