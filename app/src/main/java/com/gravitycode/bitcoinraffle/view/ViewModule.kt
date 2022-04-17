package com.gravitycode.bitcoinraffle.view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gravitycode.bitcoinraffle.util.getContentView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ViewModule {

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