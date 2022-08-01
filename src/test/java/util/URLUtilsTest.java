package util;

import junit.framework.TestCase;
import org.junit.Test;

public class URLUtilsTest extends TestCase {
    @Test
    public void testSplitLine() {
        String[] line = URLUtils.splitLine("GET /index.html HTTP/1.1");
        assertEquals("GET",line[1]);
    }

}