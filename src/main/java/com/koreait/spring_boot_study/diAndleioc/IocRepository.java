package com.koreait.spring_boot_study.diAndleioc;

import java.util.List;

public class DiRepository {


    public List<Integer> getScores() {
        return scores;
    }

    /*싱글톤패턴 적용 -> 객체 하나만 생성해서 돌려쓰기.
            1. 생성자 외부 접근을 private로 방어.
            2. 자기 자신의 타입을 static 필드로 가짐.
            3. 외부접근이 가능한 static 메서드로 단 하나의 객체만 사용하게 설계.
            */
    private List<Integer> scores = List.of(100,90,80,70); // DB대체용 데이터.

    private static DiRepository instance;
    private DiRepository(){ }
        public static DiRepository getInstance(){
            if(instance == null)   instance = new DiRepository();
            return instance;
    }
}
