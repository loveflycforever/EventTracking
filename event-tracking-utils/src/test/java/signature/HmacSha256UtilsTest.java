package signature;

import com.apoem.mmxx.eventtracking.signature.HmacSha256Utils;
import org.junit.Test;

public class HmacSha256UtilsTest {


    @Test
    public void encodeTest() {
        String jsonString = "{\"sites\":[{\"name\":\"google\",\"url\":\"www.google.com\"},{\"name\":\"微博\",\"url\":\"www.weibo.com\"}]}";
        String sourcePassport = "bs";
        String result = HmacSha256Utils.encode(jsonString, sourcePassport);
        System.out.println(result);
    }

    @Test
    public void teString() {
        String format = String.format("%s%08d", 20200723, 1111111);
        System.out.println(format);
    }
}