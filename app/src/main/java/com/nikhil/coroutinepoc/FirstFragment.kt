@file:OptIn(ExperimentalTime::class)

package com.nikhil.coroutinepoc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nikhil.coroutinepoc.databinding.FragmentFirstBinding
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    companion object {
        private const val TAG = "FirstFragment"
    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun coroutineExample() {
        // example1()
        //example2()
        // example3()
        //  example4()
        // example5()
       // example6()
      // example7()
        example8()
    }

    private fun example8() {


    }

    @OptIn(ExperimentalTime::class)
    private fun example7() {
        lifecycleScope.launch(Dispatchers.IO) {
            val time = measureTime {

                val login = async {
                     loginAPI()

                }

                val home = async {
                    homeAPI()
                }
                Log.d(TAG, "example7 loginAPI : ${login.await()}")
                Log.d(TAG, "example7 loginAPI : ${home.await()}")
            }
            Log.d(TAG, "example7: $time")
        }

    }

    private fun example6() {
        lifecycleScope.launch(Dispatchers.IO) {
            val time = measureTime {
                val job1 = launch {
                    Log.d(TAG, "example6 loginAPI : ${loginAPI()}")
                }

                val job2 = launch {
                    Log.d(TAG, "example6 homeAPI : ${homeAPI()}")
                }
                job1.join()
                job2.join()
            }
            Log.d(TAG, "example6: $time")
        }
    }

    private fun example5() {


        val job = GlobalScope.launch(Dispatchers.Default) {
            withTimeout(5000L) {
                Log.d(TAG, "example5 before fibonacci loop ")

                for (i in 40..50) {
                    if (isActive) {
                        Log.d(TAG, "example5:fibonacci($i) = ${fibonacci(i.toLong())} ")
                    } else {
                        Log.d(TAG, "example5:isActive false for $i ")
                    }

                }

                Log.d(TAG, "example5 after fibonacci loop ")
            }
        }
        runBlocking {
            // job.join()
            //  delay(2000)
            // job.cancel()
            Log.d(TAG, "example5 job.cancel() called ")
        }

    }


    fun fibonacci(n: Long): Long {
        return if (n == 0L || n == 1L) n else fibonacci(n - 1) + fibonacci(n - 2)
    }

    private fun example4() {
        //run on main thread
        runBlocking {
            Log.d(TAG, "example4 thread is  ${Thread.currentThread().name}")

            launch(Dispatchers.IO) {
                val login = loginAPI()
                Log.d(TAG, "example4 loginAPI thread is  ${Thread.currentThread().name}")
                Log.d(TAG, "example2 loginAPI : $login")
            }
            launch(Dispatchers.IO) {
                val home = homeAPI()
                Log.d(TAG, "example4 homeAPI thread is  ${Thread.currentThread().name}")
                Log.d(TAG, "example2 homeAPI : $home")
            }
        }

    }

    private fun example3() {
        GlobalScope.launch(Dispatchers.IO) {
            val login = loginAPI()
            Log.d(
                TAG,
                "example3 Dispatchers.IO $login and thread is  ${Thread.currentThread().name}"
            )
            withContext(Dispatchers.Main) {
                Log.d(
                    TAG,
                    "example3  Dispatchers.Main $login and thread is  ${Thread.currentThread().name}"
                )
            }
        }

    }

    private fun example2() {
        GlobalScope.launch {
            val time = measureTime {
                val login = loginAPI()
                Log.d(TAG, "example2: $login")
                val home = homeAPI()
                Log.d(TAG, "example2: $home")
            }
            Log.d(TAG, "example2: $time")
        }

    }

    private suspend fun loginAPI(): String {
        delay(3000L)
        return "Login API load successfully"
    }

    private suspend fun homeAPI(): String {
        delay(3000L)
        return "Home API load successfully "
    }

    private fun example1() {
        GlobalScope.launch {
            delay(3000)
            Log.d(TAG, "coroutineExample: Thread name inside  ${Thread.currentThread().name}")
        }
        Log.d(TAG, "coroutineExample: Thread name outside  ${Thread.currentThread().name}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        coroutineExample()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}