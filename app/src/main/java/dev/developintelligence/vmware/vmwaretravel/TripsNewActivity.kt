package dev.developintelligence.vmware.vmwaretravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import dev.developintelligence.vmware.vmwaretravel.datamodel.Trip
import dev.developintelligence.vmware.vmwaretravel.providers.TripsStore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TripsNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips_new)

        title = "New Trip"
        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            var name = findViewById<EditText>(R.id.editNameTrip).text.toString()
            var startDate = findViewById<EditText>(R.id.editStartDate).text.toString()
            var duration = findViewById<EditText>(R.id.editTripDuration).text.toString()

            if (!isNameValid(name)){
                AlertDialog.Builder(this)
                        .setTitle("Invalid Trip Name.")
                        .setMessage("Please add a valid name to the trip.")
                        .setNeutralButton("Ok") { _, _ ->
                        }
                        .show()

            }
            else if(!startDate.isEmpty() && !isDateValid(startDate)){
                AlertDialog.Builder(this)
                        .setTitle("Invalid Start Date")
                        .setMessage("Please enter a valid date in the format MM/DD/YYYY")
                        .setNeutralButton("Ok") { _, _ ->
                        }
                        .show()
            }

            else if((!duration.isEmpty() && startDate.isEmpty()) ||
                    (duration.isEmpty() && !startDate.isEmpty())){
                AlertDialog.Builder(this)
                        .setTitle("Invalid Timeline")
                        .setMessage("Both a start date and duration is needed for timeline.")
                        .setNeutralButton("Ok") { _, _ ->
                        }
                        .show()
            }
            else{
                val description = findViewById<EditText>(R.id.editDescription).text.toString()
                val trip = Trip(
                        name, description, startDate,
                        if (duration.isEmpty()) 0 else Integer.parseInt(duration))
                TripsStore.instance.add(trip)
                finish() // go back to previous activity
            }
        }
    }

    // To make sure trip name is not empty
    private fun isNameValid(tripName : String?): Boolean {
        return !(tripName == null || tripName.isEmpty())
    }

    // To make sure the date is valid
    private fun isDateValid(date : String): Boolean {
        var df : SimpleDateFormat = SimpleDateFormat("mm/dd/yyyy")
        df.isLenient = false
        var cal: Calendar = Calendar.getInstance();
        try{
            cal.time = df.parse(date)
        }
        catch (e : ParseException){
            return false
        }
        return true
    }
}