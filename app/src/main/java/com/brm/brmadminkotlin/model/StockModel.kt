package com.brm.brmadminkotlin.model

class StockModel {
    var name: String? = null
    var quantity: Int? = null

    constructor()
    constructor(name: String?, quantity: Int?) {
        this.name = name
        this.quantity = quantity
    }

}