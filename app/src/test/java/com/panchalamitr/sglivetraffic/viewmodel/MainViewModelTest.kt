package com.panchalamitr.sglivetraffic.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.google.common.truth.Truth.assertThat
import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.model.TrafficData
import com.panchalamitr.sglivetraffic.repository.FakeTrafficImagesRepository
import com.panchalamitr.sglivetraffic.utils.MainCoroutineRule
import com.panchalamitr.sglivetraffic.utils.getOrAwaitValueTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest  {

    private val mockWebServer = MockWebServer()

    /**
     * Set the main coroutines dispatcher for unit testing.
     */
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    /**
     * It will tell JUnit to force tests to be executed synchronously,
     * especially when using Architecture Components.
     */
    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @MockK
    lateinit var mainViewModel: MainViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testIsResultSuccess() {
        runBlockingTest {
            var trafficImagesRepository = FakeTrafficImagesRepository()

            trafficImagesRepository.networkErrorType()
            val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
            mainViewModel = MainViewModel(trafficImagesRepository, savedStateHandle)
            mainViewModel.fetchTrafficData()
            val result = mainViewModel.observeTrafficCameras().getOrAwaitValueTest()
            assertThat(result.isNotEmpty())
        }
    }

    @Test
    fun testIsResultFail() {
        runBlockingTest {
            var trafficImagesRepository = FakeTrafficImagesRepository()
            trafficImagesRepository.networkErrorType(true)
            val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
            mainViewModel = MainViewModel(trafficImagesRepository, savedStateHandle)
            mainViewModel.fetchTrafficData()
            val result = mainViewModel.observeErrorResponse().getOrAwaitValueTest()
            assertThat(result.contains("error"))
        }
    }

}