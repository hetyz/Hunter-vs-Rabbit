package hu.elte.fi.progtech.tron.model.logic;


public class Stopwatch {
    private long executionTime;

    public Stopwatch() {
        this.executionTime = 0;
    }

    public void startTimer() {
        executionTime++;
    }

    public void resetTimer() {
        this.executionTime = 0;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
