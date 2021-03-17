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
    private final ConcurrentSkipListMap<Long, Future<SeriesTerm>> collector;
    AtomicReference<BigDecimal> piValue;
    private long lastTerm;
    private final NilankaSeriesEngine engine;

    public PiTermCollector(ConcurrentSkipListMap<Long, Future<SeriesTerm>> collector, long startTerm, NilankaSeriesEngine engine) {
        this.collector = collector;
        this.lastTerm = startTerm;
        this.engine = engine;
        piValue = new AtomicReference<>(new BigDecimal("3.000000").setScale(this.engine.getScale(), RoundingMode.HALF_UP));
    }

    @Override
    public void run() {

        while( true ) {
            if (collector.containsKey(lastTerm)) {
                Future<SeriesTerm> future = collector.get(lastTerm);
                long t1 = System.nanoTime();
                if (future.isDone()) {
                    synchronized ( monitor ) {
                        try {
                            SeriesTerm term = future.get();
                            String thread = Long.toString(Thread.currentThread().getId()) + "-" + Thread.currentThread().getName();
                            thread += " Nilanka term thread => "+term.getThreadId();
                            // add it... print it... remove this Future from the map
                            piValue.set( piValue.get().add(term.getValue()) );
                            engine.printCurrentResult(piValue.get().toString(), lastTerm, thread, System.nanoTime() - t1);
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
