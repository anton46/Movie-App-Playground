package com.movie.app.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


class SampleResponseServerDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest?): MockResponse {
        request?.let {
            if (it.path == "/v1/chat/")
                return MockResponse().setResponseCode(200).setBody(Mocks().movieResponse())
            if (it.path == "/3/movie/612152?api_key=328c283cd27bd1877d9080ccb1604c91")
                return MockResponse().setResponseCode(200).setBody(Mocks().movieDetailResponse())
        }
        return MockResponse().setResponseCode(404)
    }
}