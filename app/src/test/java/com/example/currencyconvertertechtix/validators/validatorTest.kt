package com.example.currencyconvertertechtix.validators

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class validatorTest {

    @Test
    fun whenInputIsValidAmt() {
        val amt = "125.4"
        val result = validator.validateAmount(amt)
        assertThat(result).isTrue()
    }

    @Test
    fun whenInputIsValidCurrency() {
        val cur = "USD"
        val result = validator.validateCurrencySymbol(cur)
        assertThat(result).isTrue()
    }

    @Test
    fun whenCurrenciesAreNotFormatted() {
        val query = "usd"
        val result = validator.validateCurrencySymbol(query)
        assertThat(result).isTrue()
    }

    @Test
    fun whenAmountIsLargerThanTwelveDigits() {
        val query = "124357497354390754395045.4"
        val result = validator.validateAmount(query)
        assertThat(result).isTrue()
    }

    @Test
    fun whenAmountContainsLetters() {
        val query = "123e"
        val result = validator.validateAmount(query)
        assertThat(result).isTrue()
    }
}