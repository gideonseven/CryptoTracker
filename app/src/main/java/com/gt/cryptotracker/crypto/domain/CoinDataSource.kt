package com.gt.cryptotracker.crypto.domain

import com.gt.cryptotracker.core.domain.util.NetworkError
import com.gt.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}