import java.util.Random;

public class PriorityProcess extends Thread
{
    private static final int LOOPS = 10;
    private int k;
    private int i;

    public PriorityProcess(int i)
    {
        k = 0;
        this.i = i;
    }

    public void run()
    {
        while (k < LOOPS)
        {
            critical();
        }
    }

    public int getI()
    {
        return i;
    }

    public int getK()
    {
        return k;
    }

    protected void critical()
    {
        PrioritySemaphore.getInstance().newWait(this);
        PriorityProcess.sleep(20);
        PrioritySemaphore.getInstance().newSignal(this);
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
