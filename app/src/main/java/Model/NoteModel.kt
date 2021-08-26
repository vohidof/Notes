package Model

class NoteModel {
    var id:Int? = null
    var title = ""
    var desc:String? = null

    constructor(id: Int?, title: String, desc: String?) {
        this.id = id
        this.title = title
        this.desc = desc
    }

    constructor(title: String, desc: String?) {
        this.title = title
        this.desc = desc
    }

    constructor()
}