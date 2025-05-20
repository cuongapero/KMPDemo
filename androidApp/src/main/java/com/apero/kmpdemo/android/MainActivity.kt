package com.apero.kmpdemo.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apero.composeapp.presentation.home.BannerSection
import com.apero.composeapp.presentation.home.CategorySection
import com.apero.composeapp.presentation.home.HomeScreen
import com.apero.composeapp.presentation.home.TrendingSection
import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.model.Style

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun BannerPreview() {
    BannerSection(
        banners = listOf(
            Style(
                id = "modern_living",
                name = "Modern Living Room",
                description = "Clean lines and minimalist design for contemporary living",
                categoryId = "living_room",
                thumbnail = "https://example.com/modern_living.jpg"
            ),
            Style(
                id = "scandinavian_living",
                name = "Scandinavian Living Room",
                description = "Light, airy spaces with functional furniture",
                categoryId = "living_room",
                thumbnail = "https://example.com/scandinavian_living.jpg"
            )
        )
    )
}

@Preview
@Composable
fun TrendingPreview() {
    TrendingSection(
        category = Category(
            id = "living_room",
            name = "Living Room",
            listOf(
                Style(
                    id = "modern_living",
                    name = "Modern Living Room",
                    description = "Clean lines and minimalist design for contemporary living",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/modern_living.jpg"
                ),
                Style(
                    id = "scandinavian_living",
                    name = "Scandinavian Living Room",
                    description = "Light, airy spaces with functional furniture",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/scandinavian_living.jpg"
                ),
                Style(
                    id = "modern_living",
                    name = "Modern Living Room",
                    description = "Clean lines and minimalist design for contemporary living",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/modern_living.jpg"
                ),
                Style(
                    id = "scandinavian_living",
                    name = "Scandinavian Living Room",
                    description = "Light, airy spaces with functional furniture",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/scandinavian_living.jpg"
                )
            )
        )
    )
}

@Preview
@Composable
fun CategorySectionPreview() {
    CategorySection(
        category = Category(
            id = "living_room",
            name = "Living Room",
            listOf(
                Style(
                    id = "modern_living",
                    name = "Modern Living Room",
                    description = "Clean lines and minimalist design for contemporary living",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/modern_living.jpg"
                ),
                Style(
                    id = "scandinavian_living",
                    name = "Scandinavian Living Room",
                    description = "Light, airy spaces with functional furniture",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/scandinavian_living.jpg"
                ),
                Style(
                    id = "modern_living",
                    name = "Modern Living Room",
                    description = "Clean lines and minimalist design for contemporary living",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/modern_living.jpg"
                ),
                Style(
                    id = "scandinavian_living",
                    name = "Scandinavian Living Room",
                    description = "Light, airy spaces with functional furniture",
                    categoryId = "living_room",
                    thumbnail = "https://example.com/scandinavian_living.jpg"
                )
            )
        )
    )
}