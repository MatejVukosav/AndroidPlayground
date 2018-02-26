package com.playground.android.vuki.androidplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SuperAct : AppCompatActivity(), SuperActContract.View {

    @Inject
    internal var presenter: SuperActContract.Presenter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

}