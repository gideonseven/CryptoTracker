package com.gt.cryptotracker.crypto.presentation.coin_list

import com.gt.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}