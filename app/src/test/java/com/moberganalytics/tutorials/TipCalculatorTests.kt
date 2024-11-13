package com.moberganalytics.tutorials

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.NumberFormat

/*
Here we implement a "local test" which is intended to test aspecific function/class

in general, when you write tests:
- write tests as methods
- annotate methods with @Test
- end with a Assert
 */
class TipCalculatorTests {
    @Test
    fun caclulateTip_20PercentNoRoundUP() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount, tipPercent, false)
        assertEquals(expectedTip, actualTip)
    }
}