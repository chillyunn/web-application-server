package webserver;

import http.HttpRequest;
import org.junit.Test;
import util.HttpRequestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class HttpRequestTest {
    private String testDirectory = "./src/test/java/resources/";

    @Test
    public void request_GET() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory +"Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("GET",request.getMethod());
        assertEquals("/user/create",request.getPath());
        assertEquals("keep-alive",request.getHeader("Connection"));
        assertEquals("javajigi",request.getParameter("userId"));
    }
    @Test
    public void request_POST() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory +"Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("POST",request.getMethod());
        assertEquals("/user/create",request.getPath());
        assertEquals("keep-alive",request.getHeader("Connection"));
        assertEquals("javajigi",request.getParameter("userId"));
    }
}
