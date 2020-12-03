package dev.developintelligence.vmware.vmwaretravel

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import dev.developintelligence.vmware.vmwaretravel.providers.TripsStore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TripsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips_details)

        // we receive the current index by argument
        // we load the data in the screen
        val index = intent.getIntExtra("tripIndex", -1)
        if (index>=0) {
            val trip = TripsStore.instance.trips[index]
            title = trip.name
            findViewById<TextView>(R.id.textNoteDescription).text = (trip.description)
            if(!trip.startDate.isEmpty()){
                findViewById<TextView>(R.id.displayDurationDetails).text =
                        getTimeline(trip.startDate, trip.duration)
            }
            else{
                findViewById<TextView>(R.id.textDurationDetails).visibility = View.GONE;
                findViewById<TextView>(R.id.displayDurationDetails).visibility = View.GONE;
            }
            findViewById<Button>(R.id.buttonDelete).setOnClickListener {
                AlertDialog.Builder(this)
                        .setTitle("Delete a Trip")
                        .setMessage("Are you sure you want to delete this trip? You can't undone this operation")
                        .setPositiveButton("Yes, delete") { _, _ ->
                            TripsStore.instance.delete(trip)
                            finish()
                        }
                        .setNegativeButton("No, cancel") { _, _ ->

                        }
                        .show()
            }
        }

    }
    // Gets the trip timeline (start - end)
    private fun getTimeline(startdate: String, duration:Int) : String {
        // Convert string date -> Date object for calendar
        var df : SimpleDateFormat = SimpleDateFormat("mm/dd/yyyy");
        var cal: Calendar = Calendar.getInstance();
        try{
            cal.time = df.parse(startdate)
        } catch(e: ParseException) {
            e.printStackTrace()
        }
        cal.add(Calendar.DATE, duration)
        // Convert new date to string
        val endDate : String = df.format(cal.time)
        return String.format("$startdate to $endDate")
    }
}