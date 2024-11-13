package com.moberganalytics.tutorials.data

import com.moberganalytics.tutorials.R
import com.moberganalytics.tutorials.model.Affirmation
import com.moberganalytics.tutorials.model.Topic

/**
 * [AffirmDataSource] generates a list of [Affirmation]
 */
class AffirmDataSource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirm1, R.drawable.image1),
            Affirmation(R.string.affirm2, R.drawable.image2),
            Affirmation(R.string.affirm3, R.drawable.image3),
            Affirmation(R.string.affirm4, R.drawable.image4),
            Affirmation(R.string.affirm5, R.drawable.image5),
            Affirmation(R.string.affirm6, R.drawable.image6),
            Affirmation(R.string.affirm7, R.drawable.image7),
            Affirmation(R.string.affirm8, R.drawable.image8),
            Affirmation(R.string.affirm9, R.drawable.image9),
            Affirmation(R.string.affirm10, R.drawable.image10))
    }
}

object TopicDataSource {
    val topics = listOf(
        Topic(R.string.architecture, 58, R.drawable.architecture),
        Topic(R.string.crafts, 121, R.drawable.crafts),
        Topic(R.string.business, 78, R.drawable.business),
        Topic(R.string.culinary, 118, R.drawable.culinary),
        Topic(R.string.design, 423, R.drawable.design),
        Topic(R.string.fashion, 92, R.drawable.fashion),
        Topic(R.string.film, 165, R.drawable.film),
        Topic(R.string.gaming, 164, R.drawable.gaming),
        Topic(R.string.drawing, 326, R.drawable.drawing),
        Topic(R.string.lifestyle, 305, R.drawable.lifestyle),
        Topic(R.string.music, 212, R.drawable.music),
        Topic(R.string.painting, 172, R.drawable.painting),
        Topic(R.string.photography, 321, R.drawable.photography),
        Topic(R.string.tech, 118, R.drawable.tech)
    )
}
