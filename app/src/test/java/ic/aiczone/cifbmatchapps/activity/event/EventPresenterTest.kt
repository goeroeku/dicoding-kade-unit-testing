package ic.aiczone.cifbmatchapps.activity.event

import com.google.gson.Gson
import ic.aiczone.cifbmatchapps.entities.MatchDetail
import ic.aiczone.cifbmatchapps.entities.MatchDetailResponse
import ic.aiczone.cifbmatchapps.utils.ApiRepository
import ic.aiczone.cifbmatchapps.utils.CoroutineContextProvider
import ic.aiczone.cifbmatchapps.utils.DBApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/*@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)*/
class EventPresenterTest {

    @Mock
    private lateinit var eventView: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var eventPresenter: EventPresenter

    private lateinit var api: String
    private var leagueId = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val dbApi = DBApi(leagueId)
        api = dbApi.getPrevSchedule()
        eventPresenter = EventPresenter(eventView, api, gson, CoroutineContextProvider())
    }

    @Test
    fun getListPrev() {

        val event: MutableList<MatchDetail> = mutableListOf()
        val response = MatchDetailResponse(event)

        val dbApi = DBApi(leagueId)
        api = dbApi.getPrevSchedule()

        GlobalScope.launch {
            `when`(
                    gson.fromJson(ApiRepository().doRequest(api).await(), MatchDetailResponse::class.java)
            ).thenReturn(response)

            eventPresenter.getList()

            Mockito.verify(eventView).showLoading()
            Mockito.verify(eventView).showList(response.events)
            Mockito.verify(eventView).hideLoading()

        }
    }

    @Test
    fun getListNext() {

        val event: MutableList<MatchDetail> = mutableListOf()
        val response = MatchDetailResponse(event)

        val dbApi = DBApi(leagueId)
        api = dbApi.getNextSchedule()

        GlobalScope.launch {
            `when`(
                    gson.fromJson(ApiRepository().doRequest(api).await(), MatchDetailResponse::class.java)
            ).thenReturn(response)

            eventPresenter.getList()

            Mockito.verify(eventView).showLoading()
            Mockito.verify(eventView).showList(response.events)
            Mockito.verify(eventView).hideLoading()

        }
    }

}