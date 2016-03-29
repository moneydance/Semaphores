import java.util.Random;
public class PrintProcess extends Thread
{
    private static final int loops = 5;
    private static int num_threads;
    private int k;
    private int i;

    public PrintProcess(int i)
    {
        k = 0;
        this.i = i;
        num_threads++;
    }

    public void run()
    {
        while (k < loops)
        {
            print();
        }
    }

    public int getK()
    {
        return k;
    }

    public int getI()
    {
        return i;
    }

    public static int getLoops()
    {
        return loops;
    }

    public static int getNumThreads()
    {
        return num_threads;
    }

    protected void print()
    {
        sleep(20);
        System.out.println("\n=========================");
        System.out.println("Thread: " + i + " Entering Critical Section");
        System.out.println("Thread " + i + " is starting iteration " + (k + 1));
        System.out.println("We hold these truths to be self-evident, that all men are created equal,");
        System.out.println("that they are endowed by their Creator with certain unalienable Rights,");
        System.out.println("that among these are Life, Liberty and the pursuit of Happiness.");
        System.out.println("Thread " + i + " is done with iteration " + (k + 1));
        System.out.println("Thread: " + i + " Exiting Critical Section");
        System.out.println("=========================");
        k++;
    }

    public static void sleep(int max_sleep_time)
    {
        Random rand = new Random();
        int sleep_time = rand.nextInt((max_sleep_time - 5) + 1) + 5;
        try
        {
                Thread.sleep(sleep_time);
        }
        catch (InterruptedException e)
        {
            System.out.println("got interrupted!");
        }
    }
}
