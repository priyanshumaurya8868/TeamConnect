package com.acompworld.teamconnect

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.acompworld.teamconnect.api.service.MyClient
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.acompworld.teamconnect", appContext.packageName)
    }

    @Test
    fun myWall(){
        runBlocking{
            val api = MyClient.api
            val response = api.getMyWall("wrihq")
            assertNotNull(response.body())
        }
    }

    @Test
    fun getEmployeeByID(){
        runBlocking {
            val api = MyClient.api
            val response = api.getEmployeeById("wrihq","087277",)
            assertNotNull(response.body())
        }
    }

    @Test
    fun getContactByID(){
        runBlocking {
            val api = MyClient.api
            val response = api.getContactById("wrihq","2",)
            assertNotNull(response.body())
        }
    }


}