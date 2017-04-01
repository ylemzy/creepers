

package application.http.utils;



import application.http.exceptions.HttpException;
import application.http.request.HttpRequest;
import application.http.response.HttpResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;



public class HttpClientHelper {
    public static Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);


    /**
     * @param httpRequest
     * @param responseClass
     * @param <T>
     * @return
     * @throws HttpException
     */
    public static <T> HttpResponse<T> request(HttpRequest httpRequest, Class<T> responseClass) throws HttpException {
    	Response response = null;
        try {

            OkHttpClient client = ClientFactory.getHttpClient();

            Request request = prepareRequest(httpRequest);

            response = client.newCall(request).execute();

            HttpResponse httpResponse = new HttpResponse<>(request.url().toString(), request.method(), response, responseClass);

            return httpResponse;
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
        	if(response != null) {
        		try {
        			response.body().close();
				} catch (Exception e2) {
        }
        	}
        }

    }

    public static void requestAsync(HttpRequest httpRequest, final Class<?> responseClass) {

        OkHttpClient client = ClientFactory.getHttpClient();

        final Request request = prepareRequest(httpRequest);


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                HttpResponse httpResponse = new HttpResponse(request.url().toString(), request.method(), response, responseClass);
                //respose code
            }
        });
    }

    private static Request prepareRequest(HttpRequest request) {

        String urlToRequest = null;
        try {
            URL url = new URL(request.getUrl());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), URLDecoder.decode(url.getPath(), "UTF-8"), "", url.getRef());
            urlToRequest = uri.toURL().toString();
            if (url.getQuery() != null && !url.getQuery().trim().equals("")) {
                if (!urlToRequest.substring(urlToRequest.length() - 1).equals("?")) {
                    urlToRequest += "?";
                }
                urlToRequest += url.getQuery();
            } else if (urlToRequest.substring(urlToRequest.length() - 1).equals("?")) {
                urlToRequest = urlToRequest.substring(0, urlToRequest.length() - 1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Request.Builder builder = new Request.Builder()
                .url(urlToRequest);

        //add headers
        Set<Entry<String, List<String>>> entrySet = request.getHeaders().entrySet();
        for (Entry<String, List<String>> entry : entrySet) {
            List<String> values = entry.getValue();
            if (values != null) {
                for (String value : values) {
                    builder.addHeader(entry.getKey(), value);
                }
            }
        }


        return builder.method(request.getHttpMethod().name(), request.getBody()).build();
    }

}
