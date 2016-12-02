import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Huiying Li on 29/11/2016.
 */
public class Percolation {
    private int[][] indexes;
    private int size;
    private int N;
    private boolean[][] opened;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wqu1;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        N=n;
        size=n*n;
        indexes= new int[n][n];
        opened= new boolean[n][n];
        // initialize wqu with size of (size+2). The two stands for the two virtual root.
        wqu=new WeightedQuickUnionUF(size+2);
        //create another wqu in order to prevent the unfilled site become filled through
        // connecting to the bottom virtual root when it is percolate.
        //the way to prevent fault filled site is to get ride of the bottom virtual root
        // in the wqu1. Therefore the wqu1 is initialized with size of (size+1) instead of
        // (size+2).
        wqu1=new WeightedQuickUnionUF(size+1);
        // below codes are use to fill the indexes array and the opened array.
        int index=0;
        for (int r=0; r< n; r++){
            for (int c=0; c< n; c++){
                indexes[r][c]=index;
                //connect the first row with the top virtual root.
                if (r==0){
                    wqu.union(indexes[0][c], size);
                    wqu1.union(indexes[0][c], size);
                }
                //connect the last row with the top virtual root.
                if(r==n-1){
                    wqu.union(indexes[n-1][c], size+1);
                }
                index++;
                opened[r][c]=false;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col){
        if (row <= 0 || col <= 0) throw new IndexOutOfBoundsException();
        opened[row-1][col-1]=true;
        //union the neighbour opened site.
        //up site
        if (row-2>-1 && opened[row-2][col-1]){
            wqu.union(indexes[row-2][col-1], indexes[row-1][col-1]);
            wqu1.union(indexes[row-2][col-1], indexes[row-1][col-1]);
        }
        //down site
        if (row<N && opened[row][col-1]){
            wqu.union(indexes[row][col-1], indexes[row-1][col-1]);
            wqu1.union(indexes[row][col-1], indexes[row-1][col-1]);
        }
        //right site
        if (col<N && opened[row-1][col]){
            wqu.union(indexes[row-1][col], indexes[row-1][col-1]);
            wqu1.union(indexes[row-1][col], indexes[row-1][col-1]);
        }
        //left site
        if (col-2>-1 && opened[row-1][col-2]){
            wqu.union(indexes[row-1][col-2], indexes[row-1][col-1]);
            wqu1.union(indexes[row-1][col-2], indexes[row-1][col-1]);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row <= 0 || col <= 0) throw new IndexOutOfBoundsException();
        return opened[row-1][col-1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) throw new IndexOutOfBoundsException();
        //use wqu1 to remove the unfilled sites which recognised as filled in wqu because
        //they are connected the bottom virtual root when the whole system becomes percolated.
        return wqu.connected(indexes[row-1][col-1], size) && wqu1.connected(indexes[row-1][col-1], size) && isOpen(row,col);

    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.connected(size, size+1);
    }

    // test client (optional)
    /*public static void main(String[] args){

    }*/
}
