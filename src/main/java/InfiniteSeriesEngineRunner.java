import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 23121020+pedalPusher68@users.noreply.github.com
 * Date:  3/14/21
 * Time:  1:48 PM
 */
public class InfiniteSeriesEngineRunner {

    public static void main(String[] args) {

        Map<String, String> rawArgs = processRawArguments(args); // process the supplied command-line args and store in Map

        InfiniteSeriesEngine engine = null;

        String str = rawArgs.getOrDefault("seriesEngineClass","NilankaSeriesMultiThreadedEngine");
        try {
            engine = (InfiniteSeriesEngine)Class.forName(str).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            System.err.println(String.format("Problem instantiating seriesEngineClass=\"%s\"", str));
            e.printStackTrace();
            System.exit(-1);
        }

        str = rawArgs.getOrDefault("N","10");
        long N = 10;
        try {
            N = Long.parseLong(str);
        } catch (NumberFormatException e) {
            System.out.println(String.format("N \"%s\" is not an integer - defaulting to %d.", str, N));
        }
        rawArgs.put("N", str.toString());

        engine.configureEngine(rawArgs);
        engine.computeSeries();
        System.out.println(
            String.format("Final Approximation for Pi:  %s  using %d approximations.",
                engine.getComputedResults(),
                engine.getNumberOfTermsUsed()
            )
        );
        System.exit(0);
    }


    private static Map<String, String> processRawArguments(String[] args) {

        Map<String, String> argMap = new HashMap<>(args.length);

        for (String arg : args) {
            String[] pairs = arg.split("[=]", 2);
            if (pairs.length == 2) {
                argMap.put(pairs[0], pairs[1]);
            } else {
                argMap.put(arg, null);
            }
        }

        if (argMap.containsKey("echoInputs")
            && "true".equalsIgnoreCase(argMap.get("echoInputs").trim().toLowerCase())) {
            System.out.println("Input Arguments to InfiniteSeriesEngineRunner");
            for (String key : argMap.keySet()) {
                System.out.println(String.format("\t %s = '%s'", key, argMap.get(key)));
            }
        }
        // lots of ways to ask for help
        if (argMap.containsKey("help") || argMap.containsKey("-help") || argMap.containsKey("--help") ||
            argMap.containsKey("h") || argMap.containsKey("-h") || argMap.containsKey("--h")
        ) {
            System.out.println(doGenerateHelpText());
            System.exit(0);
        }
        return argMap;
    }

    public static String doGenerateHelpText() {
        String helpText = "\nInfiniteSeriesEngineRunner - usage:  "
            + "java InfiniteSeriesEngineRunner (PROGRAM ARGUMENTS)\n"
            + "\twhere PROGRAM ARGUMENTS are\n"
            + "\tseriesEngineClass=\"<YOUR FULLY QUALIFIED ENGINE CLASS>\" - Defaults to NilankaSeriesSingleThreadedEngine.\n"
            + "\tN=(First N terms of series to compute) - Defaults to 10.\n";
        return helpText;
    }

}
