package com.program.repository;


import com.program.entity.WiseSaying;

import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {
    void add(WiseSaying wiseSaying);

    List<WiseSaying> findAll();

    boolean removeById(int id);

    Optional<WiseSaying> findById(int id);

    void modify(WiseSaying wiseSaying);
}