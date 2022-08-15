package com.example.currencyconvertertechtix.validators

object validator {

    fun validateAmount(amt: String): Boolean {
        if (amt.count() > 12) {
            return false
        }
        if (amt == "") {
            return false
        }
        if (!(amt.matches("[+-]?([0-9]*[.])?[0-9]+".toRegex()))) {
            return false
        }
        return true
    }

    fun validateCurrencySymbol(value: String): Boolean {
        if (value.count() > 3) {
            return false
        }
        if (value == "") {
            return false
        }
        if (!(value.matches("[A-Z]+".toRegex()))) {
            return false
        }
        return true
    }

}