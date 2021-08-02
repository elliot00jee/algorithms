### 풀이
N = 5 이고, number = 12 일 경우, N을 사용한 횟수를 m이라고 할 때
1) m = 1 
- [5]
2) m = 2
- [5+5, 5-5, 5*5, 5/5, 55] = [10, 0, 25, 1, 55] 
3) m = 3
- [5+5+5, 5+5-5, 5+5*5, 5+5/5, 55/5, ...]
- 일단 여기까지 해보면, (이게 DP 문제이기도 하고) m=3을 처음부터 다 계산하는게 아니라, 
  계산된 m=2 하고 m=1을 사용해서 풀 수 있을 것 같단 생각이 든다.
- [(m=3)] = [(m=2 배열) [*,/,+,-,숫자붙이기] (m=1 배열)]
- 계산 하다보면 분명히 중복이 발생한다. 중복은 계산에서 빼고 저장해도 될 듯하다. ➔ HashSet<Integer>
- m을 늘려가면서, 이전 m 값에서 계산된 셋을 가지고 계산하려면 모두 저장해서 갖고 있어야 할 듯 ➔ HashSet<Integer>[] sets
4) m = 4
- [(m=4)] = [(m=3) 배열 [*,/,+,-,숫자붙이기] (m=1 배열)]
- 이 경우, (5○5○5)○5 연산에서 앞의 3개의 5가 먼저 계산되고, 마지막 5가 가장 늦게 계산된다.
위 경우만 포함할 경우, (5○5)○(5○5) 경우의 수가 빠지게 된다.
- [(m=4)] = [(m=3) 배열 [*,/,+,-,숫자붙이기] (m=1 배열)] + [(m=2) 배열 [*,/,+,-,숫자붙이기] (m=2 배열)]
- 다시 풀어보니까 이 때 왜 나머지 경우의 수를 놓쳤을까 싶다..

```java
public class Solution01 {
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
```

### 결과 
- 테스트 8 하나를 통과하지 못했다.
- 힌트를 보다보니, 내 코드는 N=8, number=53 일 경우는 (8*8)-(88/8) 로써, 최소값 m=5 인데 
이 경우를 찾아내지 못했다.(6으로 오류)
- 즉, m=5 일 때, (m=4, m=1), (m=3, m=2) 만 계산해가지고는 위 경우인 (m=2, m=3)을 계산할 수 없는 것
- m=5 일 때, (m=4,m=1), (m=3, m=2), (m=2, m=3), (m=1, m=4)를 모두 계산해야 하는 것
- `(5○5○5)○5 경우만 포함할 경우, (5○5)○(5○5) 경우의 수가 빠지게 된다.` 여기에서 추가로, 5○(5○5○5)
도 빼먹지 말았어야 했는데 왜 그랬을까? 

### 고친 결과 
```java
class Solution {
    public static int solution(int N, int number) {
        HashSet<Integer>[] sets = new HashSet[9];

        for (int m = 1; m <= 8; m++) {
            sets[m] = new HashSet<>();
            for (int i = m - 1; i >= 1; i--) {
                Iterator iterk = sets[m - i].iterator();

                while (iterk.hasNext()) {
                    int a = (int) iterk.next();
                    Iterator iterj = sets[i].iterator();
                    while (iterj.hasNext()) {
                        int b = (int) iterj.next();

                        sets[m].add(a * b);
                        if (b != 0) {
                            sets[m].add(a / b);
                        }
                        sets[m].add(a - b);
                        sets[m].add(a + b);

                    }
                }

            }

            String s = "";
            for (int i = m; i > 0; i--) {
                s += "" + N;
            }
            sets[m].add(Integer.parseInt(s));
            if (sets[m].contains(number))
                return m;
        }

        return -1;
    }
}
```



