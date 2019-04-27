/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] percentToMakePercolate;
    private final int trials;

    public PercolationStats(int n, int trials) {

        percentToMakePercolate = new double[trials];
        this.trials = trials;

        // loop through trials
        for (int j = 0; j < trials; j++) {
            Percolation perc = new Percolation(n);

            // Create a list of random 1D index and
            // convert them to 2D coordinates later.
            // So we can randomly open sites.
            int totalNum = n * n;
            int[] arrayIndex1D = new int[totalNum];
            for (int k = 0; k < totalNum; k++) {
                arrayIndex1D[k] = k;
            }
            StdRandom.shuffle(arrayIndex1D);

            // randomly open sites util percolates
            for (int k = 0; k < n * n; k++) {
                int[] arrayxy = index1DToxy(arrayIndex1D[k], n);
                int x = arrayxy[0];
                int y = arrayxy[1];

                perc.open(x, y);
                if (perc.percolates()) {
                    percentToMakePercolate[j] = perc
                            .numberOfOpenSites()
                            / (double) totalNum;  // keep track the number of open sites
                    break;
                }

            }
        }
    }

    public double mean() {
        return StdStats.mean(percentToMakePercolate);
    }

    public double stddev() {
        return StdStats.stddev(percentToMakePercolate);
    }

    public double confidenceLo() {
        return this.mean() - this.stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return this.mean() + this.stddev() / Math.sqrt(trials);
    }

    // convert 1D index to 2D x, y. 1 <= x <= n, 1 <= y <= n
    // 0 <= index1D <= n-1
    private int[] index1DToxy(int index1D, int n) {
        int x = 1 + index1D / n;
        int y = 1 + index1D % n;

        int[] arrayxy = new int[] { x, y };

        return arrayxy;
    }

    public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]),
                                                          Integer.parseInt(args[1]));
        System.out.println("mean: =" + String.format("%.8f", percStats.mean()));
        System.out.println("stddev: =" + String.format("%.8f", percStats.stddev()));
        String output = String.format("confidence interval: [%.8f = %.8f]",
                                      percStats.confidenceLo(),
                                      percStats.confidenceHi());
        System.out.printf(output);

    }
}
