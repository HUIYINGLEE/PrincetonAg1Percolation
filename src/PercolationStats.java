import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Huiying Li on 29/11/2016.
 */
public class PercolationStats {
    private double[] threshold;
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        threshold=new double[trials];
        // loop T times
        for (int i=0; i< trials; i++){
            int count=0;
            Percolation p=new Percolation(n);
            while(!p.percolates()){
                int row=StdRandom.uniform(1, n+1);
                int col=StdRandom.uniform(1, n+1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    count+=1;
                }
            }
            threshold[i]=(double) (count)/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-1.96*(stddev()/Math.sqrt(threshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){

        return mean()+1.96*(stddev()/Math.sqrt(threshold.length));
    }

    // test client (described below)
    public static void main(String[] args){
        int i = Integer.parseInt(args[0]);
        int j = Integer.parseInt(args[1]);
        PercolationStats pls=new PercolationStats(i,j);
        System.out.printf("mean                     = %f\n", pls.mean());
        System.out.printf("stddev                   = %f\n", pls.stddev());
        System.out.printf("95%% confidence Interval  = %f, %f\n",
                pls.confidenceLo(), pls.confidenceHi());
    }
}
