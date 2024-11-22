import com.program.WiseSaying;
import com.program.WiseSayingController;
import com.program.WiseSayingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class WiseSayingControllerTest {

    @Mock
    private WiseSayingService wsService;

    @InjectMocks
    private WiseSayingController wsController;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testWiseSayingRegister() {
        // 준비된 서비스 동작 설정
        doNothing().when(wsService).addWiseSaying(anyString(), anyString());

        // 입력값 시뮬레이션
        String input = "등록\n명언: Live and let live\n저자: John\n종료\n";
        simulateInput(input);

        // 실행
        wsController.run();

        // 출력값 검증
        assertTrue(outContent.toString().contains("명언이 등록되었습니다."));
    }

    @Test
    public void testWiseSayingList() {
        // 준비된 서비스 동작 설정
        when(wsService.getAllWiseSaying()).thenReturn(List.of(
                new WiseSaying("Live and let live", "John", 1),
                new WiseSaying("Carpe diem", "Horace", 2)
        ));

        // 입력값 시뮬레이션
        String input = "목록\n종료\n";
        simulateInput(input);

        // 실행
        wsController.run();

        // 출력값 검증
        assertTrue(outContent.toString().contains("번호 / 작가 / 명언"));
        assertTrue(outContent.toString().contains("1 / John / Live and let live"));
        assertTrue(outContent.toString().contains("2 / Horace / Carpe diem"));
    }

    @Test
    public void testWiseSayingDelete() {
        // 준비된 서비스 동작 설정
        doNothing().when(wsService).deleteWiseSaying(anyInt());

        // 입력값 시뮬레이션
        String input = "삭제?id=1\n종료\n";
        simulateInput(input);

        // 실행
        wsController.run();

        // 출력값 검증
        assertTrue(outContent.toString().contains("1번 명언이 삭제되었습니다."));
    }

    @Test
    public void testWiseSayingUpdate() {
        // 준비된 서비스 동작 설정
        doNothing().when(wsService).updateWiseSaying(anyInt(), anyString(), anyString());

        // 입력값 시뮬레이션
        String input = "수정?id=1\n명언: Carpe diem\n작가: Horace\n종료\n";
        simulateInput(input);

        // 실행
        wsController.run();

        // 출력값 검증
        assertTrue(outContent.toString().contains("1번 명언이 수정되었습니다."));
    }

    // 입력값을 시뮬레이션하는 메서드
    private void simulateInput(String input) {
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
    }
}
