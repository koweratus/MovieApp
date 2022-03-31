package com.example.tmdb.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@Composable
fun HomeScreen() {

    TabScreen()

}

@ExperimentalPagerApi
@Composable
fun TabScreen() {
    val pagerStateFirstTab = rememberPagerState(initialPage = 0)
    val pagerStateSecondTab = rememberPagerState(initialPage = 1)
    val pagerStateThirdTab = rememberPagerState(initialPage = 1)
    val listFirstTab = listOf("Streaming", "On Tv", "In Theaters")
    val listSecondTab = listOf("Movies", "Tv")
    val listThirdTab = listOf("Today", "This Week")

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,

        )
    {
        item {

            Spacer(modifier = Modifier.padding(40.dp))
            Text(
                text ="What's popular",
                color = colorResource( R.color.purple_700),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Tabs(pagerState = pagerStateFirstTab, listFirstTab)
            TabsContent(pagerState = pagerStateFirstTab, listFirstTab.size)
            Spacer(modifier = Modifier.padding(20.dp))
        }
        item {
            Text(
                text ="Free to Watch",
                color = colorResource( R.color.purple_700),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Tabs(pagerState = pagerStateSecondTab, listSecondTab)
            TabsContent(pagerState = pagerStateSecondTab, listSecondTab.size)
            Spacer(modifier = Modifier.padding(20.dp))


        }
        item {
            Text(
                text ="Trending",
                color = colorResource( R.color.purple_700),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Tabs(pagerState = pagerStateThirdTab, listThirdTab)
            TabsContent(pagerState = pagerStateThirdTab, listThirdTab.size)
            Spacer(modifier = Modifier.padding(40.dp))

        }

    }
}


@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState, list: List<String>) {

    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = Color.White
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .wrapContentWidth(),
                height = 3.dp,
                color = colorResource( R.color.purple_700)

            )

        },
        edgePadding = 0.dp,
        modifier = Modifier.padding(start = 10.dp)
    ) {
        list.forEachIndexed { index, _ ->

            Tab(
                text = {
                    Text(
                        text = list[index],
                        textAlign = TextAlign.Start,
                        color = if (pagerState.currentPage == index) Color.Black else Color.LightGray,
                        fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                        fontSize = 16.sp,
                    )

                },
                modifier = Modifier
                    .wrapContentWidth(),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }

    }
}


@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, count: Int) {

    HorizontalPager(count, state = pagerState, verticalAlignment = Alignment.Top) { page ->
        when (page) {
            0 -> RowSectionItem(list = createTestDataList()) {}
            1 -> RowSectionItem(list = createTestDataList()) {}
            2 -> RowSectionItem(list = createTestDataList()) {}
        }

    }

}

@ExperimentalPagerApi
@Composable
private fun RowSectionItem(
    list: List<Movies>,
    onItemClick: (moviesData: Movies) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp)
    ) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 5.dp,start = 16.dp)
        ) {
            items(
                items = list
            ) { item ->
                RowItem(moviesData = item, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
private fun RowItem(
    moviesData: Movies,
    onItemClick: (moviesData: Movies) -> Unit
) {

    Card(
        modifier = Modifier
            .size(width = 140.dp, height = 220.dp)
            .padding(horizontal = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = { onItemClick(moviesData) }
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(moviesData.imageUrl)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(6.dp))
            )
            FavoriteButton(modifier = Modifier.padding(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
        }
    }


}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {

    var isFavorite by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite =! isFavorite
        }
    ) {
        Icon(
            tint = color,

            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

private fun createTestDataList(): List<Movies> {
    val list = mutableListOf<Movies>()
    val actors = listOf("Robert Downey Jr.", "Terrance Howard", "Mate Kovilic", "Mi vi oni")


    list.add(
        Movies(
            "Iron Man",
            actors,
            "https://upload.wikimedia.org/wikipedia/en/0/02/Iron_Man_%282008_film%29_poster.jpg"
        )
    )
    list.add(
        Movies(
            "Iron Man 2",
            actors,
            "https://upload.wikimedia.org/wikipedia/en/e/ed/Iron_Man_2_poster.jpg"
        )
    )
    list.add(
        Movies(
            "Iron Man 3",
            actors,
            "https://m.media-amazon.com/images/M/MV5BMjE5MzcyNjk1M15BMl5BanBnXkFtZTcwMjQ4MjcxOQ@@._V1_.jpg"
        )
    )
    list.add(
        Movies(
            "Batman 3",
            actors,
            "https://m.media-amazon.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1_FMjpg_UX1000_.jpg"
        )
    )
    list.add(
        Movies(
            "Batman 2",
            actors,
            "https://upload.wikimedia.org/wikipedia/en/1/1c/The_Dark_Knight_%282008_film%29.jpg"
        )
    )
    list.add(
        Movies(
            "Batman",
            actors,
            "https://m.media-amazon.com/images/M/MV5BMDdmMTBiNTYtMDIzNi00NGVlLWIzMDYtZTk3MTQ3NGQxZGEwXkEyXkFqcGdeQXVyMzMwOTU5MDk@._V1_.jpg"
        )
    )
    list.add(
        Movies(
            "Joker",
            actors,
            "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"
        )
    )

    val tempRandomList = mutableListOf<Movies>()
    for (i in list.indices) {
        tempRandomList.add(list.random())
    }

    return tempRandomList

}

private data class Movies(
    val description: String,
    val actors: List<String>,
    val imageUrl: String
)
