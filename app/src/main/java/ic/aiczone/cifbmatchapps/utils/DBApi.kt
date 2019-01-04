package ic.aiczone.cifbmatchapps.utils

import android.net.Uri
import ic.aiczone.cifbmatchapps.BuildConfig


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DBApi(val id: String?){
    fun getTeamDetail() = urlBuild("lookupteam.php")
    fun getPrevSchedule() = urlBuild("eventspastleague.php")
    fun getNextSchedule() = urlBuild("eventsnextleague.php")
    fun getMatchDetail() = urlBuild("lookupevent.php")

    /**
     * jika generate url menggunakan Uri.parse, akan susah ketika melakukan unit testing
     * harus menggunakan sintak berikut pada class test-nya
     * # @RunWith(RobolectricTestRunner::class)
     * # @Config(constants = BuildConfig::class)
     */
/*    private fun urlBuild(path: String?) = Uri.parse(BuildConfig.API_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath(path)
            .appendQueryParameter("id", id)
            .build().toString()*/

    private fun urlBuild(path: String?) = BuildConfig.API_URL
            .plus("api/v1/json/")
            .plus(BuildConfig.API_KEY + "/")
            .plus(path)
            .plus("?id=$id")
}