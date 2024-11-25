package com.program.service;

import com.program.entity.WiseSaying;
import com.program.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private WiseSayingRepository wsRepository = new WiseSayingRepository();

    public void addWiseSaying(String wiseSay, String author) {
        wsRepository.addWiseSaying(new WiseSaying(wiseSay, author, 0));
    }

    public List<WiseSaying> getAllWiseSaying() {
        return wsRepository.getAllWiseSaying();
    }

    public WiseSaying getWsByIndex(int index) {
        return wsRepository.getWsByIndex(index);
    }

    public boolean updateWiseSaying(int index, String newWiseSaying, String newAuthor) {
        WiseSaying wiseSaying = getWsByIndex(index);
        if (wiseSaying == null) {
            return false;
        }
        wsRepository.updateWiseSaying(index, newWiseSaying, newAuthor);
        return true;
    }

    public boolean deleteWiseSaying(int index) {
        WiseSaying wiseSaying = getWsByIndex(index);
        if (wiseSaying == null) {
            return false;
        }
        wsRepository.deleteWiseSaying(index);
        return true;
    }

    public void buildDataFile() {
        wsRepository.buildDataFile();
    }
}