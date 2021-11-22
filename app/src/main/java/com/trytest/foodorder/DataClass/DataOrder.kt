package com.trytest.foodorder.DataClass

import com.trytest.foodorder.models.Menus

data class DataOrder(
    var addressCur:String,
    var phoneCur:String,
    var ArrOrder: List<Menus?>?,
    )
