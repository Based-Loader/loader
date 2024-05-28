package com.kloader.api.plugin

import com.kloader.loader.KLoader

interface LoaderPlugin {
    fun apply(loader: KLoader)
}