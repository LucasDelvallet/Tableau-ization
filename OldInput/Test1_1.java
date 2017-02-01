package test;

public class Test {
	private static int partition(Comparable[] a, int lo, int hi) {  
	    int i = lo;
	    int j = hi+1;
	    Comparable v = a[lo];
	    while (true) {
	        while (less(a[++i], v)) {
	            if (i == hi) break;
	        }
	        while (less(v, a[--j])) {
	            if (j == lo) break;
	        }
	        if (i >= j) break;
	        exch(a, i, j);
	    }
	    exch(a, lo, j);
	    return j;
	}
}
