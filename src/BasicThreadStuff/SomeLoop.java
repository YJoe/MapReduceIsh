package BasicThreadStuff;

public class SomeLoop extends Thread{
    private String tag;
    private int a, b, c;


    public SomeLoop(String tag, int a, int b){
        this.tag = tag;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        System.out.println("STARTING " + startTime);
        c = 0;

        for(int i = 0; i < 10000 * a * b; i++){
            c += a + b;
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("TOTAL DURATION " + duration);

    }

    public int getResult(){
        return c;
    }
}
