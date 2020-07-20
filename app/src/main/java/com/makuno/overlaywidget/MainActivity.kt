package com.makuno.overlaywidget

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.mattcarroll.hover.overlay.OverlayPermission


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_HOVER_PERMISSION = 1000
    private var mPermissionsRequested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q=-1.324285,36.777465")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            this@MainActivity.startActivity(mapIntent)

            val startHoverIntent = Intent(this@MainActivity, MyHoverMenuService::class.java)
            startService(startHoverIntent)
        }
    }

    override fun onResume() {
        super.onResume()

        // On Android M and above we need to ask the user for permission to display the Hover
        // menu within the "alert window" layer.  Use OverlayPermission to check for the permission
        // and to request it.

        if(checkServiceRunning(MyHoverMenuService::class.java)){
            stopService(Intent(this@MainActivity, MyHoverMenuService::class.java))
        }

        if (!mPermissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val myIntent = OverlayPermission.createIntentToRequestOverlayPermission(this)
                startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (REQUEST_CODE_HOVER_PERMISSION === requestCode) {
            mPermissionsRequested = true
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun checkServiceRunning(serviceClass: Class<*>): Boolean {
        val manager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}