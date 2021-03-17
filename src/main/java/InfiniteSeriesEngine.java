import java.util.Map;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  1:55 PM
 */
public interface InfiniteSeriesEngine {

    void configureEngine(Map<String, String> config);

    void computeSeries();

    String getComputedResults();

    long getNumberOfTermsUsed();

    /**
     *     NO_PRINTING,
     *     SIMPLE_FORMAT,
     *     ENUMERATED_FORMAT,
     *     THREAD_ID_FORMAT,
     *     THREAD_ID_ENUMERATED_FORMAT,
     *     TIME_PROFILING_FORMAT,
     *     FULL_DETAILS_FORMAT
     *
     * @param termValue
     * @param termNumber
     * @param threadId
     * @param timeDeltaNs
     */
    void printCurrentResult(String termValue, long termNumber, String threadId, long timeDeltaNs);
}
