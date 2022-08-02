package webserver;

import junit.framework.TestCase;
import org.junit.Test;
import util.HttpRequestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestHandlerTest extends TestCase {
    @Test
    public void testParseURL(){
        String url = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        String[] tokens = url.split(" ");
        String[] queryString = tokens[1].split("\\?");
        assertEquals("userId=javajigi&password=password&name=JaeSung",queryString[1]);
    }
    @Test
    public void testParseQueryString(){
        String url = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        String[] tokens = url.split(" ");
        String[] queryString = tokens[1].split("\\?");
        Map<String,String> parameters = HttpRequestUtils.parseQueryString(queryString[1]);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is("password"));
        assertThat(parameters.get("name"), is("JaeSung"));
    }
    @Test
    public void testDir() throws IOException {
        System.out.println(new File("/webapp").getCanonicalPath());
    }
    @Test
    public void testNull(){
        String url = "\"GET /user/create";
        int index = url.indexOf("?");
        assertEquals(0,index);
    }
}