package campalans.m8.a09

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var captureButton: Button
    private lateinit var recordButton: Button
    private lateinit var videoView: VideoView

    private val captureImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private val recordVideoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val videoUri: Uri? = intent?.data
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        captureButton = findViewById(R.id.btnCamera)
        recordButton = findViewById(R.id.btnRecord)
        videoView = findViewById(R.id.videoView)

        captureButton.setOnClickListener {
            captureImageResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        recordButton.setOnClickListener {
            recordVideoResult.launch(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
        }
    }
}
