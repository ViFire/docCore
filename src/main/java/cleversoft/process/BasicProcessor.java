package cleversoft.process;

public abstract class BasicProcessor<T> {

    protected BasicProcessor<T> nextProc;

    public void setNextProcessor(BasicProcessor<T> nextProcessor) {
        this.nextProc = nextProcessor;
    }

    public T process(T input) {
        T r = doProcess(input);
        if (nextProc != null) {
            return nextProc.process(r);
        }
        return r;
    }

    abstract protected T doProcess(T input);
}
