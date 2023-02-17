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
    lateinit var mTask: AsyncTask<String, Int, String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mTask = AsyncTask<String,Int,String>()
            .onPreExecute {
                //Running on main thread
                mBinding.txtCounter.text = "Working"
                Log.e(TAG,"Pre Execute")
            }
            .onPostExecute {
                //Running on main thread
                mBinding.txtCounter.text = "${it}"
                Log.e(TAG,"Post Execute: ${it}")
            }
            .doInBackground { input ->
                //Running on background thread
                var total = 0
                for(i in 0..10){
                    delay(1000)
                    total += i;
                    mTask.publishProgress(total)
                    Log.e(TAG,"Background: ${total}")
                }

                //Here is the differed returned value
                //Note Do not use "return" keyword
                "${input} : ${total} "
            }
            .onProgressUpdate {
                //Running on main thread
                mBinding.txtCounter.text = "${it}"
                Log.e(TAG,"OnProgressUpdate: ${it}")
            }.onCancelled {
                //Running on main thread
                Log.e(TAG,"Cancelled: Thread: ${Thread.currentThread().name}")
            }


        //Execute the task inside coroutine
        CoroutineScope(Dispatchers.Main).launch {
            mTask.execute("Param one","Param two")
        }
        mBinding.button.setOnClickListener {
            mTask.cancel()
        }
    }
}