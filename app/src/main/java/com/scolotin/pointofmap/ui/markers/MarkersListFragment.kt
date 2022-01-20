package com.scolotin.pointofmap.ui.markers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.model.Marker
import com.scolotin.pointofmap.R
import com.scolotin.pointofmap.databinding.FragmentMarkersListBinding
import com.scolotin.pointofmap.ui.common.SharedViewModel

class MarkersListFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var viewBinding: FragmentMarkersListBinding
    private lateinit var markersListAdapter: MarkersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_markers_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.data.observe(viewLifecycleOwner, { o(it) })

        markersListAdapter = MarkersListAdapter()

        viewBinding = FragmentMarkersListBinding.bind(view)
        viewBinding.run {
            markerList.adapter = markersListAdapter
        }
    }

    private fun o(markersList: ArrayList<Marker>) {
        val a = markersList
        markersListAdapter.updateData(a)
    }

}