package com.daifuku.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class RENDIMENTO_MENSAL {
    public static final BigDecimal PF = BigDecimal.TEN.divide(new BigDecimal(100));
    public static final BigDecimal PJ = PF.multiply(new BigDecimal(102).divide(new BigDecimal(100)));

}
