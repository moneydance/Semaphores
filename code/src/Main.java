public class Main
{
    public static void main (String[] args)
    {
        if (args[0].equals("b"))
        {
            PrioritySemaphore.setProblem("b");
            int N = 5;
            PriorityProcess[] p = new PriorityProcess[N];
            for(int i=N-1; i >= 0; i--)
            {
                p[i] = new PriorityProcess(i);
                p[i].start();
                sleep(2);
            }
        }
        else if (args[0].equals("c"))
        {
            PrioritySemaphore.setProblem("c");
            int N = 5;
            PriorityProcess[] p = new PriorityProcess[N];
            for(int i=N-1; i >= 0; i--)
            {
                p[i] = new PriorityProcess(i);
                p[i].start();
                sleep(2);
            }
        }
        else if (args[0].equals("d"))
        {
            PrioritySemaphore.setProblem("d");
            int N = 5;
            PriorityProcess[] p = new PriorityProcess[N];
            for(int i=N-1; i >= 0; i--)
            {
                p[i] = new PriorityProcess(i);
                p[i].start();
                sleep(2);
            }
        }
        else if (args[0].equals("4"))
        {
            int N = 5;
            PriorityProcessNBatch[] p = new PriorityProcessNBatch[N];
            for(int i=N-1; i >= 0; i--)
            {
                p[i] = new PriorityProcessNBatch(i);
                p[i].start();
                sleep(2);
            }
        }
        else if (args[0].equals("1"))
        {
            int N = 4;
            PrintProcessBakery[] p = new PrintProcessBakery[N];
            for(int i=N-1; i >= 0; i--)
            {
                p[i] = new PrintProcessBakery(i);
                p[i].start();
                sleep(2);
            }
        }
    }

    public static void sleep(int sleep_time)
    {
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
