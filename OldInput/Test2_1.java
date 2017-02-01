package test;

public class Test {
	private static int partition(int[] bob, int left, int right) {  
	    int x = left;
	    int y = right+1;
	    for (;;) {
	        while (less(bob[left], bob[--y]))
	            if (y == left) break;
	        while (less(bob[++x], bob[left]))
	            if (x == right) break;
	        if (x >= y) break;
	        swap(bob, y, x);
	    }
	    swap(bob, y, left);
	    return y;
	}
}
