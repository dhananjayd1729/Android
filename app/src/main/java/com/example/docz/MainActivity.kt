package com.example.docz

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {




    //private lateinit var edUsername: EditText
    //private lateinit var btnRegister: TextView
    //private lateinit var edPassword: TextView
    //private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var data:DocumentSnapshot
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //`val btnLogin by lazy { findViewById(Button) }(R.id.btnLogin)

        var btnLogin = findViewById(R.id.btnLogin) as Button
        //var btnRegister: TextView
        var btnRegister = findViewById<TextView>(R.id.btnRegister) as TextView
        var edPassword = findViewById<TextView>(R.id.edPassword)

        var edUsername = findViewById<TextView>(R.id.edUsername)


        //val fAuth = FirebaseAuth.getInstance()
        val fStore = FirebaseFirestore.getInstance()


        btnLogin.setOnClickListener {

            when {

                TextUtils.isEmpty(edUsername.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter your email ID",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(edPassword.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(this@MainActivity, "Please enter password", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {

                    val email: String = edUsername.text.toString().trim() { it <= ' ' }
                    val password: String = edPassword.text.toString().trim() { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                if (task.isSuccessful) {
                                    //firebase registered user
                                    //val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@MainActivity,
                                        "You are logged in successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    checkUserAccessLevel(FirebaseAuth.getInstance().currentUser!!.uid)
                                    val intent =
                                        Intent(this@MainActivity, DashboardActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra(
                                        "user_id",
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                }
            }


//        btnLogin.setOnClickListener{
//            if(edUsername.text.trim().isNotEmpty()){
//                Toast.makeText(this, "Input provided",Toast.LENGTH_LONG).show()
//            }else{
//                Toast.makeText(this, "Input required",Toast.LENGTH_LONG).show()
//            }
//
//        }


            //btnRegister = findViewById(R.id.btnRegister)

            btnRegister.setOnClickListener {
                intent = Intent(this, RegisterActivity::class.java);
                startActivity(intent)
            }

        }

    }

    private fun checkUserAccessLevel(uid: String) {

        //lateinit var fStore:FirebaseFirestore
        val fStore = FirebaseFirestore.getInstance()
        val docRef = fStore.collection("Users").document(uid as String)

        //extraxt data from the document firestore
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d("TAG", "No such document")
            }
            //identify the user access level
            if(document.getString("isAdmin")!= null){
                //user is admin
                val intent=
                    Intent(this@MainActivity, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }

            if(document.getString("isUser")!= null){
                //user is admin
                val intent=
                    Intent(this@MainActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener { exception ->
            Log.w("TAG","Error getting documents.", exception)
        }


    }


}