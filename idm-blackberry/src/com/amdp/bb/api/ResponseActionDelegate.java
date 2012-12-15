package com.amdp.bb.api;

public interface ResponseActionDelegate {
	void didSuccessfully(String status);
	void didNotSuccessfully(String status);
}
