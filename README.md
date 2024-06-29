### Kotlin AsyncTask
This is an android library which is an alternate to android java built-in AsyncTask (Now Depreceted)

### Implementation:

    implementation 'io.github.farimarwat:asynctask:1.2'

### Usage:

```
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
```
### onPreExecute:

- Runs on the main thread before the background task starts.
- Used to set up the UI or show a loading indicator.
- In this example, updates the TextView to show a "Calculating..." message.

### doInBackground:

- Runs on a background thread and performs the main computation.
- Receives input parameters (provided during execute) and returns a result.
- Sums the integers in the provided list and calls publishProgress to update the progress.

### onProgressUpdate:

- Runs on the main thread and updates the UI with progress information.
- Called from doInBackground to provide progress updates.
- Updates the TextView with the current progress in this example.

### onPostExecute:

- Runs on the main thread after the background task completes.
- Receives the result of the background computation and updates the UI.
- Updates the TextView with the result of the sum.

### nError:

- Runs on the main thread if an error occurs during the background computation.
- Updates the TextView with the error message in this example

**Note: Complete example app is included**
