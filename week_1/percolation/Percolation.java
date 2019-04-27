/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;    // number of sites on one side
    private final int total;    // total number of sites
    private int numOpen = 0;    // keep track of the number of open sites.
    private boolean[] openArray;    // keep track of weather each site is open
    private final WeightedQuickUnionUF ufObj;    // Keep track of connected components

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        gridSize = n;
        total = n * n + 2;    // including two virtual sites
        this.ufObj = new WeightedQuickUnionUF(total);
        this.openArray = new boolean[total];

        this.openArray[0] = true;    // open the top virtual site
        this.openArray[total - 1] = true;    // open the buttom virtual site

        for (int i = 1; i < total - 1; i++) {
            this.openArray[i] = false;    // set all the other sites blocked
        }


    }

    /*
     * Convert a 2D coordinate to 1D. If the given coordinate is invalid,
     * -1 is returned.
     */

    private int xyTo1D(int x, int y) {
        if ((x < 1) || (x > gridSize) || (y < 1) || (y > gridSize)) {
            return -1;    //  -1 indicates the site is invalid
        }
        else {
            int index1D = (x - 1) * gridSize + y;
            return index1D;
        }
    }

    /*
     * Calculate the 4 neighbors' index of the input site.
     * If the input site is on the boundary, those invalid neighbors are
     * labeled by -1 through xyTo1D function.
     */
    private int[] neighbors(int x, int y) {
        int[] neighbors = new int[4];

        int indexUp = xyTo1D(x - 1, y);
        int indexDown = xyTo1D(x + 1, y);
        int indexLeft = xyTo1D(x, y - 1);
        int indexRight = xyTo1D(x, y + 1);

        neighbors[0] = indexUp;
        neighbors[1] = indexDown;
        neighbors[2] = indexLeft;
        neighbors[3] = indexRight;

        return neighbors;
    }

    /*
     * Open a site. It does two jobs.
     * 1. set the input site to be open, i.e. set the crosponding element
     * in openArray to be true.
     * 2. connect the input site to its open neighbor. If the site is on the
     * last row, it should be connectted to the bottom virtual point.
     */
    public void open(int x, int y) {
        if ((x < 1) || (x > gridSize) || (y < 1) || (y > gridSize)) {
            throw new java.lang.IllegalArgumentException();
        }

        int currentSiteIndex = xyTo1D(x, y);
        openArray[currentSiteIndex] = true;
        numOpen++;

        if (x == gridSize) {
            ufObj.union(currentSiteIndex, total - 1);    // connnet to bottom virtual site.
        }

        if (x == 1) {
            ufObj.union(currentSiteIndex, 0);    // connet to top virtual site.
        }

        int[] neighbors = neighbors(x, y);
        for (int i = 0; i < 4; i++) {
            int neighborIndex = neighbors[i];
            if ((neighborIndex != -1) && (openArray[neighborIndex])) {
                ufObj.union(neighborIndex, currentSiteIndex);
            }
        }


    }

    /*
     * Check if a site is open.
     */
    public boolean isOpen(int x, int y) {
        if ((x < 1) || (x > gridSize) || (y < 1) || (y > gridSize)) {
            throw new java.lang.IllegalArgumentException();
        }
        int index1D = xyTo1D(x, y);
        return openArray[index1D];
    }

    /*
     * Check if a site is connected to the top virtual site.
     */
    public boolean isFull(int x, int y) {
        if ((x <= 0) || (x > gridSize) || (y <= 0) || (y > gridSize)) {
            throw new java.lang.IllegalArgumentException();
        }
        int index1D = xyTo1D(x, y);
        return ufObj.connected(0, index1D);
    }

    /*
     * Return the number of open sites (excluding the two virtual sites)
     */
    public int numberOfOpenSites() {
        return numOpen;
    }

    /*
     * Check if the two virtual sites are connected.
     */
    public boolean percolates() {
        return ufObj.connected(0, total - 1);
    }

    public static void main(String[] args) {
        Percolation newObj = new Percolation(6);

        newObj.open(1, 6);
        newObj.open(2, 6);
        newObj.open(3, 6);
        newObj.open(4, 6);
        newObj.open(5, 6);
        newObj.open(5, 5);

        // System.out.println(newObj.ufObj.count());
        // System.out.println(newObj.numberOfOpenSites());
        // System.out.println(Arrays.toString(newObj.openArray));
        // System.out.println(newObj.percolates());

    }
}
