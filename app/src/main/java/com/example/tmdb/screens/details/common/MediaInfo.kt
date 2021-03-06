package com.example.tmdb.screens.details.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.screens.widgets.SectionText
import com.example.tmdb.utils.Resource
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun Overview(
    navController: NavController,
    overview: String,
    casts: Resource<CreditsResponse>,
    mediaId: Int
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(dimensionResource(id = R.dimen.height_xl))
    ) {

        item {
            SectionText("Overview")
            Text(
                text = overview,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.small_padding),
                        top = dimensionResource(id = R.dimen.small_medium_padding)
                    )
                    .fillMaxWidth(.8f),
                softWrap = true
            )
        }
        item {
            if (casts is Resource.Success) {
                CastDetails(casts.data)

            }


        }
    }
}

@ExperimentalPagerApi
@Composable
fun OverviewOffline(
    overview: String,
    mediaId: Int
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(dimensionResource(id = R.dimen.height_xl))
    ) {

        item {
            SectionText("Overview")
            Text(
                text = overview,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.small_padding),
                        top = dimensionResource(id = R.dimen.small_medium_padding)
                    )
                    .fillMaxWidth(.8f),
                softWrap = true
            )
        }
        item {
            CastDetailsOffline(mediaId)
        }

    }
}
