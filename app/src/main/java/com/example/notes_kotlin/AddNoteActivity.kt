package com.example.notes_kotlin

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.row.*
import java.lang.Exception

class AddNoteActivity : AppCompatActivity() {

    var dbTable = "notes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)


        try {
            val bundle: Bundle? = intent.extras;
            id = bundle!!.getInt("id", 0)
            if (id != 0) {
                titleEdit.setText(bundle.getString("title"))
                descEdit.setText(bundle.getString("desc"))
            }
        } catch (ex: Exception) {

        }
    }

    fun addNoteFunc(view: View) {
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("title", titleEdit.text.toString())
        values.put("description", descEdit.text.toString())

        if (id == 0) {
            var id = dbManager.insert(values)
            if (id > 0) {
                Toast.makeText(this, "Nota salva com sucesso...", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao salvar nota...", Toast.LENGTH_SHORT).show()
            }
        } else {
            var selectionArgs = arrayOf(id.toString())
            val id = dbManager.upate(values, "id = ?", selectionArgs)

            if (id > 0) {
                Toast.makeText(this, "Nota salva com sucesso...", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao salvar nota...", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
