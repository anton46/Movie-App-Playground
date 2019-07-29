package com.movie.app.extentions

import org.junit.Assert

infix fun Any?.shouldEqual(theOther: Any?) = Assert.assertEquals(theOther, this)

infix fun Any?.shouldNotEqual(theOther: Any?) = Assert.assertNotEquals(theOther, this)