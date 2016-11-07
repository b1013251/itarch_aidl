// IGrayService.aidl
package b1013251.itarch.com.aidlpractice;

// Declare any non-default types here with import statements

interface IStaticticsService {
    double mean (in double[] numList);
    double variance (in double[] numList);
}