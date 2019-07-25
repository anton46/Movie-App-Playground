package com.movie.app.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


class MockResponseServerDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest?): MockResponse {
        request?.let {
            if (it.path.contains("/3/discover/movie".toRegex()))
                return MockResponse().setResponseCode(200).setBody(Mocks().movieResponse())
            if (it.path.contains("/3/movie/612152".toRegex()))
                return MockResponse().setResponseCode(200).setBody(Mocks().movieDetailResponse())
        }
        return MockResponse().setResponseCode(404)
    }
}