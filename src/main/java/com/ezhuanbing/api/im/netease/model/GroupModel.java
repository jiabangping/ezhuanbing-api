package com.ezhuanbing.api.im.netease.model;

import java.util.ArrayList;
import java.util.List;

public class GroupModel {
	
	private String tname;
	private String owner;
	private List<String> members = new ArrayList<String>();
	private String msg;
	private int maxMemberCount;
	
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getMaxMemberCount() {
		return maxMemberCount;
	}
	public void setMaxMemberCount(int maxMemberCount) {
		this.maxMemberCount = maxMemberCount;
	}
}
