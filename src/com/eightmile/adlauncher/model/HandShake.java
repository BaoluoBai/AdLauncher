package com.eightmile.adlauncher.model;

public class HandShake {
	private String type;
    private String mid;

    public HandShake(String mid, String type) {
        this.mid = mid;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LoginData [type=" + type + ", mid=" + mid + "]";
	}
    
    
}
