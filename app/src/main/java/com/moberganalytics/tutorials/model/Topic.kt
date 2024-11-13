package com.moberganalytics.tutorials.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic (
    @StringRes val nameRes: Int,
    val numCourses: Int,
    @DrawableRes val imageRes: Int
)