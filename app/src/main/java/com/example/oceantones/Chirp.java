package com.example.oceantones;
public class Chirp {
    public static short[] generateChirpSpeaker(double startFreq, double endFreq, double time, double fs, double initialPhase,double scale) {

        int N = (int) (time * fs);
        short[] ans = new short[N];
        double f = startFreq;
        double k = (endFreq - startFreq) / time;
        double mult=(32767)*scale;
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            double phase = initialPhase + 2*Math.PI*(startFreq * t + 0.5 * k * t * t);
            phase = AngularMath.Normalize(phase);
            ans[i] = (short) (Math.sin(phase) * mult);
        }

        return ans;
    }
}