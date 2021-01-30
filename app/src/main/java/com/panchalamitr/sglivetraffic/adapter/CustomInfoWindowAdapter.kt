package com.panchalamitr.sglivetraffic.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.panchalamitr.sglivetraffic.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/** It will show when user taps on Pin **/
class CustomInfoWindowAdapter(private var inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    private var view: View? = null
    /** Helps to track, if showInfoWindow() called from MarkerCallback() or not **/
    private var lastMarker: Marker? = null
    private var textView: TextView? = null

    override fun getInfoContents(marker: Marker): View {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_info_window, null)
        }

        if (lastMarker == null || lastMarker!!.id != marker.id) {
            lastMarker = marker

            val imageView = view!!.findViewById<AppCompatImageView>(R.id.ivCustomInfoWindow)
            textView = view!!.findViewById<TextView>(R.id.tvPleaseWait)
            textView!!.visibility = View.VISIBLE

            val url = marker.tag as String

            lastMarker = marker

            /** Here we have to use noFade() otherwise image will not show proper **/
            Picasso.get()
                .load(url)
                .noFade()
                .into(imageView, MarkerCallback(marker))
        }
        return view!!
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    /**
     * Whatever we return from getInfoContents(), at that moment, view
     * is converted into a Bitmap and is used for displaying the results,
     *
     * So we can not display the image until later,
     * when the download is complete, by which point in time
     * the Bitmap is already created and used.
     *
     * So by calling showInfoWindow() again, we can show image.
     */
    inner class MarkerCallback(var marker: Marker) : Callback {
        override fun onSuccess() {
            if (marker.isInfoWindowShown) {
                textView?.visibility = View.GONE
                marker.showInfoWindow()
            }
        }

        override fun onError(e: Exception?) {

        }

    }

}