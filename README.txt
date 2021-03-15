This solution can be run either using Java directly from a command prompt or using gradle.

Do the following to run the the solution using Java from the command line:

1. Change to working directory ./NilakanthaSeriesPiEstimator/src/main/
2. javac *.java
3. Run the program using coding problem requirements (defaults):
   type:  java InfiniteSeriesEngine

4. Default number of terms computed is 10 (N = 10).  Rerun the program with any integer
   value of N by typing (for example with N=1000):
   java InfiniteSeriesEngine N=1000

5. Run the program in 'bonus mode' as a generalized computation engine component
   that uses polymorphism to support runtime binding to different implementations
   of infinite series functions.  In this case, two implementations are provided:
     a.  NilankaSeriesMultiThreadedEngine - this engine meets the "Take-Home Coding Problem"
         requirements.
     b.  NilankaSeriesSingleThreadedEngine - this engine runs in a single 'main' thread of
         execution and computes the Nilanka Series similar to the problem solution of a.

   Run the bonuse mode like such:
   java InfiniteSeriesEngineRunner echoInputs=true N=20000 seriesEngineClass="NilankaSeriesSingleThreadedEngine"

6. Complete list of parameters that can be passed to the program:

    echoInputs=true - shows program input args

    N - sets the number of terms in the series to compute and sum - defaults to 10

    seriesEngineClass - sets the InfiniteSeriesEngine implementation to run.
                        - two are provided see 5. above
                        - defaults to NilankaSeriesMultiThreadedEngine to meet assignment requirements

    scale - sets the scale (precision) of the output values of the series - defaults to 6 per requirements.

    printFormat - can be set to SIMPLE_FORMAT or ENUMERATED_FORMAT - defaults to SIMPLE_FORMAT to match requirements.


Happy Pi Day - 3.14, 2021
and thank you for your consideration.

Brad Smith

