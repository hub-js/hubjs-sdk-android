package org.hubjs.tracker.tools;

import androidx.annotation.Nullable;

import java.math.BigDecimal;

public class CurrencyFormatter {
    @Nullable
    public static String priceString(@Nullable Integer cents) {
        if (cents == null) return null;
        return new BigDecimal(cents).movePointLeft(2).toPlainString();
    }
}
