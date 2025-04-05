package com.example.studyonline.models

data class Group(
    val groupId: String = "",
    val groupName: String = "",
    val description: String = "",
    val category: String = "",
    val type: String = "",
    val createdBy: String = "",
    val timestamp: Long = 0L,
    val members: List<String> = emptyList()
)

