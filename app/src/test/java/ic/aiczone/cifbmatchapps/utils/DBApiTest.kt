package ic.aiczone.cifbmatchapps.utils

import ic.aiczone.cifbmatchapps.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test

/*@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)*/
class DBApiTest {

    var apiURL = "https://www.thesportsdb.com/api/v1/json/" + BuildConfig.API_KEY

    @Test
    fun testGetTeamDetail() {
        var teamId = "133604"
        var realita = DBApi(teamId).getTeamDetail()
        var impian = apiURL.plus("/lookupteam.php?id=").plus(teamId)
        assertEquals(impian, realita)
    }

    @Test
    fun testGetMatchDetail() {
        var eventId = "576566"
        var realita = DBApi(eventId).getMatchDetail()
        var impian = apiURL.plus("/lookupevent.php?id=").plus(eventId)
        assertEquals(impian, realita)
    }
}