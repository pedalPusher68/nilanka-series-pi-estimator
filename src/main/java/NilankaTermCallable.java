import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  1:35 PM
 */
public class NilankaTermCallable implements Callable<BigDecimal> {

    public static final String SYS_PROP_SCALE_KEY = "NilankaTerm.scale";
    private static int scale;
    private static final BigDecimal four;

    static {
        String s = System.getProperty(SYS_PROP_SCALE_KEY, "6");
        try {
            scale = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            scale = 6;
        }
        four = new BigDecimal(4).setScale(scale, RoundingMode.HALF_UP);
    }

    private final BigDecimal sign;
    private final BigDecimal D1;
    private final BigDecimal D2;
    private final BigDecimal D3;

    public NilankaTermCallable( long d1, long d2, long d3, long N) {
        sign = (N % 2 > 0) ? BigDecimal.ONE.negate() : BigDecimal.ONE;
        this.D1 = new BigDecimal( d1 );
        this.D2 = new BigDecimal( d2 );
        this.D3 = new BigDecimal( d3 );
    }

    @Override
    public BigDecimal call() {
        BigDecimal DEN = D1.multiply( D2 ).multiply( D3 );
        return four.multiply(sign).divide( DEN, RoundingMode.HALF_UP );
    }

}
