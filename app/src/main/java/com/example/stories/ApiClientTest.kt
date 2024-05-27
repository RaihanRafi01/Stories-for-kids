import com.example.stories.ApiService
import com.example.stories.Story

class ApiClientTest : ApiService {
    override suspend fun getStories(): List<Story> {
        val dummyStories = listOf(
            Story("Dummy Title 1", "Dummy Content 1", "dummy_image_url_1"),
            Story("Dummy Title 2", "Dummy Content 2", "dummy_image_url_2"),
        )
        return dummyStories
    }
}
