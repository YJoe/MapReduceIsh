package Abstract;

public abstract class Reducer<I, O> extends Thread{
    protected I input;
    protected O output;

    public Reducer(I input){
        this.input = input;
    }

    @Override
    public void run(){
        reduce();
    }

    public abstract void reduce();

    public O getOutput(){
        return output;
    }
}
