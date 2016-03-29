public class PrintProcessBakery extends PrintProcess
{
    public static volatile int[] tickets = new int[4];

    public PrintProcessBakery(int i)
    {
        super(i);
    }

    @Override
    public void run()
    {
        while(getK() < getLoops())
        {
            int i = getI();
            tickets[i] = 1;
            tickets[i] = max(tickets) + 1;
            for (int j =0; j < tickets.length; j++)
            {
                while (tickets[j]!= 0 && less(tickets[j], j, tickets[i], i)){}
            }
            print();
            tickets[i] = 0;
        }
    }

    private boolean less(int t1, int i1, int t2, int i2)
    {
        if (t1 == t2)
            return i1 < i2;
        else
            return t1 < t2;
    }

    private int max(int[] a)
    {
        int cmax = -10000;
        for(int i = 0; i < a.length; i++)
        {
            if (cmax < a[i])
            {
                cmax = a[i];
            }
        }
        return cmax;
    }
}


