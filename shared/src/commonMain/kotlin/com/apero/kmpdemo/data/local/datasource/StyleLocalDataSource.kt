package com.apero.kmpdemo.data.local.datasource

import com.apero.kmpdemo.domain.model.Category
import com.apero.kmpdemo.domain.model.Style
import data.local.model.CategoryEntityQueries
import data.local.model.StyleEntityQueries

interface StyleLocalDataSource {
    suspend fun getStyleHome(): List<Category>
    suspend fun saveStyleHome(categories: List<Category>)
}

class StyleLocalDataSourceImpl(
    private val categoryQueries: CategoryEntityQueries,
    private val styleQueries: StyleEntityQueries
) : StyleLocalDataSource {
    override suspend fun getStyleHome(): List<Category> {
        return categoryQueries.getAllCategories().executeAsList().map { categoryEntity ->
            Category(
                id = categoryEntity.id,
                name = categoryEntity.name,
                styles = styleQueries.getStylesByCategory(categoryEntity.id).executeAsList().map { styleEntity ->
                    Style(
                        id = styleEntity.id,
                        name = styleEntity.name,
                        description = styleEntity.description,
                        categoryId = styleEntity.categoryId,
                        thumbnail = styleEntity.thumbnail
                    )
                }
            )
        }
    }

    override suspend fun saveStyleHome(categories: List<Category>) {
        categoryQueries.transaction {
            categoryQueries.deleteAllCategories()
            styleQueries.deleteAllStyles()

            categories.forEach { category ->
                categoryQueries.insertCategory(category.id, category.name)
                category.styles.forEach { style ->
                    styleQueries.insertStyle(
                        id = style.id,
                        name = style.name,
                        description = style.description,
                        categoryId = style.categoryId,
                        thumbnail = style.thumbnail
                    )
                }
            }
        }
    }
} 