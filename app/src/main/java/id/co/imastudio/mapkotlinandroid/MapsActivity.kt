package id.co.imastudio.mapkotlinandroid

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import android.support.v4.app.ShareCompat.IntentBuilder
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.Place




class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),34)
        }

        mMap.isMyLocationEnabled = true

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")
                //change marker
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        lokasiAwal.setOnClickListener {
            try {
                val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)

                        .build(this)
                startActivityForResult(intent,34)
            } catch (e: GooglePlayServicesRepairableException) {
                // TODO: Handle the error.
            } catch (e: GooglePlayServicesNotAvailableException) {
                // TODO: Handle the error.
            }


        }
        lokasiTujuan.setOnClickListener {  }



        //change marker

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 34) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(this, data)
                //ambil coordinat
                var koordinat = place.latLng

                //nmbahin marker

                mMap.addMarker(MarkerOptions().position(koordinat)
                        .title(place.address.toString()))



                //set camera / set zoom
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(koordinat,17f))



            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(this, data)
                // TODO: Handle the error.

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
