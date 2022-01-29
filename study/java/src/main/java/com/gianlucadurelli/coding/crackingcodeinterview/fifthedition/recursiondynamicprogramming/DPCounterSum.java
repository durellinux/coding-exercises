package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.recursiondynamicprogramming;

public class DPCounterSum {
    private long result;
    private long count;

    public DPCounterSum(long result, long count) {
        this.result = result;
        this.count = count;
    }

    public long getResult() {
        return result;
    }

    public long getCount() {
        return count;
    }

    public void add(DPCounterSum other) {
        this.result += other.result;
        this.count += other.count;
    }
}
