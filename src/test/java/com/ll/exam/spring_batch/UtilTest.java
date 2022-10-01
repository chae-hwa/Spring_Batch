package com.ll.exam.spring_batch;

import com.ll.exam.spring_batch.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest {

    @Test
    @DisplayName("특정 월에 마지막 날을 계산")
    void t1(){
        int endDayOf__2022_09 = Util.date.getEndDayOf(2022, 9);

        assertThat(endDayOf__2022_09).isEqualTo(30);


        int endDayOf__2022_08 = Util.date.getEndDayOf(2022, 8);

        assertThat(endDayOf__2022_08).isEqualTo(31);
    }

    @Test
    @DisplayName("문자열로 LocalDateTime 객체 만들기")
    void t2(){
        LocalDateTime localDateTime1 = Util.date.parse("2022-09-01 23:59:59.999999");
        assertThat(localDateTime1.toString().length()).isEqualTo(26);

        LocalDateTime localDateTime2 = Util.date.parse("yyyy-MM-dd HH:mm:ss", "2022-09-01 23:59:59");
        assertThat(localDateTime2.toString().length()).isEqualTo(19);
    }
}
