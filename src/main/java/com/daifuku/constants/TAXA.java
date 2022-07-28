package com.daifuku.constants;

import java.math.BigDecimal;

public final class TAXA {
    public static final BigDecimal SAQUE = new BigDecimal(5).divide(new BigDecimal(1_000));
    public static final BigDecimal TRANSF = SAQUE;
}
