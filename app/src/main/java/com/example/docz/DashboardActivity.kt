package com.example.docz

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    //private lateinit var tv_email_id: TextView
    //private lateinit var tv_user_id:TextView
    //private lateinit var etdata: EditText
    private lateinit var idBtnGenerateQR: Button
    private lateinit var btn_logout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        //etdata = findViewById(R.id.etdata)
        idBtnGenerateQR = findViewById(R.id.idBtnGenerateQR);

        idBtnGenerateQR.setOnClickListener {

            val intent = Intent(this@DashboardActivity, GenerateQRCodeActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()


        }

        //val userID= intent.getStringExtra("user_id")
        //val emailID=intent.getStringExtra("email_id")

//        tv_email_id!!.text = "Email ID :: $emailID"
//        tv_user_id!!.text = "User ID :: $userID"
        btn_logout=findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener{
            //logout from app
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@DashboardActivity, MainActivity::class.java))
            finish()
        }

//        val bmp = intent.getParcelableExtra("MyFirstBarcode") as Bitmap?
//        val myBarcode = findViewById<ImageView>(R.id.myBarcode)
//        myBarcode.setImageBitmap(bmp)


    }
}