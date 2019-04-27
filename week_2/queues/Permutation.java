/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randQueue.enqueue(str);
        }

        Iterator<String> iter = randQueue.iterator();
        while (iter.hasNext() &&
                k > 0) {
            StdOut.println(iter.next());
            k--;
        }
    }
}
