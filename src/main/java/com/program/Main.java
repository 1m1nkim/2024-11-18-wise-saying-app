import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("==명언 앱==");
        Scanner scanner = new Scanner(System.in);
        String wiseSayingControl;       // 명령어 입력
        String wiseSayingContent;       // 명언 입력
        String wiseSayingAuth;          // 명언 작가
        int wiseSayingId;               // 명언 아이디 값 > index의 + 1 하여 1부터 시작
        ArrayList<ArrayList<String>> arrayList1 = new ArrayList<>(); // 중첩 ArrayList 사용

        do {
            System.out.print("명령) ");
            wiseSayingControl = inputString();
            if (wiseSayingControl.equals("등록")) {
                ArrayList<String> arrayList2 = new ArrayList<>();

                // ID
                wiseSayingId = arrayList1.size() + 1;
                arrayList2.add(String.valueOf(wiseSayingId));

                // 작가 입력
                System.out.print("작가 : ");
                wiseSayingAuth = inputString();
                arrayList2.add(wiseSayingAuth);

                // 명언 입력
                System.out.print("명언 : ");
                wiseSayingContent = inputString();
                arrayList2.add(wiseSayingContent);

                // 명언 데이터 추가
                arrayList1.add(arrayList2);
            }

            if (wiseSayingControl.equals("목록")) {
                for (int i = 0; i < arrayList1.size(); i++) {
                    ArrayList<String> currentWiseSaying = arrayList1.get(i);
                    System.out.println(
                            currentWiseSaying.get(0) + " / " + currentWiseSaying.get(1) + " / " + currentWiseSaying.get(2)
                    );
                }
            }
        } while (!wiseSayingControl.equals("종료"));

        System.out.println("종료되었습니다.");
    }

    public static String inputString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
