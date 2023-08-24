package ru.freelanzer1.videolistapp2.domain.use_case

data class AlbumUseCases(
    val getAlbums: GetAlbums,
    val getRandomVideos: GetRandomVideos,
    val deleteAlbum: DeleteAlbum,
    val addAlbum: AddAlbum,
    val getAlbum: GetAlbum
)
