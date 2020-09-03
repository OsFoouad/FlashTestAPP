package com.example.flashtest

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var TheCameraPermission = 200
    private var flashLightState : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        flasher!!.setOnClickListener {

            val permissions = ContextCompat.checkSelfPermission(this , Manifest.permission.CAMERA)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                if (permissions != PackageManager.PERMISSION_GRANTED)
                    getPer()
                else
                    OpenLight()
            } else
                OpenLight()


        }

    }

    private fun getPer () {
        ActivityCompat.requestPermissions(this , arrayOf(Manifest.permission.CAMERA ) , TheCameraPermission)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            TheCameraPermission -> if (grantResults.isEmpty() ||  !grantResults[0].equals(PackageManager.PERMISSION_GRANTED))
                Toast.makeText(this,"no permission" , Toast.LENGTH_SHORT).show()
            else
                OpenLight()

        }


    }

    private fun OpenLight() {

        val camManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val camID = camManager.cameraIdList[0]

        if (!flashLightState)

            try {
                camManager.setTorchMode(camID , true)
                flashLightState = true
            } catch (e: CameraAccessException ) {

            }
        else
            try {
                camManager.setTorchMode(camID , false)
                flashLightState = false
            } catch (e: CameraAccessException ) {

            }

    }


}
