package caqc.cgodin.android_project1.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.GooglePlaceQuery
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.SearchType
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject


class MapsFragment : Fragment() {

    companion object{
        val placeQuery: GooglePlaceQuery = GooglePlaceQuery(
            "type=restaurant", Session.location ?: Location(""), 10.0,
            SearchType.Nearby, "name", "formatted_address", "icon", "geometry"
        );
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var REQUEST_CODE = 1;

    private var map: GoogleMap? = null;
    fun clear()  = map?.clear()
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        initiateMapSettings(googleMap)
        map = googleMap;
        mapMarker(Session.location, "Current Location");
    }

    fun initiateMapSettings(googleMap: GoogleMap){
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL;
        val mapUI = googleMap.uiSettings
        mapUI.isZoomControlsEnabled = true;
        mapUI.isZoomGesturesEnabled = true;
        mapUI.isCompassEnabled = true;
    }

    fun mapMarker(location: Location?, name: String = "Marker", moveCam: Boolean = true){
        if(map == null || location == null) return;
        val marker = LatLng(location.latitude, location.longitude)
        map!!.addMarker(MarkerOptions().position(marker).title(name))
        if(moveCam)
            map!!.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), REQUEST_CODE
            )
        }
        else setLocationClient();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setLocationClient();
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    @SuppressLint("MissingPermission")
    fun setLocationClient(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if(location != null){
                    Session.location = location
                    map?.moveCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(location.latitude, location.longitude)
                        )
                    )
                }
            }
    }

    fun openGoogleMaps(query: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    fun googlePlacesQuery(recyclerView: RestaurantListFragment, distance: Double, callback: (JSONObject) -> Unit) {
        placeQuery.location = Session.location ?: Location("");
        placeQuery.distance = distance * 1000;

        placeQuery.request(callback)

        refreshMarkers(recyclerView)
    }

    fun refreshMarkers(recyclerView: RestaurantListFragment){
        val handler = Handler()
        handler.postDelayed(
            Runnable {
                recyclerView.restaurantAdapter.submitList(Session.searchResult)
                map?.clear()
                for(resto in Session.searchResult){
                    Log.i("Resto", "${resto.name}: ${resto.latitude ?: 0.0}, ${resto.longitude ?: 0.0}")
                    val marker = LatLng(resto.latitude ?: 0.0, resto.longitude ?: 0.0)
                    map?.addMarker(MarkerOptions().position(marker).title(resto.name))
                }
            },
            3000
        )
    }
}
