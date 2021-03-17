import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  1:42 PM
 */
public class PiTermCollector implements Runnable {

    private final Object monitor = new Object();
    private final ConcurrentSkipListMap<Long, Future<BigDecimal>> collector;
    AtomicReference<BigDecimal> piValue;
    private long lastTerm;
    private final NilankaSeriesEngine engine;

    public PiTermCollector(ConcurrentSkipListMap<Long, Future<BigDecimal>> collector, long startTerm, NilankaSeriesEngine engine) {
        this.collector = collector;
        this.lastTerm = startTerm;
        this.engine = engine;
        piValue = new AtomicReference<>(new BigDecimal("3.000000").setScale(this.engine.getScale(), RoundingMode.HALF_UP));
    }

    @Override
    public void run() {

        while( true ) {
            if (collector.containsKey(lastTerm)) {
                Future<BigDecimal> term = collector.get(lastTerm);
                if (term.isDone()) {
                    synchronized ( monitor ) {
                        try {
                            BigDecimal termVal = term.get();
                            String thread = Long.toString(Thread.currentThread().getId()) + "-" + Thread.currentThread().getName();
                            // add it... print it... remove this Future from the map
                            piValue.set( piValue.get().add(term.get()) );
                            engine.printCurrentResult(piValue.get().toString(), lastTerm, thread, 0L);
//                            System.out.println(String.format("%d.  %s", lastTerm, piValue ));
                            collector.remove(lastTerm);
                            lastTerm++;
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Thread.sleep(0, 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public BigDecimal getPi() {
        return this.piValue.get();
    }
}
