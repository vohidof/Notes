package Utils

import Model.NoteModel
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, ConstantDb.DB_NAME, null, ConstantDb.TABLE_VERSION),
    DbServiceInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table " + ConstantDb.TABLE_NAME + " (" + ConstantDb.ID + " integer not null primary key autoincrement unique, " + ConstantDb.TITLE + " text not null unique, " + ConstantDb.DESC + " text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists ${ConstantDb.TABLE_NAME}")
        onCreate(db)
    }

    override fun addNotes(noteModel: NoteModel) {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(ConstantDb.TITLE, noteModel.title)
        contentValue.put(ConstantDb.DESC, noteModel.desc)
        database.insert(ConstantDb.TABLE_NAME, null, contentValue)
        database.close()
    }

    override fun deleteNotes(noteModel: NoteModel) {
        val database = this.writableDatabase
        database.delete(ConstantDb.TABLE_NAME, "${ConstantDb.ID} = ?", arrayOf("${noteModel.id}"))
        database.close()
    }

    override fun updateNotes(noteModel: NoteModel): Int {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(ConstantDb.ID, noteModel.id)
        contentValue.put(ConstantDb.TITLE, noteModel.title)
        contentValue.put(ConstantDb.DESC, noteModel.desc)
        return database.update(ConstantDb.TABLE_NAME, contentValue, "${ConstantDb.ID} = ?", arrayOf("${noteModel.id}"))
    }

    @SuppressLint("Recycle")
    override fun getAllNotes(): List<NoteModel> {
        val list = ArrayList<NoteModel>()
        val query = "select * from ${ConstantDb.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)

                val user = NoteModel(id, name, number)
                list.add(user)

            } while (cursor.moveToNext())

        }
        return list
    }

}