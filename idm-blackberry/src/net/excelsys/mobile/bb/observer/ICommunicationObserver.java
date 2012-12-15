package net.excelsys.mobile.bb.observer;

public interface ICommunicationObserver{
	void alertHTTPUnauthorized();
	void alertHTTPClientError();
	void alertHTTPServerError();
	void alertNetworkNotAvailable();
	void alertClientInBlackList();
	void alertServiceUnavailable();
	void alertShowMessage(String message);
}
