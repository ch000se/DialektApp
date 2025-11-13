package com.example.dialektapp.data.util

object CourseIdMapper {

    private val slugToIdMap = mapOf(
        "transcarpathian" to 1,
        "galician" to 2,
        "kuban" to 3
    )

    private val idToSlugMap = slugToIdMap.entries.associate { (k, v) -> v to k }

    fun slugToId(slug: String): Int? {
        return slugToIdMap[slug.lowercase()]
    }

    fun idToSlug(id: Int): String? {
        return idToSlugMap[id]
    }

    fun stringToIntId(stringId: String): Int? {
        stringId.toIntOrNull()?.let { return it }
        return slugToId(stringId)
    }
}
