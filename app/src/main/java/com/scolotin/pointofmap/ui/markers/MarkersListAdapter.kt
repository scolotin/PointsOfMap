package com.scolotin.pointofmap.ui.markers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.Marker
import com.scolotin.pointofmap.R
import com.scolotin.pointofmap.databinding.ItemMarkersListBinding

class MarkersListAdapter : RecyclerView.Adapter<MarkersListAdapter.MarkersListVH>() {

    private lateinit var data: ArrayList<Marker>

    fun updateData(data: ArrayList<Marker>) {
        this.data = data
        notifyItemRangeChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkersListVH {
        val inflater = LayoutInflater.from(parent.context).apply {
            inflate(R.layout.item_markers_list, parent, false)
        }
        val viewBinding = ItemMarkersListBinding.inflate(inflater, parent, false)
        return MarkersListVH(viewBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MarkersListVH, position: Int) {
        holder.viewBinding.run {
            data[position].let {
                markerTitle.setText(it.title)
                markerDescription.setText(it.snippet)
            }

//            root.setOnClickListener {
//                holder.toggleText()
//            }

//            noteDescription.visibility = if (data[position].second) View.VISIBLE else View.GONE
//            data[position].first.description?.let {
//                noteDescription.setText(it)
//            }

//            noteDelete.setOnClickListener {
//                holder.removeItem()
//            }
        }
    }

    inner class MarkersListVH(val viewBinding: ItemMarkersListBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
    }
}
