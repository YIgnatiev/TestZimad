package com.yaroslav.testzimad.callback

import com.yaroslav.testzimad.response.Data

interface CallbackOpenDetail {
    fun openDetail(animal: Data, position: Int)
}