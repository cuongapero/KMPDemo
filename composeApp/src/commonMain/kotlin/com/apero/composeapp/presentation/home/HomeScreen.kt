package com.apero.composeapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.model.Style
import com.apero.picker_image.ImagePicker
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import kmpdemo.composeapp.generated.resources.Res
import kmpdemo.composeapp.generated.resources.banner_1
import kmpdemo.composeapp.generated.resources.ic_add
import kmpdemo.composeapp.generated.resources.ic_see_all
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
expect fun HomeScreen(imagePicker: ImagePicker)

@Composable
internal fun HomeScreenContent(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.LoadCategories)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
    ) {
        HomeScreenContent(
            banners = state.banners,
            trending = state.trending,
            categories = state.categories
        )

        // Add FloatingActionButton
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp)
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .clickable {
                    viewModel.processIntent(HomeIntent.PickImage)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_add),
                contentDescription = "Pick Image",
                tint = Color.White
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    banners: List<Style>,
    trending: Category?,
    categories: List<Category>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item { BannerSection(banners = banners) }
        trending?.let {
            item { TrendingSection(category = trending) }
        }
        item { CategoriesSection(categories = categories) }
    }
}

@Composable
fun BannerSection(banners: List<Style>) {
    if (banners.isEmpty()) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val pagerState = rememberPagerState(pageCount = { banners.size })
        val coroutineScope = rememberCoroutineScope()

        // Auto-scroll
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
                coroutineScope.launch {
                    val nextPage = (pagerState.currentPage + 1) % banners.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            BannerItem(banners[page])
        }

        // Page Indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                Color.White
                            else
                                Color.White.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}

@Composable
fun BannerItem(style: Style) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box {
            Image(
                painter = painterResource(Res.drawable.banner_1),
                contentDescription = style.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xE60D0D0D)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = style.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = style.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun TrendingSection(category: Category) {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Trending",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                imageVector = vectorResource(Res.drawable.ic_see_all),
                contentDescription = "See all",
                tint = Color.White
            )
        }

        // Grid with different sized items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Left column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Large item (6/10 of height)
                TrendingItem(
                    style = category.styles.getOrNull(0) ?: return,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f),
                    showStyleTag = true
                )
                // Small item (4/10 of height)
                TrendingItem(
                    style = category.styles.getOrNull(1) ?: return,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f),
                    showStyleTag = true
                )
            }

            // Right column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Two equal sized items
                TrendingItem(
                    style = category.styles.getOrNull(2) ?: return,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    showStyleTag = true
                )
                TrendingItem(
                    style = category.styles.getOrNull(3) ?: return,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    showStyleTag = true
                )
            }
        }
    }
}

@Composable
private fun TrendingItem(
    style: Style,
    modifier: Modifier = Modifier,
    showStyleTag: Boolean = false
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box {
            CoilImage(
                imageModel = { "https://fastly.picsum.photos/id/160/536/354.jpg?hmac=KdHNYMFv6uqCoLUy2caCfxOiM8APSOP9K-NByrnChTY" },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x99000000)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                if (showStyleTag) {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp)),
                        color = Color.White.copy(alpha = 0.8f)
                    ) {
                        Text(
                            text = style.name ?: "",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                Text(
                    text = style.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(top = if (showStyleTag) 4.dp else 0.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoriesSection(categories: List<Category>) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        categories.forEach { category ->
            CategorySection(category)
        }
    }
}

@Composable
fun CategorySection(category: Category) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                imageVector = vectorResource(Res.drawable.ic_see_all),
                contentDescription = "See all",
                tint = Color.White
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(category.styles) { item ->
                CategoryItem(item)
            }
        }
    }
}

@Composable
private fun CategoryItem(item: Style) {
    Surface(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp)),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box {
            CoilImage(
                imageModel = { "https://fastly.picsum.photos/id/833/200/300.jpg?hmac=b_oHYH2fuGTTRs2679QZsX0IQq4rTJaywPYnYnOGPtE" },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x99000000)
                            )
                        )
                    )
            )

            Text(
                text = item.name,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}
