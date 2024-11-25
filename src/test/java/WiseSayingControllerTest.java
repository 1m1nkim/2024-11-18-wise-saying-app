import com.program.controller.WiseSayingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class WiseSayingControllerTest {
    WiseSayingController wiseSayingController;
    ByteArrayOutputStream outputStream;

    @BeforeEach
    void beforeEach() {
        wiseSayingController = new WiseSayingController();
        // System.out 출력을 캡처하기 위한 ByteArrayOutputStream 설정
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    @DisplayName("등록")
    void t3() {
        // 명령어 입력을 시뮬레이션하기 위한 Scanner 객체
        Scanner sc = new Scanner("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        // run 메서드를 호출하여 출력
        wiseSayingController.run(sc);

        // 캡처한 출력을 문자열로 변환
        String out = outputStream.toString();

        // 출력 내용에 대한 검증
        assertThat(out, containsString("명언: "));
        assertThat(out, containsString("작가: "));
        assertThat(out, containsString("1번 명언이 등록되었습니다."));
        System.setOut(new PrintStream(outputStream)); // System.out을 outputStream으로 리다이렉트
    }
}
