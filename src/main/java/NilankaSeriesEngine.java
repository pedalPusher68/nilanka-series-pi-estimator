import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  2:03 PM
 */
public abstract class NilankaSeriesEngine implements InfiniteSeriesEngine {

    private SeriesOutputs outputFormat = SeriesOutputs.SIMPLE_FORMAT;
    private long termsToCompute;
    private int scale;
    private BigDecimal pi = BigDecimal.ZERO;

    @Override
    public final void configureEngine(Map<String, String> config) {

        String param = config.getOrDefault("N","10");
        try {
            termsToCompute = Long.parseLong(param);
            if (termsToCompute < 0) {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            termsToCompute = 10;
            System.out.println(String.format("N \"%s\" is not an integer - defaulting to %d.", param, termsToCompute));
        } catch (IllegalArgumentException e) {
            termsToCompute = 10;
            System.out.println(String.format("N \"%s\" is not allowed integer - defaulting to %d.", param, termsToCompute));
        }

        param = config.getOrDefault("scale","6");
        try {
            scale = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            scale = 6;
            System.out.println(String.format("precision \"%s\" is not an integer - defaulting to %d.", param, scale));
        }
        System.setProperty(NilankaTermCallable.SYS_PROP_SCALE_KEY,Integer.toString(scale));

        param = config.getOrDefault("printFormat", "SIMPLE_FORMAT");
        try {
            outputFormat = SeriesOutputs.valueOf(param);
        } catch (IllegalArgumentException e) {
            System.out.println(String.format("precision \"%s\" is not allowed - defaulting to %s.", param, SeriesOutputs.SIMPLE_FORMAT.toString()));
            outputFormat = SeriesOutputs.SIMPLE_FORMAT;
        }

    }

    protected final int getScale() {
        return scale;
    }

    protected final void setPi(BigDecimal pi) {
        this.pi = pi;
    }

    @Override
    public long getNumberOfTermsUsed() {
        return termsToCompute;
    }

    @Override
    public String getComputedResults() {
        return this.pi.toString();
    }

    @Override
    public void printCurrentResult(long termNumber, String termValue) {
        switch(outputFormat) {
            case ENUMERATED_FORMAT:
                System.out.println(String.format("%d.  %s", termNumber, termValue ));
                break;
            case SIMPLE_FORMAT:
            default:
                System.out.println(String.format("%s", termValue ));
                break;
        }
    }
}
