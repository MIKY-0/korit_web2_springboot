package com.koreait.spring_boot_study.diAndleioc;

import java.util.List;

public class DiService {
    //싱글톤 적용
    private DiRepository diRepository;
    private static DiService instance;

    private DiService(DiRepository diRepository) { // priavate DiRepository diRepository 필드 때문에 생성
        this.diRepository = diRepository;
    }

    public static DiService getInstance(DiRepository diRepository){ // getInstance를 호출한 쪽에서 DiRepo 주입.
        if(instance == null)   instance = new DiService(diRepository);
        return instance;
    }

    public int getTotal(){ // 총점 구하는 메서드
        List<Integer> scores = diRepository.getScores();
        int total = 0;
        for(int s : scores)  total += s;
        return total;
    }
    public double getAvg(){ // 평균연산
        List<Integer> scores = diRepository.getScores();
        double avg = (double) getTotal() / scores.size();
         return avg;
    }
}
