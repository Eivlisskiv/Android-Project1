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
import caqc.cgodin.android_project1.*
import caqc.cgodin.android_project1.sqlite.RestaurantRecyclerAdapter
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import org.json.JSONObject


class MapsFragment : Fragment() {

    companion object{
        val placeQuery: GooglePlaceQuery = GooglePlaceQuery(
            "type=restaurant", Session.current_session?.location ?: Location(""), 10.0,
            SearchType.Nearby, "name", "place_id", "icon", "geometry", "formatted_address", "website", "formatted_phone_number"
        );
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var REQUEST_CODE = 1;

    private var initLocation: Location = Session.current_session?.getSessionLocation() ?: Location("");
    private var map: GoogleMap? = null;

    fun clear()  = map?.clear()
    var mapCallback: ((MapsFragment) -> Unit)? = null;
    private val callback = OnMapReadyCallback { googleMap ->
        initiateMapSettings(googleMap)
        map = googleMap;

        if(mapCallback != null) mapCallback!!(this);
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
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16F))
    }

    fun zoomTo(latitude: Double?, longitude: Double?) {
        val marker = LatLng(latitude ?: 0.0, longitude ?: 0.0)
        if(map == null){
            initLocation.latitude = latitude ?: 0.0;
            initLocation.longitude = longitude ?: 0.0;
            return;
        }
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16F))
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
                    Session.current_session?.location = location
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
        placeQuery.location = Session.current_session?.getSessionLocation() ?: Location("")
        placeQuery.distance = distance * 1000;

        placeQuery.request(callback)

        //refreshMarkers(recyclerView)
    }

    fun refreshMarkers(recyclerView: RestaurantListFragment){
        val handler = Handler()
        handler.postDelayed(
            Runnable {
                recyclerView.submitList(Session.current_session!!.searchResult)
                placeSearcheResultMarkers()
            },
            1000
        )
    }

    fun placeSearcheResultMarkers(){
        map?.clear()
        for(resto in Session.current_session!!.searchResult){
            Log.i("Resto", "${resto.name}: ${resto.latitude ?: 0.0}, ${resto.longitude ?: 0.0}")
            val marker = LatLng(resto.latitude ?: 0.0, resto.longitude ?: 0.0)
            map?.addMarker(MarkerOptions().position(marker).title(resto.name))
        }
    }


}
