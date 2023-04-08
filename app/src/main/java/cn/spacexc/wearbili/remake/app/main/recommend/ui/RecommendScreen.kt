package cn.spacexc.wearbili.remake.app.main.recommend.ui

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.spacexc.wearbili.remake.app.main.recommend.remote.rcmd.app.Item
import cn.spacexc.wearbili.remake.app.settings.SettingsManager
import cn.spacexc.wearbili.remake.common.domain.video.VideoUtils
import cn.spacexc.wearbili.remake.common.domain.video.toShortChinese
import cn.spacexc.wearbili.remake.common.ui.UIState
import cn.spacexc.wearbili.remake.common.ui.VideoCard
import javax.inject.Inject

/**
 * Created by XC-Qan on 2023/4/6.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */

data class RecommendScreenState(
    val isRefreshing: Boolean = false,
    val videoList: List<Any> = emptyList(),
    val uiState: UIState = UIState.Loading
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendScreen(
    state: RecommendScreenState,
    onFetch: (isRefresh: Boolean) -> Unit
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = {
            onFetch(true)
        }, refreshThreshold = 40.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(6.dp)) {
            when (SettingsManager.getInstance().recommendSource) {
                "app" -> {
                    (state.videoList as List<Item>/* 这里真的没事的（确信 */).forEach {
                        if (it.goto == "av") {
                            item {
                                VideoCard(
                                    videoName = it.title,
                                    uploader = it.args.up_name ?: "",
                                    views = it.cover_left_text_2 ?: "",
                                    coverUrl = it.cover ?: "",
                                )
                            }
                        }
                    }
                }
                "web" -> {
                    (state.videoList as List<cn.spacexc.wearbili.remake.app.main.recommend.remote.rcmd.web.Item>/* 这里真的没事的（确信 */).forEach {
                        if (it.goto == "av") {
                            item(key = it.bvid) {
                                VideoCard(
                                    videoName = it.title,
                                    uploader = it.owner?.name ?: "",
                                    views = it.stat?.view?.toShortChinese()
                                        ?: "",
                                    coverUrl = it.pic
                                )
                            }
                        }
                    }
                }
            }
            item {
                LaunchedEffect(key1 = Unit, block = {
                    onFetch(false)
                })
            }
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing, state = pullRefreshState,
            modifier = Modifier.align(
                Alignment.TopCenter
            ),
        )
    }
}