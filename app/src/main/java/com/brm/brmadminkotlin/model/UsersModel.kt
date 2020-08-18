package com.brm.brmadminkotlin.model

class UsersModel {
    var name: String? = null
    var town: String? = null
    var medications: String? = null
    var region: String? = null
    var version: String? = null
    var manager: String? = null
    var image: String? = null
    var token: String? = null



    constructor()
    constructor(
        name: String?,
        town: String?,
        medications: String?,
        region: String?,
        version: String?,
        manager: String?,
        image: String?,
        token: String?
    ) {
        this.name = name
        this.town = town
        this.medications = medications
        this.region = region
        this.version = version
        this.manager = manager
        this.image = image
        this.token = token
    }


}