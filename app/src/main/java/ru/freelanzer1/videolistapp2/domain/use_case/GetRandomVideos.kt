package ru.freelanzer1.videolistapp2.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.freelanzer1.videolistapp2.domain.model.Video
import ru.freelanzer1.videolistapp2.ui.util.videos

class GetRandomVideos(
) {

    operator fun invoke(
    ): Flow<List<Video>> {

        val randomPosition =  (0..videos.size-2).random()
        val list = videos.subList(fromIndex = randomPosition, toIndex = randomPosition +2);

        return flow {
                emit(list)
        }
    }
}