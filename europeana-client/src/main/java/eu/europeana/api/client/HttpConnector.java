/*
 * HttpConnector.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * A HttpConnector is a class encapsulating simple HTTP access.
 *
 * @author Andres Viedma Pelez
 */
public class HttpConnector {

    private static final int CONNECTION_RETRIES = 3;
    private static final int TIMEOUT_CONNECTION = 40000;
    private static final int STATUS_OK_START = 200;
    private static final int STATUS_OK_END = 299;
    private static final String ENCODING = "UTF-8";
    private HttpClient httpClient = null;

    public String getURLContent(String url) throws IOException {
        HttpClient client = this.getHttpClient(CONNECTION_RETRIES, TIMEOUT_CONNECTION);
        GetMethod consulta = new GetMethod(url);

        try {
            client.executeMethod(consulta);

            if (consulta.getStatusCode() >= STATUS_OK_START && consulta.getStatusCode() <= STATUS_OK_END) {
                byte[] byteResponse = consulta.getResponseBody();
                String res = new String(byteResponse, ENCODING);
                return res;
            } else {
                return null;
            }

        } finally {
            consulta.releaseConnection();
        }
    }

    public boolean writeURLContent(String url, OutputStream out) throws IOException {
        return writeURLContent(url, out, null);
    }

    public boolean writeURLContent(String url, OutputStream out, String requiredMime) throws IOException {
        HttpClient client = this.getHttpClient(CONNECTION_RETRIES, TIMEOUT_CONNECTION);
        GetMethod consulta = new GetMethod(url);
        try {
            client.executeMethod(consulta);

            Header tipoMimeHead = consulta.getResponseHeader("Content-Type");
            String tipoMimeResp = "";
            if (tipoMimeHead != null) {
                tipoMimeResp = tipoMimeHead.getValue();
            }

            if (consulta.getStatusCode() >= STATUS_OK_START && consulta.getStatusCode() <= STATUS_OK_END
                    && ((requiredMime == null) || ((tipoMimeResp != null) && tipoMimeResp.contains(requiredMime)))) {
                InputStream in = consulta.getResponseBodyAsStream();

                // Copy input stream to output stream
                byte[] b = new byte[4 * 1024];
                int read;
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }

                consulta.releaseConnection();
                return true;
            } else {
                return false;
            }

        } finally {
            consulta.releaseConnection();
        }
    }

    public boolean silentWriteURLContent(String url, OutputStream out, String mimeType) {
        try {
            return this.writeURLContent(url, out, mimeType);

        } catch (Exception e) {
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

    private HttpClient getHttpClient(int reintentosConexion, int timeoutConexion) {
        if (this.httpClient == null) {
            HttpClient client = new HttpClient();

            //TODO: write english code comments 
            //Se configura el n�mero de reintentos
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(reintentosConexion, false));

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
                client.getParams().setIntParameter(HttpMethodParams.SO_TIMEOUT, timeoutConexion);
            }

            this.httpClient = client;
        }
        return this.httpClient;
    }
}
