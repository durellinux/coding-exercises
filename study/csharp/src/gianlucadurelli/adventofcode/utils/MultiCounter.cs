namespace src.gianlucadurelli.adventofcode.utils;

public class MultiCounter
{
    private int _bitMask;
    private int _maskBits;
    private long _counter;

    public MultiCounter(int counterBits, int counters)
    {
        if (counterBits * counters > 64)
        {
            throw new ArgumentException("Multi counter must fit 64 bits");
        }

        _counter = 0;
        _bitMask = (1 << counterBits) - 1;
        _maskBits = counterBits;
    }

    public long GetValue()
    {
        return _counter;
    }

    public long GetCounter(int counter)
    {
        int offset = counter * _maskBits;
        return (_counter >> offset) & _bitMask;
    }

    public void SetCounter(int counter, long value)
    {
        int offset = counter * _maskBits;
        long newValueWithOffset = value << offset;
        _counter = (_counter & ~(_bitMask << offset)) | newValueWithOffset;
    }

    public long Increment(int counter)
    {
        long value = GetCounter(counter) + 1;
        SetCounter(counter, value);
        return value;
    }

    public long Decrement(int counter)
    {
        long value = GetCounter(counter) - 1;
        SetCounter(counter, value);
        return value;
    }
}