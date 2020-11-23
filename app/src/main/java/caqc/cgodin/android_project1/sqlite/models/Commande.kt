package caqc.cgodin.android_project1.sqlite.models

import caqc.cgodin.android_project1.sqlite.SqlEntity

class Commande() : SqlEntity(Commande::class) {

    var restoId: String? = null;
    var email: String? = null;
    var commande: String? = null;

    constructor(rid: String, email: String, cmd: String) : this() {
        restoId = rid;
        this.email = email
        commande = cmd;
    }
}