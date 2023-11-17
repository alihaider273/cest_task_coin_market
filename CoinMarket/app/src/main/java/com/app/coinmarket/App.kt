package com.app.coinmarket

import android.app.Application
import com.app.coinmarket.internal.Client
import com.app.coinmarket.internal.NetworkInterceptor
import com.app.coinmarket.remote.CoinAPI
import com.app.coinmarket.remote.coin.CoinDataSource
import com.app.coinmarket.remote.coin.CoinFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App:Application(),KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidCoreModule(this@App))

        bind() from singleton { NetworkInterceptor(instance()) }
        bind() from singleton { Client(instance(),instance()) }
        bind() from singleton { CoinAPI(instance()) }

        bind() from provider { CoinDataSource(instance()) }
        bind() from provider { CoinFactory(instance()) }
    }
}