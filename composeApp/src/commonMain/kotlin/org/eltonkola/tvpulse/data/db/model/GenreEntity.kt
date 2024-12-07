package org.eltonkola.tvpulse.data.db.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class GenreEntity : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
}
