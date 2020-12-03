package dev.developintelligence.vmware.vmwaretravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import dev.developintelligence.vmware.vmwaretravel.providers.WeatherAPI

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        title = "Weather"
        findViewById<TextView>(R.id.textCityWeather).text = "City Weather Look Up"

        // Search Listener
        findViewById<Button>(R.id.searchCityButton).setOnClickListener(){
            var city : String = findViewById<EditText>(R.id.searchCity).text.toString()
            updateCityUI(city)
            searchCityAPI(city)
        }




    }
    // Updates the UI
    private fun updateCityUI(city : String) {
        if (city.isEmpty()){
            findViewById<TextView>(R.id.textCityWeather).text = "City Weather Look Up"
        }
        else{
            findViewById<TextView>(R.id.textCityWeather).text = "Temperature at $city"
        }
    }

    // Searchs for city data through API
    private fun searchCityAPI(city: String){
        WeatherAPI().getWeather(city, this)
        { weather ->
            if (weather != null) {
                findViewById<TextView>(R.id.textTemperature).setText("${weather.temperature} ⁰F")
            } else {
                findViewById<TextView>(R.id.textTemperature).setText("No city exists")
            }

        }
    }
}