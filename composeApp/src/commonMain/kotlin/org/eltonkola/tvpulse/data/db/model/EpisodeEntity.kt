package org.eltonkola.tvpulse.data.db.model

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class EpisodeEntity : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var date: RealmInstant = RealmInstant.now()
    var userRating: Int? = null
    var userEmotion: Int? = null
    var userComment: String? = null
}
