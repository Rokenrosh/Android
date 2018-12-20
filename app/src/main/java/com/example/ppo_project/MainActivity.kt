package com.example.ppo_project

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.PendingIntent.getActivity
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.telephony.TelephonyManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private val _myPermissionsRequestPhoneState = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!resources.getBoolean(R.bool.develop_mode)){
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val textViewVersion = findViewById<TextView>(R.id.textView_version)
        textViewVersion.text = BuildConfig.VERSION_NAME

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.READ_PHONE_STATE)) {
                showExploration()
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    _myPermissionsRequestPhoneState)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    _myPermissionsRequestPhoneState)
            }
        } else {
            showIMEI()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            _myPermissionsRequestPhoneState -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showIMEI()
                } else {
                    val textViewIMEI = findViewById<TextView>(R.id.textView_IMEI)
                    textViewIMEI.text = getString(R.string.not_access_text)
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun showIMEI() {
        val textViewIMEI = findViewById<TextView>(R.id.textView_IMEI)
        val telephonyManager: TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            textViewIMEI.text = resources.getString(R.string.not_access_text)
        } else {
            textViewIMEI.text = telephonyManager.deviceId
        }
    }
    private fun showExploration(){
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(resources.getString(R.string.Exploration_title))
            .setMessage(resources.getString(R.string.IMEI_exploration_string))
            .setCancelable(false)
            .setNeutralButton(resources.getString(R.string.OK_string)){dialog,_ -> run {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    _myPermissionsRequestPhoneState)
                dialog.dismiss()
            }}
        val exploration = builder.create()
        exploration.show()
    }
}
