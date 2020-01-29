package com.ocs.sequre.app.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    @Inject
    protected lateinit var factory: ViewModelProvider.Factory

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val disposables = CompositeDisposable()


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewBound(view)
        disposables.addAll(*subscriptions())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetBehavior = BottomSheetBehavior.from(view!!.parent as View)

        bottomSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {

                }

                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {

                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss()
                }
            }
        })
        return super.onCreateDialog(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun onViewBound(view: View)

    protected open fun subscriptions(): Array<Disposable?> {
        return arrayOfNulls(0)
    }

    protected fun subscribe(d: Disposable) {
        disposables.add(d)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}