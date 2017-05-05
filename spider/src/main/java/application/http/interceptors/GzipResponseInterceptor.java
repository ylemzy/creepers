package application.http.interceptors;


import application.http.HttpHeaders;
import application.http.utils.HTTPLogger;
import okhttp3.*;
import okio.*;

import java.io.IOException;
import java.util.zip.GZIPInputStream;


public class GzipResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.header(HttpHeaders.ACCEPT_ENCODING) == null) {
            HTTPLogger.debug("We can accept gzip response content!");
            Response response = chain.proceed(request.newBuilder().header(HttpHeaders.ACCEPT_ENCODING, "gzip").build());
            String contentEncoding = response.header(HttpHeaders.CONTENT_ENCODING);
            if (contentEncoding != null) {
                HTTPLogger.debug("Start to parse gzip response content");
                return response.newBuilder().body(gzip(response.body())).build();
            }
            HTTPLogger.debug("Response seems not support gzip.");
            return response;
        }


        return chain.proceed(request);
    }

    private ResponseBody gzip(final ResponseBody body) {
        return new ResponseBody() {

            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                //we don't know the size
                return -1;
            }

            @Override
            public BufferedSource source() {
                /*unzip*/
                try {
                    GZIPInputStream in = new GZIPInputStream(body.byteStream());
                    Source source = Okio.source(in);
                    return Okio.buffer(source);
                } catch (IOException e) {
                    return null;
                }
            }
        };
    }
}
