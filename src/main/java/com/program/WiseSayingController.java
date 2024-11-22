package com.program;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private WiseSayingService wsService = new WiseSayingService();

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령 ) ");
            String command = sc.next();

            if (command.startsWith("삭제?id=")) {
                wiseSayingDelete(sc, command);
                continue;
            }

            if (command.startsWith("수정?id=")) {
                wiseSayingUpdate(sc, command);
                continue;
            }

            switch (command) {
                case "등록":
                    wiseSayingRegister(sc);
                    break;

                case "목록":
                    wiseSayingList();
                    break;

                case "빌드":
                    wiseSayingBuild();
                    break;

                case "종료":
                    System.out.println("종료되었습니다");
                    sc.close();
                    return;

                default:
                    System.out.println("유효하지 않은 명령입니다.");
            }
        }
    }

    private void wiseSayingDelete(Scanner sc, String command) {
        String idStr = command.replace("삭제?id=", "");
        try {
            int indexDelete = Integer.parseInt(idStr);
            if (wsService.deleteWiseSaying(indexDelete)) {
                System.out.println(indexDelete + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(indexDelete + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 번호입니다.");
        }
    }

    private void wiseSayingUpdate(Scanner sc, String command) {
        String idStr = command.replace("수정?id=", "");
        try {
            int indexUpdate = Integer.parseInt(idStr);
            WiseSaying existing = wsService.getWsByIndex(indexUpdate);
            if (existing == null) {
                System.out.println(indexUpdate + "번 명언은 존재하지 않습니다.");
                return;
            }

            System.out.print("명언: ");
            String wiseSayingUpdate = sc.next();
            System.out.print("작가: ");
            String authorUpdate = sc.next();

            if (wsService.updateWiseSaying(indexUpdate, wiseSayingUpdate, authorUpdate)) {
                System.out.println(indexUpdate + "번 명언이 수정되었습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 번호입니다.");
        }
    }

    private void wiseSayingRegister(Scanner sc) {
        System.out.print("명언: ");
        String wiseSayingSc = sc.next();
        System.out.print("저자: ");
        String authorSc = sc.next();
        wsService.addWiseSaying(wiseSayingSc, authorSc);
        System.out.println("명언이 등록되었습니다.");
    }

    private void wiseSayingList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-------------------");
        List<WiseSaying> wiseSayings = wsService.getAllWiseSaying();
        for (WiseSaying wiseSaying : wiseSayings) {
            System.out.println(wiseSaying.toString());
        }
    }

    private void wiseSayingBuild() {
        wsService.buildDataFile();
        System.out.println("data.json 파일이 갱신되었습니다.");
    }

    public static void main(String[] args) {
        new WiseSayingController().run();
    }
}
