package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> parameter = new HashMap<>();;

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            nullCheck(line);
            int contentLength = 0;
            String[] tokens = line.split(" ");

            String[] url = tokens[1].split("/?");
            path = url[0];
            String[] queryString= url[1].split("&");
            parameter.put(queryString[0],queryString[1]);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getHeader(String line){
        while (!line.equals("")) {
            line = br.readLine();
            log.debug("header: {}", line);
            tokens = line.split(":");
            header.put(tokens[0], tokens[1]);
        }
    }
    private void nullCheck(String line){
        if(line == null)
            return;
    }
    public String[] split(String string,String regex){
        return string.split("regex");
    }
    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getParameter() {
        return parameter;
    }
}
