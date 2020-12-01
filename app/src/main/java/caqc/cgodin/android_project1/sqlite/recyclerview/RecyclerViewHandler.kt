package caqc.cgodin.android_project1.sqlite.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class  RecyclerViewHandler<I>(private val activity: AppCompatActivity, private val holderLayoutId : Int, val binder: (View, I) -> Unit)
    : RecyclerView.Adapter<RecyclerViewHandler.RecyclerViewModel>(){

    private var items: List<I> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewModel {
        return RecyclerViewModel(
            LayoutInflater.from(parent.context)
                .inflate(holderLayoutId, parent, false),
            activity
        );
    }

    override fun onBindViewHolder(holder: RecyclerViewModel, position: Int) {
        binder(holder.view, items[position])
    }

    override fun getItemCount(): Int = items.count()

    fun submitList(newItemsList: List<I>){
        items = newItemsList
        this.notifyDataSetChanged()
    }

    class RecyclerViewModel(val view: View, val activity: AppCompatActivity) : RecyclerView.ViewHolder(view) { }
}

