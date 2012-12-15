package com.amdp.bb.basic.entity;

public class UserBLL {
	private static UserBLL instance;
	private User currentUser;

	private UserBLL() {

	}

	public static UserBLL getInstanceBLL() {
		if (instance == null) {
			instance = new UserBLL();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
