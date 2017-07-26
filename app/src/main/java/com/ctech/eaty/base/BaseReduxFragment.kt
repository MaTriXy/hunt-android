package com.ctech.eaty.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ctech.eaty.base.redux.LifeCycleDelegate
import com.ctech.eaty.base.redux.Store
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseReduxFragment<State> : Fragment() {

    private lateinit var lifecycleDelegate: LifeCycleDelegate<State>
    private val disposables = CompositeDisposable()


    protected abstract fun store(): Store<State>

    protected fun disposeOnStop(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleDelegate = LifeCycleDelegate(store())
        lifecycleDelegate.onStart()

    }

    override fun onDestroyView() {
        lifecycleDelegate.onStop(activity.isFinishing)
        disposables.clear()
        super.onDestroyView()

    }

}