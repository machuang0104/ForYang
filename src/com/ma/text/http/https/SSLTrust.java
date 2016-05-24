package com.ma.text.http.https;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class SSLTrust extends SSLSocketFactory {
	private SSLContext s_context;
	public class SSLTrustAllManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[]{};
		}

	}
	public SSLTrust(String algorithm, KeyStore keystore,
			String keystorePassword, KeyStore truststore, SecureRandom random,
			HostNameResolver nameResolver) throws NoSuchAlgorithmException,
			KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super(algorithm, keystore, keystorePassword, truststore, random,
				nameResolver);
		s_context = SSLContext.getInstance("TLS");
		s_context
				.init(null, new TrustManager[]{new SSLTrustAllManager()}, null);
		setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	public SSLTrust(KeyStore keystore, String keystorePassword,
			KeyStore truststore) throws NoSuchAlgorithmException,
			KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super(keystore, keystorePassword, truststore);
		s_context = SSLContext.getInstance("TLS");
		s_context
				.init(null, new TrustManager[]{new SSLTrustAllManager()}, null);
		setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	public SSLTrust(KeyStore keystore, String keystorePassword)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(keystore, keystorePassword);
		s_context = SSLContext.getInstance("TLS");
		s_context
				.init(null, new TrustManager[]{new SSLTrustAllManager()}, null);
		setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	public SSLTrust(KeyStore truststore) throws NoSuchAlgorithmException,
			KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super(truststore);
		s_context = SSLContext.getInstance("TLS");
		s_context
				.init(null, new TrustManager[]{new SSLTrustAllManager()}, null);
		setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		return s_context.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}
	@Override
	public Socket createSocket() throws IOException {
		return super.createSocket();
	}

	public static SSLSocketFactory getSocketFactory1() {
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			SSLTrust trust = new SSLTrust(keystore);
			return trust;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
