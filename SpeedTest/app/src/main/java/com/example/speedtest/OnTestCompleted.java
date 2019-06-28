package com.example.speedtest;

import java.math.BigDecimal;

public interface OnTestCompleted {

    void updateInfo(BigDecimal rate);
}
