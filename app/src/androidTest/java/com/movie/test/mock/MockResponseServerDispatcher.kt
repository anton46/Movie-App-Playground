package com.movie.test.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


class MockResponseServerDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest?): MockResponse {
        request?.let {
            if (it.path == "/3/discover/movie?api_key=328c283cd27bd1877d9080ccb1604c91&sort_by=release_date.desc&primary_release_date.lte=2019-07-23&vote_count.gte=50&page=1")
                return MockResponse().setResponseCode(200).setBody(Mocks().movieResponse())
            if (it.path == "/3/movie/612152?api_key=328c283cd27bd1877d9080ccb1604c91")
                return MockResponse().setResponseCode(200).setBody(Mocks().movieDetailResponse())
        }
        return MockResponse().setResponseCode(404)
    }
}