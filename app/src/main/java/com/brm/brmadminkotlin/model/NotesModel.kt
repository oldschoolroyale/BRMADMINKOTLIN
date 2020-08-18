package com.brm.brmadminkotlin.model

class NotesModel {
    var address: String? = null
    var name: String? = null
    var time_end: String? = null
    var time_start: String? = null
    var type: String? = null
    var visit: String? = null
    var id: String? = null

    constructor()
    constructor(
        address: String?,
        name: String?,
        time_end: String?,
        time_start: String?,
        type: String?,
        visit: String?,
        id: String?
    ) {
        this.address = address
        this.name = name
        this.time_end = time_end
        this.time_start = time_start
        this.type = type
        this.visit = visit
        this.id = id
    }


}
