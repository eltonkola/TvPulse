package org.eltonkola.tvpulse.data

enum class AppsScreen(val title: String) {
    Splash(title = "splash"),
    Tutorial(title = "tutorial"),
    Main(title = "main"),
    TvShow(title = "tv_show"),
    Movie(title = "tv_show"),
}

enum class AppTabs(val title: String) {
    Shows(title = "shows"),
    Movies(title = "movies"),
    Explore(title = "explore"),
    Settings(title = "settings")
}
