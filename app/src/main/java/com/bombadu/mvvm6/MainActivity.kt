package com.bombadu.mvvm6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bombadu.mvvm6.ViewModel.RecordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recordViewModel: RecordViewModel
    private val adapter = RecordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

        recordViewModel.getAllRecords().observe(this,
        Observer { list ->
            list?.let {
                adapter.setRecords(it)
            }
        })

    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
        recordViewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        recordViewModel.getAllRecords().observe(this, Observer { allRecords ->
            allRecords?.let { adapter.setRecords(it) }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_records -> {
                recordViewModel.deleteAllRecords()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
