package com.movie.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleTest {

    @Test
    @Throws(Exception::class)
    fun test() {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        val server = MockWebServer()

        // Schedule some responses.
        server.enqueue(MockResponse().setBody("hello, world!"))
        server.enqueue(MockResponse().setBody("sup, bra?"))
        server.enqueue(MockResponse().setBody("yo dog"))

        // Start the server.
        server.start()

        // Ask the server for its URL. You'll need this to make HTTP requests.
        val baseUrl = server.url("/v1/chat/")

        // Optional: confirm that your app made the HTTP requests you were expecting.
        val request1 = server.takeRequest()
        assertEquals("/v1/chat/messages/", request1.path)
        assertNotNull(request1.getHeader("Authorization"))

        val request2 = server.takeRequest()
        assertEquals("/v1/chat/messages/2", request2.path)

        val request3 = server.takeRequest()
        assertEquals("/v1/chat/messages/3", request3.path)

        // Shut down the server. Instances cannot be reused.
        server.shutdown()
    }
}