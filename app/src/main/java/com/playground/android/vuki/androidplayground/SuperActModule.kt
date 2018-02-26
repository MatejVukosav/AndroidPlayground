package com.playground.android.vuki.androidplayground

import dagger.Module
import dagger.Provides

@Module
class SuperActModule {

    @Provides
    internal fun provideView(activity: SuperAct): SuperActContract.View {
        return activity
    }

    @Provides
    internal fun providePresenter(view: SuperActContract.View): SuperActContract.Presenter {
        return SuperActPresenterImpl(view)
    }
}