package com.gravitycode.bitcoinraffle.view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.common.base.Preconditions
import com.gravitycode.bitcoinraffle.app.MainActivity
import com.gravitycode.bitcoinraffle.util.getContentView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object LayoutModule {

    @Provides
    fun providesLayoutInflater(@ActivityContext context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    @Provides
    fun providesActivityView(@ActivityContext activityContext: Context): ActivityView {
        val activity = activityContext as Activity
        val contentView: ViewGroup = activity.getContentView()
        return ActivityView(contentView)
    }
}