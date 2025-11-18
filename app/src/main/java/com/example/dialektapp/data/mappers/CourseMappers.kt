package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.ActivityDto
import com.example.dialektapp.data.remote.dto.ActivityType as ActivityTypeDto
import com.example.dialektapp.data.remote.dto.CourseDto
import com.example.dialektapp.data.remote.dto.LessonDto
import com.example.dialektapp.data.remote.dto.ModuleDto
import com.example.dialektapp.domain.model.ActivityType
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.model.LessonActivity

fun CourseDto.toDomain(): Course {
    return Course(
        id = id.toString(),
        name = name,
        description = "", // API не повертає description чогось
        imageUrl = imageUrl,
        imageUrlBack = imageUrlBack,
        totalModules = totalModules,
        completedModules = completedModules,
        modules = modules.map { it.toDomain() }
    )
}

fun ModuleDto.toDomain(): CourseModule {
    return CourseModule(
        id = id.toString(),
        courseId = courseId.toString(),
        title = title,
        subtitle = subtitle,
        description = description,
        isUnlocked = isUnlocked,
        isCompleted = isCompleted,
        progress = calculateProgress(),
        lessons = lessons.map { it.toDomain() }
    )
}

private fun ModuleDto.calculateProgress(): Int {
    if (lessons.isEmpty()) return 0
    val completedLessons = lessons.count { it.isCompleted }
    return (completedLessons * 100) / lessons.size
}

fun LessonDto.toDomain(): Lesson {
    return Lesson(
        id = id.toString(),
        moduleId = moduleId.toString(),
        title = title,
        description = description,
        isUnlocked = isUnlocked,
        isCompleted = isCompleted,
        activities = activities.map { it.toDomain() },
        totalActivities = activities.size,
        completedActivities = activities.count { it.isCompleted }
    )
}

fun ActivityDto.toDomain(): LessonActivity {
    return LessonActivity(
        id = id.toString(),
        lessonId = lessonId.toString(),
        name = name,
        type = type.toDomainActivityType(),
        duration = duration,
        order = 0, // прибрати з API треба буде
        isCompleted = isCompleted,
        isUnlocked = isUnlocked
    )
}

private fun ActivityTypeDto.toDomainActivityType(): ActivityType {
    return when (this) {
        ActivityTypeDto.INTRODUCTION -> ActivityType.INTRODUCTION
        ActivityTypeDto.READING -> ActivityType.READING
        ActivityTypeDto.EXPLAINING -> ActivityType.EXPLAINING
        ActivityTypeDto.TEST -> ActivityType.TEST
    }
}
