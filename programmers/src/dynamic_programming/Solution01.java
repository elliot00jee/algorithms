package dynamic_programming;

import java.util.*;

public class Solution01 {

    public static void main(String[] args) {
        System.out.println(solution(8, 53));
    }

    public static int solution(int N, int number) {
        HashSet<Integer>[] sets = new HashSet[9];

        for (int m = 1; m <= 8; m++) {
            sets[m] = new HashSet<>();
            for (int j = m - 1; j >= m - j; j--) {
                Iterator iterk = sets[m - j].iterator();

                while (iterk.hasNext()) {
                    int a = (int) iterk.next();
                    Iterator iterj = sets[j].iterator();
                    while (iterj.hasNext()) {
                        int b = (int) iterj.next();

                        sets[m].add(a * b);
                        if (a != 0) {
                            sets[m].add(b / a);
                        }
                        sets[m].add(b - a);
                        sets[m].add(a + b);

                    }
                }
            }

            String s = "";
            for (int l = m; l > 0; l--) {
                s += "" + N;
            }
            sets[m].add(Integer.parseInt(s));
            if (sets[m].contains(number))
                return m;
        }

        return -1;
    }
}
//
//[2] = 2
//[2*2,2/2, 2+2, 2-2, 22] = global 4, 1, 0, 22
//a = [4*2, 4/2, 4+2, ]   --> a --> global