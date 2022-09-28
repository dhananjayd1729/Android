package com.example.docz

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    //private lateinit var buttonLogin: TextView
    //private lateinit var Regbutton: Button
    //private lateinit var textInputEditText:android.widget.EditText
    //private lateinit var editTextTextEmailAddress: android.widget.EditText
    //private lateinit var editTextTextPassword: android.widget.EditText
    //private lateinit var editTextTextPassword2: android.widget.EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var textInputEditText = findViewById<android.widget.EditText>(R.id.textInputEditText)
        var editTextTextEmailAddress = findViewById<android.widget.EditText>(R.id.editTextTextEmailAddress)
        var editTextTextPassword = findViewById<android.widget.EditText>(R.id.editTextTextPassword)
        var editTextTextPassword2 = findViewById<EditText>(R.id.editTextTextPassword2)
        var buttonLogin = findViewById<TextView>(R.id.buttonLogin)
        var Regbutton = findViewById<Button>(R.id.Regbutton)

        fStore = FirebaseFirestore.getInstance()

        Regbutton.setOnClickListener{

            when{
                TextUtils.isEmpty(textInputEditText.text.toString().trim(){it<= ' '}) ->{
                    Toast.makeText(this@RegisterActivity,"Please enter your Name", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString().trim(){it<= ' '}) ->{
                    Toast.makeText(this@RegisterActivity,"Please enter email ID", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextPassword.text.toString().trim(){it<= ' '}) ->{
                    Toast.makeText(this@RegisterActivity,"Please enter Roll Number", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextPassword2.text.toString().trim(){it<= ' '}) ->{
                    Toast.makeText(this@RegisterActivity,"Please enter password", Toast.LENGTH_SHORT).show()
                }

            else -> {

            val email: String = editTextTextEmailAddress.text.toString().trim() { it <= ' ' }
            val password: String = editTextTextPassword2.text.toString().trim() { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                    if (task.isSuccessful) {
                        //firebase registered user

                        Toast.makeText(
                            this@RegisterActivity,
                            "You are registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        //firestore thing
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val docRef = fStore.collection("Users").document(firebaseUser.uid)
                        val userInfo:HashMap<String,String> = HashMap()
                        userInfo.put("Email ID",editTextTextEmailAddress.text.toString())
                        userInfo.put("Roll Number",editTextTextPassword.text.toString())
                        userInfo.put("Name",textInputEditText.text.toString())
                        //specify whether user is admin
                        userInfo.put("isUser", "1")

                        docRef.set(userInfo)

                        val intent = Intent(this@RegisterActivity, DashboardActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }}
        }

        buttonLogin.setOnClickListener{
            intent= Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

    }




//    fun createUser(email:String,password:String){
//        auth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener(this){ task->
//
//                if (task.isSuccessful){
//                    Log.e("Task Message", "Successful ...");
//                }else{
//                    Log.e("Task Message", "Failed ..." +task.exception);
//                }
//            }
//    }
}


