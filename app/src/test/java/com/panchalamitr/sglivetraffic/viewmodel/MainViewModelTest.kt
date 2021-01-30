package com.panchalamitr.sglivetraffic.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.google.common.truth.Truth.assertThat
import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.model.ErrorTrafficImages
import com.panchalamitr.sglivetraffic.model.TrafficData
import com.panchalamitr.sglivetraffic.repository.FakeTrafficImagesRepository
import com.panchalamitr.sglivetraffic.utils.MainCoroutineRule
import com.panchalamitr.sglivetraffic.utils.getOrAwaitValueTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest  {

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

    private lateinit var savedStateHandle : SavedStateHandle

    private lateinit var trafficImagesRepository: FakeTrafficImagesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

        trafficImagesRepository = FakeTrafficImagesRepository()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testIsResultSuccess() {
        runBlockingTest {

            val networkResponse = NetworkResponse.Success(TrafficData(), null, 101)
            trafficImagesRepository.networkType(networkResponse)
            mainViewModel = MainViewModel(trafficImagesRepository, savedStateHandle)
            mainViewModel.fetchTrafficData()
            val result = mainViewModel.observeTrafficCameras().getOrAwaitValueTest()
            assertThat(result.isNotEmpty())
        }
    }

    @Test
    fun testIsServerError() {
        runBlockingTest {
            val networkResponse = NetworkResponse.ServerError(ErrorTrafficImages(""), 101, null)
            trafficImagesRepository.networkType(networkResponse)
            mainViewModel = MainViewModel(trafficImagesRepository, savedStateHandle)
            mainViewModel.fetchTrafficData()
            val result = mainViewModel.observeErrorResponse().getOrAwaitValueTest()
            assertThat(result.contains("error"))
        }
    }

    @Test
    fun testIsNetworkError() {
        runBlockingTest {
            val networkResponse = NetworkResponse.NetworkError(IOException(""))
            trafficImagesRepository.networkType(networkResponse)
            mainViewModel = MainViewModel(trafficImagesRepository, savedStateHandle)
            mainViewModel.fetchTrafficData()
            val result = mainViewModel.observeErrorResponse().getOrAwaitValueTest()
            assertThat(result.contains("error"))
        }
    }

    @Test
    fun testIsUnknownError() {
        runBlockingTest {
            val networkResponse = NetworkResponse.UnknownError(IOException(""))
            trafficImagesRepository.networkType(networkResponse)
            mainViewModel = MainViewModel(trafficImagesRepository, savedStateHandle)
            mainViewModel.fetchTrafficData()
            val result = mainViewModel.observeErrorResponse().getOrAwaitValueTest()
            assertThat(result.contains("error"))
        }
    }

}