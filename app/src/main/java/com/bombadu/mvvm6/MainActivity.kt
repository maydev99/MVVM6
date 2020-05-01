package com.bombadu.mvvm6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bombadu.mvvm6.ViewModel.RecordViewModel
import com.bombadu.mvvm6.db.Record
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

        button_add_record.setOnClickListener{
            startActivityForResult(
                Intent(this, AddEditRecordActivity::class.java),
                ADD_NOTE_REQUEST
            )
        }

    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
        recordViewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        recordViewModel.getAllRecords().observe(this, Observer { allRecords ->
            allRecords?.let { adapter.setRecords(it) }
        })

        ItemTouchHelper( object  :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.getRecordAt(viewHolder.adapterPosition)?.let { recordViewModel.deleteRecord(it) }
                makeAToast("Note Deleted")
            }
        }).attachToRecyclerView(recycler_view)

        adapter.onItemClick = { pos, _ ->
            val myRecord = adapter.getRecordAt(pos)
            intent = Intent(this, AddEditRecordActivity::class.java)
            intent.putExtra(AddEditRecordActivity.EXTRA_DATE, myRecord!!.date)
            intent.putExtra(AddEditRecordActivity.EXTRA_WEIGHT, myRecord.weight)
            intent.putExtra(AddEditRecordActivity.EXTRA_MOOD, myRecord.mood)
            intent.putExtra(AddEditRecordActivity.EXTRA_ID, myRecord.id)
            startActivityForResult(intent, EDIT_NOTE_REQUEST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val newRecord = Record(
                data.getStringExtra(AddEditRecordActivity.EXTRA_DATE),
                data.getIntExtra(AddEditRecordActivity.EXTRA_WEIGHT, 250),
                data.getIntExtra(AddEditRecordActivity.EXTRA_MOOD, 5)

            )

            recordViewModel.insertRecord(newRecord)
            makeAToast("Note Saved")
        } else {
            makeAToast("Note Not Saved")
        }

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditRecordActivity.EXTRA_ID, -1)
            if (id == -1) {
                makeAToast("Note Cannot be updated")
                return
            }

            val updateDate = data?.getStringExtra(AddEditRecordActivity.EXTRA_DATE)
            val updateWeight = data?.getIntExtra(AddEditRecordActivity.EXTRA_WEIGHT, 100)
            val updateMood = data?.getIntExtra(AddEditRecordActivity.EXTRA_MOOD, 5)
            val updatedRecord = Record(updateDate!!, updateWeight!!, updateMood!!)
            if (id != null) {
                updatedRecord.id = id
            }

            recordViewModel.updateRecord(updatedRecord)
            makeAToast("Note Updated")
        } else {
            makeAToast(" Note Saved")
        }


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

    companion object {
        private const val ADD_NOTE_REQUEST = 1
        private const val EDIT_NOTE_REQUEST = 2
    }

    private fun makeAToast(tMessage: String) {
        Toast.makeText(this, tMessage, Toast.LENGTH_SHORT).show()
    }
}
