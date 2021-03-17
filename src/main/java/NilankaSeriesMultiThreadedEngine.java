import java.math.BigDecimal;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  1:47 PM
 */
public class NilankaSeriesMultiThreadedEngine extends NilankaSeriesEngine {

    @Override
    public void computeSeries() {
        long N = getNumberOfTermsUsed();
        long granularity = getGranularity(N);
        long count = 1;

        ConcurrentSkipListMap<Long, Future<BigDecimal>> collector = new ConcurrentSkipListMap<>();
        PiTermCollector piTermCollector = new PiTermCollector(collector,count + 1, this);

        String thread = Long.toString(Thread.currentThread().getId()) + "-" + Thread.currentThread().getName();
        printCurrentResult(piTermCollector.getPi().toString(), count++, thread, 0L);
        if (N > 1) {
            long d1 = 2;
            long d2 = 3;
            long d3 = 4;

            // Creates a work-stealing thread pool using all available processors as its target parallelism level.
            // as java generally uses one thread per core this will result in a thread-pool with one thread per
            // available core on the machine this code runs on.
            ExecutorService service = Executors.newWorkStealingPool();

            service.submit(piTermCollector);
            do {
                for (long g=0;g<granularity;g++, count++, d1 +=2, d2 += 2, d3 += 2) {
                    NilankaTermCallable term = new NilankaTermCallable(d1, d2, d3, count);
                    collector.put(count,service.submit(term));
                    if (count > N) {
                        break;
                    }
                }
                // wait for first batch of tasks to complete
                while(!collector.isEmpty()) {
                    try {
                        Thread.sleep(0, 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (count <= N);
            service.shutdown();
        }
        setPi(piTermCollector.getPi());

    }

    /**
     * This method attempts to determine a 'granularity' based on the ratio of the number of terms to compute to the
     * number of cores available on the computer this code runs on.  It attempts to determine an order of magnitude,
     * 1, 10, 100, 1000, 10000, 100000, 1000000 with 1000000 being the upper limit to granularity.  The granularity
     * is then used to load the task queue in the thread pool with the granularity-number of tasks to compute terms.
     * the engine will compute terms and remove them from a map;  this process will be repeated until all the desired
     * terms have been computed for the series.
     *
     * @param N
     * @return
     */
    private static long getGranularity(long N) {
        N = Math.abs(N);
        long cores = Runtime.getRuntime().availableProcessors();
        long granularity = cores;
        if (N > cores) {
            long ratio = N / cores;
            long powerOfTen = 0;
            while (granularity < 1000000) { // if N is large such that granularity is > 1,000,000, then cap it at 1,000,000
                if (ratio > 10) {
                    powerOfTen++;
                    ratio /= 10;
                } else {
                    granularity = (long)Math.pow(10,powerOfTen);
                    break;
                }
            }
        }
        return granularity;
    }

}
