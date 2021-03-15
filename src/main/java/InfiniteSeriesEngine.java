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

    void printCurrentResult(long termNumber, String termValue);
}
