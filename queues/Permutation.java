/* *****************************************************************************
 *  Name:        Zhang Yi
 *  Date:        2022.03.02
 *  Description: Client for RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException();
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());
        for (int i = 0; i < k; ++i)
            StdOut.println(rq.dequeue());
        
    }
}
