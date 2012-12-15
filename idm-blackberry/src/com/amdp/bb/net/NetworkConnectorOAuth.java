package com.amdp.bb.net;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;


import net.excelsys.mobile.bb.observer.NotificationCenter;
import net.excelsys.mobile.bb.observer.NotificationsTypeConstants;
import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.http.HttpProtocolConstants;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import com.bb.log.Log4Device;

public class NetworkConnectorOAuth {

	private ConnectionManager _manager = ConnectionManager.getInstance();


	public static final String REQUEST_METHOD_PUT = "PUT";
	public static final String REQUEST_METHOD_POST = HttpConnection.POST;
	public static final String REQUEST_METHOD_DELETE = "DELETE";

	private String mURL = "";
	private HttpConnection connection;

	public NetworkConnectorOAuth(String mURL) {

		this.mURL = mURL;
	}



	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public JSONObject connect(URLEncodedPostData postParameters) {
		try {
			return connect(postParameters, false);
		} catch (IOException e) {
			return null;
		}
	}

	public JSONObject connect() throws IOException {
		return connect(null, false);
	}

	public JSONObject connect(URLEncodedPostData postParameters, boolean passRC401) throws IOException {

		String connectionURL = mURL;
		InputStream is = null;
		OutputStream os = null;
		JSONObject json = null;

		Log4Device.log("### NetworkConnector URL: " + connectionURL);

		try {

			connection = (HttpConnection) _manager.getConnection(connectionURL, Connector.READ_WRITE, true);
			connection.setRequestMethod(requestMethod);



			if (postParameters != null) {

				connection.setRequestProperty(HttpProtocolConstants.HEADER_CONTENT_TYPE, postParameters.getContentType());
				byte[] postBytes = postParameters.getBytes();
				connection.setRequestProperty(HttpProtocolConstants.HEADER_CONTENT_LENGTH, Integer.toString(postBytes.length));
				os = connection.openOutputStream();
				os.write(postBytes);
				os.flush();
				os.close();
				os = null;

			}

			int rc = connection.getResponseCode();

			if (passRC401) {

				if (rc == HttpConnection.HTTP_OK || rc == HttpConnection.HTTP_UNAUTHORIZED) {

					is = connection.openInputStream();
					// String contentType = connection.getType();
					int len = (int) connection.getLength();
					byte[] response;
					int bytesRead;
					if (len > 0) {
						// Length supplied - just read that amount
						int actual = 0;
						bytesRead = 0;
						response = new byte[len];
						// We have found reading it in one go doesn't work as
						// well
						// as the following
						while ((bytesRead != len) && (actual != -1)) {
							actual = is.read(response, bytesRead, len - bytesRead);
							bytesRead += actual;
						}
					} else {
						// No length supplied - read until EOF
						response = IOUtilities.streamToBytes(is);
						bytesRead = response.length;
					}

					String rawResponse = Utilities.fromUTF8(response);
					if (rawResponse != null) {
						try {
							json = new JSONObject(rawResponse);
						} catch (JSONException e) {
							Log4Device.log("Error parsing the raw response from the server. NetworkConnector class.");
						}
					}

					else {
						notificateHTTPError(rc);
					}
				}
			}

			else {
				if (rc == HttpConnection.HTTP_OK) {

					is = connection.openInputStream();
					// String contentType = connection.getType();
					int len = (int) connection.getLength();
					byte[] response;
					int bytesRead;
					if (len > 0) {
						// Length supplied - just read that amount
						int actual = 0;
						bytesRead = 0;
						response = new byte[len];
						// We have found reading it in one go doesn't work as
						// well
						// as the following
						while ((bytesRead != len) && (actual != -1)) {
							actual = is.read(response, bytesRead, len - bytesRead);
							bytesRead += actual;
						}
					} else {
						// No length supplied - read until EOF
						response = IOUtilities.streamToBytes(is);
						bytesRead = response.length;
					}

					String rawResponse = Utilities.fromUTF8(response);
					if (rawResponse != null) {
						try {
							json = new JSONObject(rawResponse);
							Log4Device.log(json.toString());
						} catch (JSONException e) {
							Log4Device.log("Error parsing the raw response from the server. NetworkConnector class."+e.toString());
						}
					}


				}
				
				else {
					notificateHTTPError(rc);
				}
				
			}

		} catch (IOException ioe) {
			NotificationCenter.getInstance().executeNotification(NotificationsTypeConstants.NETWORK_NOT_AVAILABLE);
			throw new IOException("Error of connection: " + ioe.getMessage());

		} finally {
			if (is != null)
				is.close();
			if (connection != null)
				connection.close();
		}

		
		return json;

	}



	private String requestMethod = HttpConnection.GET;

	public String getRawResponse() throws IOException {
		String result = "";
		InputStream is = null;

		try {
			connection = (HttpConnection) _manager.getConnection(mURL, Connector.READ_WRITE, true);
			connection.setRequestMethod(requestMethod);


			is = connection.openInputStream();
			byte[] responseData = new byte[10000];
			int length = 0;
			StringBuffer rawResponse = new StringBuffer();
			while (-1 != (length = is.read(responseData))) {
				rawResponse.append(new String(responseData, 0, length));
			}
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpConnection.HTTP_OK) {
				throw new IOException("Https response code: " + responseCode);
			}

			result = rawResponse.toString();

		} catch (IOException ioe) {
			NotificationCenter.getInstance().executeNotification(NotificationsTypeConstants.NETWORK_NOT_AVAILABLE);
			ioe.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (connection != null)
				connection.close();
		}
		return result;
	}

	
	public String getRawHttpResponse() throws IOException {
		String result = "";
		InputStream is = null;
		HttpConnection connectionHttp = null;
		try {
			connectionHttp = (HttpConnection) _manager.getConnection(mURL, Connector.READ_WRITE, true);

			connectionHttp.setRequestMethod(requestMethod);


			is = connectionHttp.openInputStream();
			byte[] responseData = new byte[10000];
			int length = 0;
			StringBuffer rawResponse = new StringBuffer();
			while (-1 != (length = is.read(responseData))) {
				rawResponse.append(new String(responseData, 0, length));
			}
			int responseCode = connectionHttp.getResponseCode();
			if (responseCode != HttpConnection.HTTP_OK) {
				throw new IOException("Https response code: " + responseCode);
			}

			result = rawResponse.toString();

		} catch (IOException ioe) {
			NotificationCenter.getInstance().executeNotification(NotificationsTypeConstants.NETWORK_NOT_AVAILABLE);
			ioe.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (connectionHttp != null)
				connectionHttp.close();
		}
		return result;
	}
	
	private void notificateHTTPError(int rc) {
		if (rc == 401) {
			NotificationCenter.getInstance().executeNotification(NotificationsTypeConstants.HTTP_UNAUTHORIZED);
		} else if (rc >= 400 && rc <= 500) {
			NotificationCenter.getInstance().executeNotification(NotificationsTypeConstants.HTTP_CLIENT_ERROR);
		} else if (rc >= 500 && rc < 600) {
			NotificationCenter.getInstance().executeNotification(NotificationsTypeConstants.HTTP_SERVER_ERROR);
		}
	}

}