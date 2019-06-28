
import android.content.Context
import com.app.lifegames.security.EncryptedPreferences


/**
 * Created by android on 27/2/18.
 */
class Preferences {
    companion object {

        var prefs: EncryptedPreferences? = null
        fun initPreferences(context: Context) {
            prefs = EncryptedPreferences(context)
        }
    }
}