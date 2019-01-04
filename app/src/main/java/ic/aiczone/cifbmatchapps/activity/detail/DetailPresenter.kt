package ic.aiczone.cifbmatchapps.activity.detail

import com.google.gson.Gson
import ic.aiczone.cifbmatchapps.entities.MatchDetailResponse
import ic.aiczone.cifbmatchapps.entities.TeamResponse
import ic.aiczone.cifbmatchapps.utils.ApiRepository
import ic.aiczone.cifbmatchapps.utils.CoroutineContextProvider
import ic.aiczone.cifbmatchapps.utils.DBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventDetail(eventId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(DBApi(eventId).getMatchDetail()).await(),
                    MatchDetailResponse::class.java
            )

            val homeBadge = gson.fromJson(apiRepository
                    .doRequest(DBApi(data.events[0].homeTeamId).getTeamDetail()).await(),
                    TeamResponse::class.java
            )

            val awayBadge = gson.fromJson(apiRepository
                    .doRequest(DBApi(data.events[0].awayTeamId).getTeamDetail()).await(),
                    TeamResponse::class.java
            )

            view.hideLoading()
            view.showDetail(data.events, homeBadge.teams, awayBadge.teams)
        }

    }

}