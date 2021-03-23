package com.zlrx.netty.nettyresearch.model

import java.time.LocalDate

data class Employee(
    val name: String,
    val birth: LocalDate,
    val address: String
)
