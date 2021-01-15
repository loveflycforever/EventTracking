package signature;

import com.apoem.mmxx.eventtracking.signature.MillisecondClock;
import org.junit.Test;

import java.sql.Timestamp;

public class MillisecondClockTest {

    @Test
    public void name() {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println(System.currentTimeMillis());
            System.out.println(timestamp.toString());

    }

    @Test
    public void tik() {
        System.out.println(
                MillisecondClock.strik());
    }

}