package com.koreait.spring_boot_study.scheduler;

import com.koreait.spring_boot_study.repository.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component      @RequiredArgsConstructor        @Slf4j
public class RefreshTokenScheduler {
    //주기적으로 만료된 토큰들 삭제.
    private final RefreshTokenMapper refreshTokenMapper;

    /*
    cron = "초 분 시 일 월 요일"
    초 : 0 ~ 59
    분 : 0 ~ 59
    시 : 0 ~ 24
    일 : 1 ~ 31
    월 : 1 ~ 12
    요일 : 1 ~ 7
    매일마다 22시에 메서드를 동작시킨다면.... -> cron = "0 0 22 * * *"
     */
    @Scheduled(cron = "0 0 22 * * *")
    public void cleanExpiredTokens(){
        int successCount = refreshTokenMapper.deleteExpiredTokens();
        log.info("만료된 refresh토큰 정리 : {}건 삭제" , successCount);
    }
}
