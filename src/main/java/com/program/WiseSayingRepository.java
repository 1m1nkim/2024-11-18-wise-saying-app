package com.program;

import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private static final String DB_DIR = "db/wiseSaying/";
    private static final String LAST_ID_DIR = DB_DIR + "lastId.txt";
    private List<WiseSaying> wiseSayings = new ArrayList<>();
    private int currentIndex = 1;

    public WiseSayingRepository() {
        initializeDirectory();
        loadLastIndex();
    }

    private void initializeDirectory() {
        File directory = new File(DB_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("디렉토리가 생성되었습니다: " + DB_DIR);
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
                    System.out.println("lastId.txt 파일이 생성되었습니다.");
                }
            } catch (IOException e) {
                System.out.println("lastId.txt 파일 생성 중 오류 발생: " + e.getMessage());
            }
        }
    }

    private void loadLastIndex() {
        File file = new File(LAST_ID_DIR);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    currentIndex = Integer.parseInt(line.trim()) + 1;
                }
            } catch (IOException e) {
                System.out.println("lastId.txt 파일 읽기 중 오류 발생: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 lastId 값입니다. 초기값 1로 설정합니다.");
                currentIndex = 1;
            }
        }
    }

    private void saveLastIndex() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_ID_DIR))) {
            writer.write(String.valueOf(currentIndex - 1));
        } catch (IOException e) {
            System.out.println("lastId 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public void addWiseSaying(WiseSaying wiseSaying) {
        wiseSaying.setIndex(currentIndex++);
        wiseSayings.add(wiseSaying);
        saveToFile(wiseSaying);
        saveLastIndex();
    }

    private void saveToFile(WiseSaying wiseSaying) {
        Gson gson = new Gson();
        String fileName = DB_DIR + wiseSaying.getIndex() + ".json";
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            gson.toJson(wiseSaying, writer);
        } catch (IOException e) {
            System.out.println("명언 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public List<WiseSaying> getAllWiseSaying() {
        return wiseSayings;
    }

    public WiseSaying getWsByIndex(int index) {
        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getIndex() == index)
                .findFirst()
                .orElse(null);
    }

    public void updateWiseSaying(int index, String newWiseSaying, String newAuthor) {
        WiseSaying wiseSaying = getWsByIndex(index);
        if (wiseSaying != null) {
            wiseSaying.setWiseSay(newWiseSaying);
            wiseSaying.setAuthor(newAuthor);
            saveToFile(wiseSaying);
        }
    }

    public void deleteWiseSaying(int index) {
        wiseSayings.removeIf(wiseSaying -> wiseSaying.getIndex() == index);
        deleteFile(index);
    }

    private void deleteFile(int index) {
        File file = new File(DB_DIR + index + ".json");
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("파일 삭제 실패: " + index + ".json");
            }
        }
    }

    public void buildDataFile() {
        Gson gson = new Gson();
        String fileName = DB_DIR + "data.json";
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            gson.toJson(wiseSayings, writer);
            System.out.println("data.json 파일이 갱신되었습니다.");
        } catch (IOException e) {
            System.out.println("data.json 파일 생성 중 오류 발생: " + e.getMessage());
        }
    }
}