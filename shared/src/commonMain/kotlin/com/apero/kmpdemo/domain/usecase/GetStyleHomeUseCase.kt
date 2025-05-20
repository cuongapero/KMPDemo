package com.apero.kmpdemo.domain.usecase

import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.model.Style
import com.apero.kmpdemo.domain.repository.StyleRepository

class GetStyleHomeUseCase(
    private val repository: StyleRepository
) {
    suspend operator fun invoke(): List<Category> {
//        return repository.getStyleHome()
        return listOf(
            Category(
                id = "living_room",
                name = "Living Room",
                styles = listOf(
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
            ),
            Category(
                id = "bedroom",
                name = "Bedroom",
                styles = listOf(
                    Style(
                        id = "cozy_bedroom",
                        name = "Cozy Bedroom",
                        description = "Warm and inviting bedroom design",
                        categoryId = "bedroom",
                        thumbnail = "https://example.com/cozy_bedroom.jpg"
                    ),
                    Style(
                        id = "luxury_bedroom",
                        name = "Luxury Bedroom",
                        description = "Elegant and sophisticated bedroom style",
                        categoryId = "bedroom",
                        thumbnail = "https://example.com/luxury_bedroom.jpg"
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
            ),
            Category(
                id = "kitchen",
                name = "Kitchen",
                styles = listOf(
                    Style(
                        id = "modern_kitchen",
                        name = "Modern Kitchen",
                        description = "Sleek and contemporary kitchen design",
                        categoryId = "kitchen",
                        thumbnail = "https://example.com/modern_kitchen.jpg"
                    ),
                    Style(
                        id = "rustic_kitchen",
                        name = "Rustic Kitchen",
                        description = "Farmhouse-inspired kitchen with natural elements",
                        categoryId = "kitchen",
                        thumbnail = "https://example.com/rustic_kitchen.jpg"
                    )
                )
            ),
            Category(
                id = "bathroom",
                name = "Bathroom",
                styles = listOf(
                    Style(
                        id = "spa_bathroom",
                        name = "Spa-like Bathroom",
                        description = "Relaxing and luxurious bathroom design",
                        categoryId = "bathroom",
                        thumbnail = "https://example.com/spa_bathroom.jpg"
                    ),
                    Style(
                        id = "minimalist_bathroom",
                        name = "Minimalist Bathroom",
                        description = "Clean and simple bathroom aesthetics",
                        categoryId = "bathroom",
                        thumbnail = "https://example.com/minimalist_bathroom.jpg"
                    )
                )
            )
        )
    }
} 