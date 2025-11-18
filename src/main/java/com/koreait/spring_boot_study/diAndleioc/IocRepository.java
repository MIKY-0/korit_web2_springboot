package com.koreait.spring_boot_study.diAndleioc;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IocRepository {


    public List<Integer> getScores() {
        return scores;
    }

    private List<Integer> scores = List.of(100,90,80,70); // DB대체용 데이터.




}
