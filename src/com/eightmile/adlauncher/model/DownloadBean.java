package com.eightmile.adlauncher.model;

public class DownloadBean {
	private String name;
	
	private double downloadProgress;
	
	private long downloadReference;
	
	private String downloadAddress;
	
	

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		boolean isEqual = false;
        if (o != null && o instanceof DownloadBean) {
            isEqual = (this.downloadReference == ((DownloadBean) o).downloadReference);
        }
        return isEqual;
	}
	
	public DownloadBean(String name, String downloadAddress) {
        this.name = name;
        this.downloadAddress = downloadAddress;
        this.downloadProgress = 0;
        this.downloadReference = 0;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDownloadProgress() {
		return downloadProgress;
	}

	public void setDownloadProgress(double downloadProgress) {
		this.downloadProgress = downloadProgress;
	}

	public long getDownloadReference() {
		return downloadReference;
	}

	public void setDownloadReference(long downloadReference) {
		this.downloadReference = downloadReference;
	}

	public String getDownloadAddress() {
		return downloadAddress;
	}

	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}
	
}
