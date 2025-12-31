package com.gt.cryptotracker.crypto.data.networking

import com.gt.cryptotracker.core.data.networking.constructUrl
import com.gt.cryptotracker.core.data.networking.safeCall
import com.gt.cryptotracker.core.domain.util.NetworkError
import com.gt.cryptotracker.core.domain.util.Result
import com.gt.cryptotracker.core.domain.util.map
import com.gt.cryptotracker.crypto.data.mappers.toCoin
import com.gt.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.gt.cryptotracker.crypto.domain.Coin
import com.gt.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map {
                it.toCoin()
            }
        }
    }
}