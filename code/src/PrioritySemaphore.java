import java.util.concurrent.Semaphore;

public class PrioritySemaphore {
    private static volatile double[] P;
    private static volatile Semaphore[] B;
    public static volatile int N;
    private static volatile PrioritySemaphore instance;
    private static volatile int C;
    private static String problem;

    private PrioritySemaphore(int p_count)
    {
        P = new double[p_count];
        B = new Semaphore[p_count];
        for (int i = 0; i < p_count; i++)
        {
            B[i] = new Semaphore(0, false);
            P[i] = -1;
        }
        N = 0;
        C = 0;
    }

    public static void setProblem(String a)
    {
        problem = a;
    }

    public static PrioritySemaphore getInstance()
    {
        if (instance == null)
        {
            synchronized (PrioritySemaphore.class)
            {
                if (instance == null)
                {
                    instance = new PrioritySemaphore(5);
                }
            }
        }
        return instance;
    }

    public void newWait(PriorityProcess p)
    {
        int i = p.getI();
        System.out.println("Process: " + i + " Requesting Critical Section");
        P[i] = getPriority(p);
        N++;
        C++;
        if (N > 1)
        {
            try
            {
                B[i].acquire();
            }
            catch(InterruptedException e){}
        }
        else
        {
            System.out.println("Process: " + i + " Entering Critical Section");
        }
    }

    public void newSignal(PriorityProcess p)
    {
        int i = p.getI();
        System.out.println("Process: " + i + " Exiting Critical Section");
        P[i] = -1;
        N--;
        if (N>0)
        {
           int next = next();
           B[next].release();
           System.out.println("Process: " + next + " Entering Critical Section. Requests: " + readRequests());
        }
    }

    private String readRequests()
    {
        String s = "";
        for(int i = 0; i < P.length; i++)
        {
            if (P[i] >= 0)
            {
                s += "(" + i + ", " + P[i] + ") ";
            }
        }
        return s;
    }

    private int nextMax()
    {
        int next = -1;
        double mv = -10000;
        for(int i = 0; i < P.length; i++)
        {
            if (P[i] > mv && P[i] >= 0)
            {
                mv = P[i];
                next = i;
            }
        }
        return next;
    }

    private int nextMin()
    {
        int next = -1;
        double mv = 10000;
        for(int i = 0; i < P.length; i++)
        {
            if (P[i] < mv && P[i] >= 0)
            {
                mv = P[i];
                next = i;
            }
        }
        return next;
    }

    private int next()
    {
        if (problem.equals("b"))
            return nextMax();
        else if (problem.equals("c"))
            return nextMin();
        else if (problem.equals("d"))
            return nextMin();
        return -1;
    }

    private double getPriority(PriorityProcess p)
    {
        if (problem.equals("b"))
            return p.getI();
        else if (problem.equals("c"))
            return p.getK();
        else if (problem.equals("d"))
            return C;
        return -1;
    }
}
