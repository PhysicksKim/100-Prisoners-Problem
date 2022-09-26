import java.util.*;

public class main {
    // I found this riddle from the Below Links
    // Veritasium video (https://www.youtube.com/watch?v=iSNsgj1OCLA)
    // Korean Version (https://www.youtube.com/watch?v=PE4vLbyOgw0)

    public static void main(String[] args) {
        int size = 100;
        int success = 0;
        int fail = 0;
        int iterTimes = 50;

        for(int i = 0 ; i < iterTimes ; i++) {
            if(simulatePrisonRiddle(size, true)){
                success++;
            } else {
                fail++;
            }
        }

        System.out.println("성공 횟수 : " + success);
        System.out.println("실패 횟수 : " + fail);
        System.out.println("성공 확률 : " + ((double)success/(double)iterTimes));
    }

    public static boolean simulatePrisonRiddle(int size, boolean debug) {
        int[] arr = getNumberBox(size);
        if(debug) {
            printArr(arr);
            printLinkList(getLinks(arr));
        }

        for(int i = 1 ; i<=size ; i++) {
            if(!canFindMyNumber(arr, i)) return false;
        }

        return true;
    }

    public static boolean canFindMyNumber(int[] arr, int myNumber) {
        int tryCount = 0;
        int idx = myNumber;
        int givenChances = arr.length/2;
        while(tryCount < givenChances) {

            if (arr[idx] == myNumber) return true;

            idx=arr[idx];

            tryCount++;
        }

        return false;
    }

    private static int[] getNumberBox(int size) {
        // 0번 인덱스 사용안함

        int[] arr = new int[size+1];

        arr[0] = -1;

        // 1~size 까지 전부 넣음
        LinkedList<Integer> sequentialList = new LinkedList<>();
        for(int i = 1; i<=size ; i++) {
            sequentialList.add(i);
        }

        // 0~size-1 까지 random으로 인덱스 뽑아서 queue에다가 넣음
        LinkedList<Integer> queue = new LinkedList<>();
        Random random = new Random();
        for(int i = size; i>0 ; i--) {
            queue.add(sequentialList.remove(random.nextInt(i)));
        }

        // queue에서 뽑아서 순서대로 arr에 넣음
        for (int i = 1; i < arr.length; i++) {
            Integer temp = queue.poll();
            arr[i] = temp;
        }

        return arr;
    }

    private static LinkedList<LinkedList<Integer>> getLinks(int[] arr) {

        int[] tempArr = arr.clone();
        boolean[] checked = new boolean[tempArr.length];
        checked[0] = true;

        int idx = 1;
        /*
        checked.length = 11 인데
        편의상 0번 인덱스는 사용하지 않으므로
        while 조건문을 <= 말고 <로 해줘도 됨
         */
        int checkedCount = 1;
        LinkedList<LinkedList<Integer>> resultList = new LinkedList<>();
        while (checkedCount < checked.length) {

            if(idx>=tempArr.length) {
                idx = 1;
            }
            if(checked[idx]) {
                idx++;
                continue;
            }

            resultList.add(new LinkedList<>());
            LinkedList<Integer> tempList = resultList.get(resultList.size()-1);

            while (true) {
                tempList.add(idx);
                checked[idx] = true;
                checkedCount++;

                if(tempList.get(0) == tempArr[idx]) {
                    break;
                }

                idx = tempArr[idx];
            }
        }

        return resultList;
    }

    private static void printLinkList(LinkedList<LinkedList<Integer>> list) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i<list.size() ; i++) {
            sb.append(i+1).append("번째 링크 (길이 ").append(list.get(i).size()).append(")\n");
            for(int j = 0 ; j<list.get(i).size() ; j++) {
                sb.append(list.get(i).get(j)).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private static void printArr(int[] arr) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append("------------------ { PRINT ARR } ------------------\n");
        for (int i = 1; i < arr.length; i++) {
            sb.append(arr[i]).append(" ");
        }
        sb.append("\n---------------------------------------------------");

        System.out.println(sb);
    }
}
