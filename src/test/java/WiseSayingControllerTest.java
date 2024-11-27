import com.program.controller.WiseSayingController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


public class WiseSayingControllerTest {
//    @Test
//    @DisplayName("== 명언 앱 ==")
//    public void testExit() {
//        Scanner scanner = TestUtil.getScanner("종료");
//        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();
//
//        WiseSayingController wiseSayingController = new WiseSayingController();
//        wiseSayingController.run(scanner);
//
//        String output = outputStream.toString();
//
//        TestUtil.clearSetOutToByteArray(outputStream);
//        System.out.println(output);
//        assertThat(output)
//                .contains("== 명언 앱 ==");
//    }

    @Test
    @DisplayName("목록 값 확인하기")
    public void testList() {
        Scanner scanner = TestUtil.getScanner("""
                목록
                종료
                """.stripIndent());
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        WiseSayingController wiseSayingController = new WiseSayingController();
        wiseSayingController.run(scanner);

        String output = outputStream.toString();

        TestUtil.clearSetOutToByteArray(outputStream);
        System.out.println(output);
        assertThat(output)
                .contains("명령) ")
                .contains("----------------------")
                .contains("2 / 키케로 / 삶이 있는 한 희망은 있다.")
                .contains("1 / 이순신 장군 / 나의 죽음을 적들에게 알리지 말라.");
    }

    @Test
    @DisplayName("등록 상태 확인하기")
    public void testAdd() {
        // 테스트 입력값 설정
        Scanner scanner = TestUtil.getScanner("""
        등록
        새로운 명언
        저자는 비밀
        종료
        """.stripIndent());
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        WiseSayingController wiseSayingController = new WiseSayingController();
        wiseSayingController.run(scanner);

        String output = outputStream.toString();

        TestUtil.clearSetOutToByteArray(outputStream);

        System.out.println(output);

        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("3번 명언이 등록되었습니다.");
    }


}