package com.kiwa.fluffit.home

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.kiwa.fluffit.home.components.CoinDisplay
import com.kiwa.fluffit.home.components.FlupetAndStatUI
import com.kiwa.fluffit.home.components.FlupetImageButton
import com.kiwa.fluffit.home.ui.FlupetNameUI

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onNavigateToCollection: () -> Unit,
    onNavigateToRankingDialog: () -> Unit,
    onNavigateToMyPage: () -> Unit
) {
    val uiState: HomeViewState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onClickPencilButton = { viewModel.onTriggerEvent(HomeViewEvent.OnClickPencilButton) },
        onClickConfirmButton = { name ->
            viewModel.onTriggerEvent(
                HomeViewEvent.OnClickConfirmEditButton(
                    name
                )
            )
        },
        onClickRankingButton = onNavigateToRankingDialog,
        onClickCollectionButton = onNavigateToCollection,
        onUpdateFullness = { viewModel.onTriggerEvent(HomeViewEvent.OnUpdateFullness()) },
        onUpdateHealth = { viewModel.onTriggerEvent(HomeViewEvent.OnUpdateHealth()) },
        onClickMyPage = onNavigateToMyPage
    )
}

@Composable
internal fun HomeScreen(
    uiState: HomeViewState,
    onClickPencilButton: () -> Unit,
    onClickCollectionButton: () -> Unit,
    onClickConfirmButton: (String) -> Unit,
    onClickRankingButton: () -> Unit,
    onUpdateFullness: () -> Unit,
    onUpdateHealth: () -> Unit,
    onClickMyPage: () -> Unit
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )

        MainButtons(uiState.coin, onClickCollectionButton, onClickRankingButton, onClickMyPage)

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 80.dp)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            FlupetAndStatUI(
                uiState = uiState,
                onUpdateFullness = onUpdateFullness,
                onUpdateHealth = onUpdateHealth
            )
            Image(
                painter = rememberImagePainter(
                    imageLoader = imageLoader,
                    data = "https://github.com/shjung53/algorithm_study/assets/" +
                        "90888718/4399f85d-7810-464c-ad76-caae980ce047",
                    builder = {
                        size(OriginalSize)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 32.dp)
            )
            FlupetNameUI(uiState, onClickPencilButton, onClickConfirmButton, uiState.flupet.name)
        }
    }
}

@Composable
private fun MainButtons(
    coin: Int,
    onClickCollectionButton: () -> Unit,
    onClickRankingButton: () -> Unit,
    onClickMyPage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 120.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.TopCenter), verticalAlignment = Alignment.Bottom) {
            CoinDisplay(coin = coin)
            Spacer(modifier = Modifier.weight(1f))
            RankingButton(onClickRankingButton)
        }
        CollectionButton(
            modifier = Modifier.align(Alignment.BottomStart),
            onNavigateToCollection = onClickCollectionButton
        )
        UserButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onNavigateToMyPage = onClickMyPage
        )
    }
}

@Composable
private fun CollectionButton(modifier: Modifier, onNavigateToCollection: () -> Unit) {
    FlupetImageButton(id = R.drawable.collection, modifier = modifier, onNavigateToCollection)
}

@Composable
private fun UserButton(modifier: Modifier, onNavigateToMyPage: () -> Unit) {
    FlupetImageButton(id = R.drawable.user, modifier = modifier, onNavigateToMyPage)
}

@Composable
private fun RankingButton(onClickRankingButton: () -> Unit) {
    FlupetImageButton(id = R.drawable.ranking, onClickImage = onClickRankingButton)
}