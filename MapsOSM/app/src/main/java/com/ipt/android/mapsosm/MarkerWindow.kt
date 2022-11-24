package com.ipt.android.mapsosm


import android.widget.Button
import android.widget.Toast
import org.osmdroid.views.MapView

import org.osmdroid.views.overlay.infowindow.InfoWindow


class MarkerWindow: InfoWindow {

    private lateinit var parent: MainActivity

    constructor(mapView: MapView, parent: MainActivity):super(R.layout.info_window, mapView) {
        this.parent = parent
    }
    override fun onOpen(item: Any?) {
        // Following command
        closeAllInfoWindowsOn(mapView)

        // Here we are settings onclick listeners for the buttons in the layouts.

        val olaButton = mView.findViewById<Button>(R.id.ola_button)


        olaButton.setOnClickListener {
            // How to create a moveMarkerMapListener is not covered here.
            // Use the Map Listeners guide for this instead
            //mapView.addMapListener(MoveMarkerMapListener)
            Toast.makeText(parent,"Ola IPT", Toast.LENGTH_LONG).show()
        }


        // You can set an onClickListener on the InfoWindow itself.
        // This is so that you can close the InfoWindow once it has been tapped.

        // Instead, you could also close the InfoWindows when the map is pressed.
        // This is covered in the Map Listeners guide.

        mView.setOnClickListener {
            close()
        }
    }

    override fun onClose() {

    }


}