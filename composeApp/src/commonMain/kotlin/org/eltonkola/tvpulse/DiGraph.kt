package org.eltonkola.tvpulse

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.eltonkola.tvpulse.data.db.DbManager
import org.eltonkola.tvpulse.data.local.AppSettings
import org.eltonkola.tvpulse.data.local.MediaRepository
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient

object DiGraph {

    private val appDispatcher = Dispatchers.Main.limitedParallelism(100)
    private var coroutineScope = CoroutineScope(appDispatcher)

    private val settings: Settings = Settings()
    val dbManager: DbManager = DbManager()
    val appSettings = AppSettings(settings)

    val tmdbApiClient: TmdbApiClient = TmdbApiClient()

    val mediaRepository: MediaRepository by lazy {
        MediaRepository(dbManager, tmdbApiClient)
    }

//    val contactsRepository: ContactsRepository by lazy {
//        ContactsRepository(coroutineScope = coroutineScope, dbManager = dbManager)
//    }

//    val messagesRepository: MessagesRepository by lazy {
//        MessagesRepository(
//            contactsRepository = contactsRepository,
//            coroutineScope = coroutineScope,
//            dbManager = dbManager,
//            networkManager = networkManager,
//            accountRepository = accountRepository
//        )
//    }


}