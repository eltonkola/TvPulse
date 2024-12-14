package org.eltonkola.tvpulse.data.db.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class SeasonEntity : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var air_date: String = ""
    var episode_count: Int = 0
    var name: String= ""
    var overview: String? = ""
    var poster_path: String? = ""
    var season_number: Int = 0
    var vote_average: Double = 0.0
}
