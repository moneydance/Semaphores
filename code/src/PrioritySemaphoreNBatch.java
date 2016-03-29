import java.util.concurrent.Semaphore;

public class PrioritySemaphoreNBatch {
    private static volatile double[] P; // priority of each process
    private static volatile double[] A; // arrival of each process
    private static volatile Semaphore[] B; //binary semaphores for processes
    private static volatile int N; //processes in system
    private static volatile PrioritySemaphoreNBatch instance; //singleton instance
    private static volatile int C; //count
    private static volatile double[] O; //out of order count
    private static int batch_count;
    private static boolean priority_flag;

    private PrioritySemaphoreNBatch(int p_count)
    {
        P = new double[p_count];
        A = new double[p_count];
        O = new double[p_count];
        B = new Semaphore[p_count];
        for (int i = 0; i < p_count; i++)
        {
            B[i] = new Semaphore(0, false);
            P[i] = -1;
            A[i] = -1;
            O[i] = 0;
        }
        batch_count = 0;
        priority_flag = true;
    }

    public static PrioritySemaphoreNBatch getInstance()
    {
        if (instance == null)
        {
            synchronized (PrioritySemaphore.class)
            {
                if (instance == null)
                {
                    instance = new PrioritySemaphoreNBatch(5);
                }
            }
        }
        return instance;
    }

    public void newWait(PriorityProcessNBatch p)
    {
        int i = p.getI();
        System.out.println("Process: " + i + " Requesting Critical Section");
        P[i] = priorityLevel(p);
        A[i] = priorityArrival();
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

    public void newSignal(PriorityProcessNBatch p)
    {
        int i = p.getI();
        System.out.println("Process: " + i + " Exiting Critical Section");
        P[i] = -1;
        A[i] = -1;
        N--;
        if (N>0)
        {
            int nextA = nextMin(A);
            if (batch_count >= 3)
            {
               priority_flag  = !priority_flag;
               batch_count = 0;
            }
            if (priority_flag) // use priority update out of order counts for debugging
            {
                batch_count++;
                int nextP = nextMax(P);
                if(nextP != nextA)
                {
                    updateO(nextP);
                }
                O[nextP] = 0;
                B[nextP].release();
                System.out.println("Process: " + nextP + " Entering Critical Section. Requests Priority: " + readRequests(P) + "Requests Arrival: " + readRequests(A) + "Out of Order Count: " + readRequests(O));
            }
            else // use fifo no need to update out of order count because processes will proceed in order.
            {
                batch_count++;
                O[nextA] = 0;
                B[nextA].release();
                System.out.println("Process: " + nextA + " Entering Critical Section. Requests Priority: " + readRequests(P) + "Requests Arrival: " + readRequests(A) + "Out of Order Count: " + readRequests(O));
            }
        }
    }

    private String readRequests(double[] a)
    {
        String s = "";
        for(int i = 0; i < a.length; i++)
        {
            if (a[i] >= 0)
            {
                s += "(" + i + ", " + a[i] + ") ";
            }
        }
        return s;
    }

    private void updateO(int nextP)
    {
        for (int x = 0; x < O.length; x++)
        {
            if (A[x]!=-1 && A[x] < A[nextP])
            {
                O[x]++;
            }
        }
    }

    private int nextMax(double[] a)
    {
        int next = -1;
        double mv = -10000;
        for(int i = 0; i < a.length; i++)
        {
            if (a[i] > mv && a[i] >= 0)
            {
                mv = a[i];
                next = i;
            }
        }
        return next;
    }

    private int nextMin(double[] a)
    {
        int next = -1;
        double mv = 10000;
        for(int i = 0; i < a.length; i++)
        {
            if (a[i] < mv && a[i] >= 0)
            {
                mv = a[i];
                next = i;
            }
        }
        return next;
    }

    private int priorityLevel(PriorityProcessNBatch p)
    {
        return p.getI();
    }

    private int priorityArrival()
    {
        return C;
    }
}
