package com.playground.android.vuki.androidplayground

import javax.inject.Inject

class SuperActPresenterImpl @Inject
constructor(private val view: SuperActContract.View) : SuperActContract.Presenter