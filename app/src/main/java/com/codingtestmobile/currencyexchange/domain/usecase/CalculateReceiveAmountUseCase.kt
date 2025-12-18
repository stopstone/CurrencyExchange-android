package com.codingtestmobile.currencyexchange.domain.usecase

import javax.inject.Inject

class CalculateReceiveAmountUseCase
    @Inject
    constructor() {
        companion object {
            private const val MIN_AMOUNT = 0.0
            private const val MAX_AMOUNT = 10_000.0
        }

        // 유효성 검사 실패 시 IllegalArgumentException 발생
        operator fun invoke(
            amount: Double,
            rate: Double,
        ): Double {
            require(amount > MIN_AMOUNT && amount <= MAX_AMOUNT) {
                "송금액이 바르지 않습니다"
            }
            return amount * rate
        }
    }
