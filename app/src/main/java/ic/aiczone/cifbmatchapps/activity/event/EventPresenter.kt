package ic.aiczone.cifbmatchapps.activity.event

import com.google.gson.Gson
import ic.aiczone.cifbmatchapps.utils.ApiRepository
import ic.aiczone.cifbmatchapps.entities.MatchDetailResponse
import ic.aiczone.cifbmatchapps.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class EventPresenter(private val view: EventView,
                     private val api: String,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getList(){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(ApiRepository().doRequest(api).await(), MatchDetailResponse::class.java)

            view.hideLoading()
            view.showList(data.events)
        }
    }

}