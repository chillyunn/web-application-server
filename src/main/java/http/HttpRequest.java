package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    int contentLength = 0;
    boolean logined = false;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> parameter = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String[] tokens = getLine(br).split(" ");
            setMethod(tokens[0]);

            String[] url = tokens[1].split("\\?");
            setPath(url[0]);

            setQueryString(url);
            setHeaders(br);
            setParameters(br,contentLength);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setMethod(String method) {
        this.method=method;
    }
    private void setPath(String path){
        this.path=path;
    }

    private void setQueryString(String[] url) {
        if (url.length == 2) {
            String[] queryString = url[1].split("\\&");
            for (String val : queryString) {
                parameter.put(val.split("=")[0], val.split("=")[1]);
            }
        }
    }

    private void setParameters(BufferedReader br, int contentLength) throws IOException {
        if (contentLength != 0) {
            String httpBody = IOUtils.readData(br, contentLength);
            parameter = HttpRequestUtils.parseQueryString(httpBody);
        }
    }

    private void setHeaders(BufferedReader br) throws IOException {
        String[] tokens;
        String line;
        while (!(line = br.readLine()).equals("")) {
            log.debug("header: {}", line);
            if (line.contains("Content-Length")) {
                contentLength = getContentLength(line);
            tokens = line.split(":");
            header.put(tokens[0], tokens[1].trim());
        }
    }
    private int getContentLength(String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
    }

    public String getLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        log.debug("request line : {}",line);
        if (line == null)
            throw new IllegalStateException();
        return line;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }
}
