package ic.aiczone.cifbmatchapps.activity.detail

import com.google.gson.Gson
import ic.aiczone.cifbmatchapps.BuildConfig
import ic.aiczone.cifbmatchapps.entities.MatchDetail
import ic.aiczone.cifbmatchapps.entities.MatchDetailResponse
import ic.aiczone.cifbmatchapps.entities.Team
import ic.aiczone.cifbmatchapps.entities.TeamResponse
import ic.aiczone.cifbmatchapps.utils.ApiRepository
import ic.aiczone.cifbmatchapps.utils.DBApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/*@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)*/
class DetailPresenterTest {

    @Mock
    private lateinit var detailView: DetailView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var eventDetail: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        eventDetail = DetailPresenter(detailView, apiRepository, gson)
    }

    @Test
    fun testDetail() {
        val matchDetail: MutableList<MatchDetail> = mutableListOf()
        val teamHome: MutableList<Team> = mutableListOf()
        val teamAway: MutableList<Team> = mutableListOf()
        val resMatch = MatchDetailResponse(matchDetail)
        val resHome = TeamResponse(teamHome)
        val resAway = TeamResponse(teamAway)
        val eventId = "576566"

        GlobalScope.launch {
            Mockito.`when`(
                    gson.fromJson(apiRepository
                            .doRequest(DBApi(eventId).getMatchDetail()).await(),
                            MatchDetailResponse::class.java)
            ).thenReturn(resMatch)

            Mockito.`when`(
                    gson.fromJson(apiRepository
                            .doRequest(DBApi(resMatch.events[0].homeTeamId).getTeamDetail()).await(),
                            TeamResponse::class.java)
            ).thenReturn(resHome)

            Mockito.`when`(
                    gson.fromJson(apiRepository
                            .doRequest(DBApi(resMatch.events[0].homeTeamId).getTeamDetail()).await(),
                            TeamResponse::class.java)
            ).thenReturn(resAway)

            eventDetail.getEventDetail(eventId)

            Mockito.verify(detailView).showLoading()
            Mockito.verify(detailView).showDetail(matchDetail, teamHome, teamAway)
            Mockito.verify(detailView).hideLoading()

        }
    }

}