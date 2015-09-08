/*
 * HttpConnector.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A HttpConnector is a class encapsulating simple HTTP access.
 *
 * @author Andres Viedma Pelez
 * @author Sergiu Gordea
 */
public class HttpConnector {

    private static final int CONNECTION_RETRIES = 3;
    private static final int TIMEOUT_CONNECTION = 300000;//5 minutes
    private static final int STATUS_OK_START = 200;
    private static final int STATUS_OK_END = 299;
    private static final String ENCODING = "UTF-8";
    private HttpClient httpClient = null;

    private static final Log log = LogFactory.getLog(HttpConnector.class);
	
    public String getURLContent(String url) throws IOException {
        HttpClient client = this.getHttpClient(CONNECTION_RETRIES, TIMEOUT_CONNECTION);
        GetMethod getRequest = new GetMethod(url);

        try {
            client.executeMethod(getRequest);
            byte[] byteResponse = getRequest.getResponseBody();
            return new String(byteResponse, ENCODING);

        } finally {
            getRequest.releaseConnection();
        }
    }

    public boolean writeURLContent(String url, OutputStream out) throws IOException {
        return writeURLContent(url, out, null);
    }

    public boolean writeURLContent(String url, OutputStream out, String requiredMime) throws IOException {
        HttpClient client = this.getHttpClient(CONNECTION_RETRIES, TIMEOUT_CONNECTION);
        GetMethod getMethod = new GetMethod(url);
        try {
            client.executeMethod(getMethod);

            Header tipoMimeHead = getMethod.getResponseHeader("Content-Type");
            String tipoMimeResp = "";
            if (tipoMimeHead != null) {
                tipoMimeResp = tipoMimeHead.getValue();
            }

            if (getMethod.getStatusCode() >= STATUS_OK_START && getMethod.getStatusCode() <= STATUS_OK_END
                    && ((requiredMime == null) || ((tipoMimeResp != null) && tipoMimeResp.contains(requiredMime)))) {
                InputStream in = getMethod.getResponseBodyAsStream();

                // Copy input stream to output stream
                byte[] b = new byte[4 * 1024];
                int read;
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }

                getMethod.releaseConnection();
                return true;
            } else {
                return false;
            }

        } finally {
            getMethod.releaseConnection();
        }
    }

    public boolean silentWriteURLContent(String url, OutputStream out, String mimeType) {
        try {
            return this.writeURLContent(url, out, mimeType);

        } catch (Exception e) {
           log.debug("Exception occured when copying thumbnail from url: " + url, e);
        	return false;
        }
    }

    public boolean checkURLContent(String url, String requiredMime) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b)
                    throws IOException {
            }
        };

        boolean bOk = this.silentWriteURLContent(url, out, requiredMime);
        return bOk;
    }

    private HttpClient getHttpClient(int connectionRetry, int conectionTimeout) {
        if (this.httpClient == null) {
            HttpClient client = new HttpClient();

            //TODO: write english code comments 
            //Se configura el n�mero de reintentos
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(connectionRetry, false));

            //TODO: write english code comments 
            //Se comprueban las propiedades proxy del sistema. Si est�n rellenas, se rellena
            String proxyHost = System.getProperty("http.proxyHost");
            if ((proxyHost != null) && (proxyHost.length() > 0)) {
                String proxyPortSrt = System.getProperty("http.proxyPort");
                if (proxyPortSrt == null) {
                    proxyPortSrt = "8080";
                }
                int proxyPort = Integer.parseInt(proxyPortSrt);

                client.getHostConfiguration().setProxy(proxyHost, proxyPort);
            }

            //TODO: write english code comments 
            //Se configura el timeout de la conexion. Primero se intenta asignar los par�metros
            //pasados. Si est�n vac�os, se pone el par�metro por defecto
            boolean bTimeout = false;
            String connectTimeOut = System.getProperty("sun.net.client.defaultConnectTimeout");
            if ((connectTimeOut != null) && (connectTimeOut.length() > 0)) {
                client.getParams().setIntParameter("sun.net.client.defaultConnectTimeout", Integer.parseInt(connectTimeOut));
                bTimeout = true;
            }
            String readTimeOut = System.getProperty("sun.net.client.defaultReadTimeout");
            if ((readTimeOut != null) && (readTimeOut.length() > 0)) {
                client.getParams().setIntParameter("sun.net.client.defaultReadTimeout", Integer.parseInt(readTimeOut));
                bTimeout = true;
            }
            if (!bTimeout) {
                client.getParams().setIntParameter(HttpMethodParams.SO_TIMEOUT, conectionTimeout);
            }

            this.httpClient = client;
        }
        return this.httpClient;
    }
    
    public String getURLContent(String url, String jsonParamName, String jsonParamValue) throws IOException {
        HttpClient client = this.getHttpClient(CONNECTION_RETRIES, TIMEOUT_CONNECTION);
        PostMethod post = new PostMethod(url);
        post.setParameter(jsonParamName, jsonParamValue);

        try {
            client.executeMethod(post);

            if (post.getStatusCode() >= STATUS_OK_START && post.getStatusCode() <= STATUS_OK_END) {
                byte[] byteResponse = post.getResponseBody();
                String res = new String(byteResponse, ENCODING);
                return res;
            } else {
                return null;
            }

        } finally {
        	post.releaseConnection();
        }
    }
}
