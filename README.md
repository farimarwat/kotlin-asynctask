### Kotlin AsyncTask
This is an android library which is an alternate to android java built-in AsyncTask (Now Depreceted)

### Implementation:

    implementation 'io.github.farimarwat:asynctask:1.1'

### Usage:

```
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
```
