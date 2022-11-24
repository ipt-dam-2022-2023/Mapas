package com.ipt.android.mapsosm

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.compass.CompassOverlay

class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map : MapView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionsIfNecessary(arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ))

        Configuration.getInstance().setUserAgentValue(this.getPackageName())

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.zoomTo(17.0)

        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        map.setMultiTouchControls(true)

        var compassOverlay = CompassOverlay(this, map)
        compassOverlay.enableCompass()
        map.overlays.add(compassOverlay)

        var point = GeoPoint(39.60068, -8.38967)

        var startMarker = Marker(map)
        startMarker.position = point
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        startMarker.infoWindow = MarkerWindow(map, this)
        map.overlays.add(startMarker)



       /* val geoPoints = ArrayList<GeoPoint>();
        geoPoints.add(point)
        geoPoints.add(GeoPoint(39.60199, -8.39675))
        val line = Polyline();   //see note below!
        line.setPoints(geoPoints);
        line.setOnClickListener({ polyline: Polyline, mapView: MapView, geoPoint: GeoPoint ->
            Toast.makeText(mapView.context, "polyline with " + line.actualPoints.size + " pts was tapped", Toast.LENGTH_LONG).show()
             false
        });
        map.overlays.add(line);*/


        Handler(Looper.getMainLooper()).postDelayed({
            map.controller.setCenter(point)
        }, 1000)




    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }


    private fun requestPermissionsIfNecessary(permissions:Array<out String>) {
        val permissionsToRequest = ArrayList<String>();
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toArray(arrayOf<String>()),
                REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


}