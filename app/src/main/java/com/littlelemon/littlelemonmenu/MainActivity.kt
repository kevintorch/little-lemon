package com.littlelemon.littlelemonmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.littlelemon.littlelemonmenu.data.AppDatabase
import com.littlelemon.littlelemonmenu.data.MenuNetwork
import com.littlelemon.littlelemonmenu.data.toMenuItemList
import com.littlelemon.littlelemonmenu.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val appDatabase by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LittleLemonPreferences.init(this)
        lifecycleScope.launch(IO) {
            repeatOnLifecycle(CREATED) {
                if (appDatabase.menuDao().isEmpty()) {
                    saveMenuToDatabase(fetchMenu())
                }
            }
        }
        setContent {
            LittleLemonTheme {
                LittleLemonNavigation(modifier = Modifier.safeContentPadding())
            }
        }
    }

    private fun saveMenuToDatabase(menuNetwork: MenuNetwork) {
        val menuNetworkItems = menuNetwork.menu
        appDatabase.menuDao().insertAllMenuItems(menuNetworkItems.toMenuItemList())
    }

    private suspend fun fetchMenu(): MenuNetwork {
        return client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()
    }
}