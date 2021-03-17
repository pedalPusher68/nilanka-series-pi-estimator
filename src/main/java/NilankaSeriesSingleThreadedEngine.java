import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  1:49 PM
 */
public class NilankaSeriesSingleThreadedEngine extends NilankaSeriesEngine {

    @Override
    public void computeSeries() {

        long N = getNumberOfTermsUsed();

        BigDecimal pi = new BigDecimal("3.000000").setScale(getScale());
        setPi(pi);

        BigDecimal one = BigDecimal.ONE;
        BigDecimal two = new BigDecimal( 2 );
        BigDecimal four = new BigDecimal(4).setScale(getScale());
        BigDecimal D1 = new BigDecimal( 2 );
        BigDecimal D2 = new BigDecimal( 3 );
        BigDecimal D3 = new BigDecimal( 4 );
        BigDecimal DEN = D1.multiply( D2 ).multiply( D3 );
        BigDecimal TERM = four.divide( DEN, RoundingMode.HALF_UP );
        long nth = 1;

        String thread = Long.toString(Thread.currentThread().getId()) + "-" + Thread.currentThread().getName();
        printCurrentResult(pi.toString(), nth++, thread, 0L);

        do {
            pi = pi.add(TERM);
            setPi(pi);
            thread = Long.toString(Thread.currentThread().getId()) + "-" + Thread.currentThread().getName();
            printCurrentResult(pi.toString(), nth++, thread, 0L);
//            System.out.println(String.format("%d.  %s", nth++, pi.toString() ));
//            if (N % 1000000 == 0) {
//                System.out.println(String.format("%d.  %s", N, pi.toString() ));
//            }

            D1 = D1.add( two );
            D2 = D2.add( two );
            D3 = D3.add( two );
            DEN = D1.multiply( D2 ).multiply( D3 );
            one = one.negate();
            TERM = four.multiply( one ).divide( DEN, RoundingMode.HALF_UP );

        } while (nth <= N);
    }

}
