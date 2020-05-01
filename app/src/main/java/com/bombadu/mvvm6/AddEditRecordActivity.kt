package com.bombadu.mvvm6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_edit_record.*
import java.text.SimpleDateFormat
import java.util.*

class AddEditRecordActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATE ="com.bombadu.mvvm6.EXTRA_DATE"
        const val EXTRA_WEIGHT = "com.bombadu.mvvm6.EXTRA_WEIGHT"
        const val EXTRA_MOOD = "com.bombadu.mvvm6.EXTRA_MOOD"
        const val EXTRA_ID = "com.bombadu.mvvm6.EXTRA_ID"
        private var myDate: String? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_record)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)


        weightNumberPicker.minValue = 100
        weightNumberPicker.maxValue = 250
        moodNumberPicker.minValue = 1
        moodNumberPicker.maxValue = 5
        weightNumberPicker.elevation = 20f

        val intent = intent
        if(intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            dateTextView.text = intent.getStringExtra(EXTRA_DATE)
            weightNumberPicker.value = intent.getIntExtra(EXTRA_WEIGHT, 100)
            moodNumberPicker.value = intent.getIntExtra(EXTRA_MOOD, 5)
            myDate = intent.getStringExtra(EXTRA_DATE)


        } else {
            myDate = getTheDate()
            dateTextView.text = myDate
            title = "Add Record"

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                saveRecord()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveRecord() {
        val data = Intent().apply {
            putExtra(EXTRA_DATE, myDate)
            putExtra(EXTRA_WEIGHT, weightNumberPicker.value)
            putExtra(EXTRA_MOOD, moodNumberPicker.value)

            val id = intent.getIntExtra(EXTRA_ID, -1)
            if (id != -1) {
                putExtra(EXTRA_ID, id)
            }
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun getTheDate(): String {
         val cal = Calendar.getInstance()
         val sdf = SimpleDateFormat("MM-dd-yy", Locale.US)
         return sdf.format(cal.time)

    }


}
