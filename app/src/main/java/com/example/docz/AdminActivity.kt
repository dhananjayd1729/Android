package com.example.docz

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

private lateinit var adminLogout:Button

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        adminLogout = findViewById<Button>(R.id.adminLogout)



//        val bmp = intent.getParcelableExtra("MyFirstBarcode") as Bitmap?
//        val barcodeAdmin = findViewById<ImageView>(R.id.barcodeAdmin)
//        barcodeAdmin.setImageBitmap(bmp)



//        val extras = intent.extras
//        if (extras != null){
//            // retrieve the data using keyName
//            val data = extras!!.getString("MyFirstBarcode")
//        }

        adminLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@AdminActivity, MainActivity::class.java))
            finish()
        }



    }
}