package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.card_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class ProfileFragment : BaseFragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)

        val pagerAdapter = PagerAdapter(this)
        pager.adapter = pagerAdapter
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = pagerAdapter.getTitle(position)
        }.attach()
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                add_dependent.visibility = if (position == 0) View.VISIBLE else View.GONE
            }
        })

        view.edit_profile.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.editProfileFragment))
        view.add_dependent.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.dependentCreateFragment))
    }

    override fun onResume() {
        super.onResume()
        viewModel.call()
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().observeOn(AndroidSchedulers.mainThread()).subscribe(::loading),
            viewModel.error().subscribe(onError()),
            viewModel.profile().subscribe {
                view?.apply {
                    name.text = it.name
                    email.text = it.email
                    phone.text = it.phone
                    birth_date.text = it.birthDate ?: ""
                    GlideApp.with(avatar)
                        .load(it.photo)
                        .error(R.drawable.ic_profile_avatar)
                        .signature(ObjectKey(it.photo ?: ""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(avatar)
                }
            }
        )
    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments: List<Fragment>
        private val title: List<String>

        init {
            fragments = arrayListOf(DependentsFragment(), AddressFragment())
            title = arrayListOf("Dependents", "Address")
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        fun getTitle(position: Int): String {
            return title[position]
        }
    }
}