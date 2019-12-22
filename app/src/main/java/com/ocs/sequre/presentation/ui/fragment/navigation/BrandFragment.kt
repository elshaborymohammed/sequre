package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Brand
import com.ocs.sequre.presentation.ui.adapter.BrandsAdapter
import com.ocs.sequre.presentation.ui.viewholder.ListItemViewHolder

class BrandFragment : BaseFragment() {

    lateinit var listItemViewHolder: ListItemViewHolder
    override fun layoutRes(): Int {
        return R.layout.fragment_list_of_items
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        listItemViewHolder = ListItemViewHolder(view)

        var data = ArrayList<Brand>()
        for (i in 1..8) {
            data.add(
                Brand(
                    id = i,
                    name = "name $i",
                    image = "https://photos.app.goo.gl/XgvXHzVB4JecxggL7"
                )
            )
        }

        val adapter = BrandsAdapter()
        listItemViewHolder.list.adapter = adapter
        adapter.swap(data)
    }

}