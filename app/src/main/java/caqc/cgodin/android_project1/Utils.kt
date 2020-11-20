package caqc.cgodin.android_project1

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import caqc.cgodin.android_project1.activities.ActivityExtension
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class Utils {

    companion object Factory {
        private fun findViewString(activity: AppCompatActivity, viewName: String, lang: String):String?{
            val id = activity.resources.getIdentifier("${viewName}_$lang", "string", activity.packageName)
            return if(id != 0) activity.resources.getString(id) else null;
        }

        fun getLangString(activity: AppCompatActivity, name: String, defaultLang: String = "en"): String{
            return findViewString(activity, name, Locale.getDefault().language)
                ?: findViewString(activity, name, defaultLang)
                ?: name;
        }

        fun findViewString(activity: AppCompatActivity, baseId:Int, defaultLang: String = "en"): String {
            return getLangString(activity, activity.resources.getResourceEntryName(baseId), defaultLang)
        }

        fun getFields(clazz: KClass<*>): List<KMutableProperty<*>> {
            return clazz.members.filterIsInstance<KMutableProperty<*>>() //{ it is KProperty<*> }
        }

        fun stringMatch(string: String, sequence: String) : Boolean = Regex(sequence).matches(string);

        fun <T : ActivityExtension> setGlideImage(activity: T, imageId: Int, url: String){
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            val iconImage = activity.findViewById<ImageView>(imageId)

            Glide.with(activity)
                .applyDefaultRequestOptions(requestOptions)
                .load(url)
                .into(iconImage)
        }
    }
}