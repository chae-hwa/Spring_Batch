package com.ll.exam.spring_batch;

import com.ll.exam.spring_batch.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
