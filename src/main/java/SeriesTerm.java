import java.math.BigDecimal;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/17/21
 * Time:  12:33 PM
 */
public class SeriesTerm {

    BigDecimal value;
    String threadId;
    long t1_nanos;
    long t2_nanos;

    public BigDecimal getValue() {
        return value;
    }

    public String getThreadId() {
        return threadId;
    }

    public long getT1_nanos() {
        return t1_nanos;
    }

    public long getT2_nanos() {
        return t2_nanos;
    }
}
