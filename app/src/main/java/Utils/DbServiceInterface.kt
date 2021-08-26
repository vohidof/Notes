package Utils

import Model.NoteModel

interface DbServiceInterface {
    fun addNotes(noteModel: NoteModel)
    fun deleteNotes(noteModel: NoteModel)
    fun updateNotes(noteModel: NoteModel): Int
    fun getAllNotes(): List<NoteModel>
}