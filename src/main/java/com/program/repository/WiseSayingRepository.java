package com.program.repository;

import com.program.entity.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class WiseSayingRepository {
    private static final String DB_DIR = "db/wiseSaying/";
    private static final String LAST_ID_DIR = DB_DIR + "lastId.txt";
    private List<WiseSaying> wiseSayings = new ArrayList<>();
    private int currentIndex = 1;
    File[] files;
    //레퍼지토리 로드
    public WiseSayingRepository() {
        initializeDirectory();
        loadLastIndex();
    }
    //디렉토리 찾기
    private void initializeDirectory() {
        File directory = new File(DB_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
            }
        }

        File lastIdFile = new File(LAST_ID_DIR);
        if (!lastIdFile.exists()) {
            try {
                boolean created = lastIdFile.createNewFile();
                if (created) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastIdFile))) {
                        writer.write("0");
                    }
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    //마지막 index 번호 찾기
    private void loadLastIndex() {
        File file = new File(LAST_ID_DIR);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    currentIndex = Integer.parseInt(line.trim()) + 1;
                }
            } catch (IOException e) {
                e.getMessage();
            } catch (NumberFormatException e) {
                e.getMessage();
            }
        }
    }

    // laseindex로 값 저장하기
    private void saveLastIndex() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_ID_DIR))) {
            writer.write(String.valueOf(currentIndex - 1));
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void addWiseSaying(WiseSaying wiseSaying) {
        wiseSaying.setIndex(currentIndex++);
        wiseSayings.add(wiseSaying);
        saveToFile(wiseSaying);
        saveLastIndex();
    }

    // Gson파일 사용하지 않고 save하는걸로 수정
    private void saveToFile(WiseSaying wiseSaying) {
        String json = "{\n" +
                "  \"index\": " + wiseSaying.getIndex() + ",\n" +
                "  \"wiseSay\": \"" + wiseSaying.getWiseSay() + "\",\n" +
                "  \"author\": \"" + wiseSaying.getAuthor() + "\"\n" +
                "}";

        String fileName = DB_DIR + wiseSaying.getIndex() + ".json";

        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write(json);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public List<WiseSaying> getAllWiseSaying() {
        return wiseSayings;
    }

    //현재 WiseSaying 인덱스 get하기
    public WiseSaying getWsByIndex(int index) {
        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getIndex() == index)
                .findFirst()
                .orElse(null);
    }

    //값 업데이트
    //이전 등록 값 수정 불가 업데이트 예정
    public void updateWiseSaying(int index, String newWiseSaying, String newAuthor) {
        WiseSaying wiseSaying = getWsByIndex(index);
        if (wiseSaying != null) {
            wiseSaying.setWiseSay(newWiseSaying);
            wiseSaying.setAuthor(newAuthor);
            saveToFile(wiseSaying);
        }
    }
    //값 삭제하기
    public void deleteWiseSaying(int index) {
        wiseSayings.removeIf(wiseSaying -> wiseSaying.getIndex() == index);
        deleteFile(index);
    }
    private void deleteFile(int index) {
        File file = new File(DB_DIR + index + ".json");
        if (file.exists()) {
            if (!file.delete()) {
            }
        }
    }

    //data.json 파일 생성 <<- chatgpt 사용한 부분
    public void buildDataFile() {
        String dataFileName = DB_DIR + "data.json";

        fileCatch();
        if (files == null || files.length == 0) {
            return;
        }

        try (FileWriter writer = new FileWriter(new File(dataFileName))) {
            writer.write("[\n"); // JSON 배열 시작
            for (int i = 0; i < files.length; i++) {
                StringBuilder fileContent = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(files[i]))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line);
                    }
                } catch (IOException e) {
                    continue;
                }

                writer.write(fileContent.toString());
                if (i < files.length - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]"); // JSON 배열 종료
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void fileCatch(){
        File directory = new File(DB_DIR);

        // db/wiseSaying 디렉토리의 모든 JSON 파일을 읽어옴
        files = directory.listFiles((dir, name) -> name.endsWith(".json") && !name.equals("data.json"));
    }
    public void searchKeyWord(String contentType){
        switch (contentType){
            case "content&keyword": break;
            case "author&keyword": break;
        }
    }
}