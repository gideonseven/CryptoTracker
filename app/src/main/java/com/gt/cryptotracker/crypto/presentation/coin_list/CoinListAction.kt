package com.gt.cryptotracker.crypto.presentation.coin_list

import com.gt.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
}