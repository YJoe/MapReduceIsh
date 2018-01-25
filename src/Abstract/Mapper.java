package Abstract;

public abstract class Mapper<I, O> extends Thread{
    protected O output;
    protected I input;

    public Mapper(I input){
        this.input = input;
    }

    @Override
    public void run(){
        super.run();
        map();
    }

    protected abstract void map();

    public O getData(){
        return output;
    }
}
