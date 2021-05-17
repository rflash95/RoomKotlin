package com.teknorial.roomkotlin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.teknorial.roomkotlin.db.Book
import com.teknorial.roomkotlin.db.BookDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var db: BookDatabase
    lateinit var tvDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inisialisasi TextView
        tvDisplay = findViewById(R.id.tv_display)

        //inisialisasi Database
        db = Room.databaseBuilder(applicationContext, BookDatabase::class.java, "book-db").build()

        //menggunakan coroutine
        GlobalScope.launch {
            //memanggil function di dalam coroutine
            db.bookDao().deleteAll() //<--
            initData()
            diplayData()
        }
    }

    private fun diplayData() {
        val books: List<Book> = db.bookDao().getAllBooks()
        var displayText = ""
        for (book in books) {
            displayText += "${book.title} | ${book.authorName} | Hal : ${book.totalPages}\n"
        }
        tvDisplay.text = displayText
    }

    private fun initData() {
        val book1 = Book("Ada Apa dengan Hujan", "Andre Senja", 30)
        val book2 = Book("Malam Setelah Sore", "Andi Bisa", 40)
        val book3 = Book("Pecahkan Mentari", "Dera", 20)
        //insert data ke database
        db.bookDao().insert(book1,book2,book3) //<--
    }
}