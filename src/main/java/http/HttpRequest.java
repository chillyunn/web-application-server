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

    public  HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            if (line == null)
                return;
            String[] tokens = line.split(" ");
            method = tokens[0];
            int contentLength = 0;
            String[] url = tokens[1].split("\\?");
            path = url[0];
            String[] queryString = url[1].split("\\&");
            for(String val:queryString){
                parameter.put(val.split("=")[0],val.split("=")[1]);
            }

            while ((line=br.readLine()) !=null) {
                log.debug("header: {}", line);
                tokens = line.split(":");
                header.put(tokens[0],tokens[1].trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
