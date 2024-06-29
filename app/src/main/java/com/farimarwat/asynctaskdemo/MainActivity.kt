package com.farimarwat.asynctaskdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.farimarwat.asynctaskdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pk.farimarwat.asynctask.AsyncTask
import pk.farimarwat.asynctask.TAG

class MainActivity : AppCompatActivity() {
    val mBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var mTask: AsyncTask<Int, Int, Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mTask = AsyncTask<Int,Int,Int>()
            .onPreExecute {
                mBinding.txtCounter.text = "Calculating"
            }
            .onPostExecute {
                mBinding.txtCounter.text = "Total: ${it}"
            }
            .doInBackground { input ->
                var total = 0
                input.forEach{
                    total += it
                    delay(1000)
                    publishProgress(total)
                }

               total
            }
            .onProgressUpdate {
                mBinding.txtCounter.text = "${it}"

            }.onCancelled {
                //Running on main thread
                Log.e(TAG,"Cancelled: Thread: ${Thread.currentThread().name}")
            }


        mTask.execute(listOf(14,34,63,22))
        mBinding.button.setOnClickListener {
            mTask.cancel()
        }
    }
}