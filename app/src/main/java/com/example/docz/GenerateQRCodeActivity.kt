package com.example.docz

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.UriUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayOutputStream


class GenerateQRCodeActivity : AppCompatActivity() {

    //private lateinit var idForQrCode: FrameLayout;
    //private lateinit var manager: WindowManager
    //interface WindowManager : ViewManager
    private lateinit var idForQrCodeText: TextView
    private lateinit var idEdtdata:TextInputEditText
    private lateinit var idForQrCodeImage: ImageView
    private lateinit var generateQrBtn: Button
    private lateinit var storage:FirebaseStorage
    private lateinit var filepath: Uri
    private lateinit var sendToAdmin: Button



    // Create a storage reference from our app

    //private lateinit var QRGEncoder qrgEncoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qrcode)
        idForQrCodeText = findViewById(R.id.idForQrCodeText)
        idEdtdata = findViewById(R.id.idEdtdata)
        idForQrCodeImage = findViewById(R.id.idForQrCodeImage)
        generateQrBtn = findViewById(R.id.generateQrBtn)
        sendToAdmin = findViewById<Button>(R.id.sendToAdmin)




        generateQrBtn.setOnClickListener {

            val data = idEdtdata.text.toString().trim()
            if(data.isEmpty()){
                Toast.makeText(this, "Enter some text", Toast.LENGTH_SHORT).show()
            }else{
                val writer= QRCodeWriter()
                try{
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    var bmp = Bitmap.createBitmap(width, height,Bitmap.Config.RGB_565)
                    for(x in 0 until width){
                        for(y in 0 until height)
                            bmp.setPixel(x,y, if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                    }
                    idForQrCodeImage.setImageBitmap(bmp)
//                    var cachedPath =
//                        "CREATE A FOLDER AND ADD (.) BEFORE THE FOLDER NAME TO MAKE IT INVISIABLE TO THE USER"
//                    ImageUtils.save(bmp, cachedPath, Bitmap.CompressFormat.PNG);
//
//                    ImageUtils.save(bitmap,cachedPath, Bitmap.CompressFormat.JPEG)
//                    val bitmap2Uri = UriUtils.file2Uri(FileUtils.getFileByPath(cachedPath))
//                    //base64
                    val bao = ByteArrayOutputStream()
                    bmp.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        bao
                    ) // bmp is bitmap from user image file

//                    bmp.recycle()
                    if (bmp != null && !bmp.isRecycled()) {
                        //bmp.recycle();
                        bmp = null;
                    }
                    val byteArray = bao.toByteArray()
                    //val imageB64: String = Base64.encodeToString(byteArray, Base64.URL_SAFE)
                    //erase this code
                   val user = FirebaseAuth.getInstance()!!.currentUser!!.uid

//                    val user = Firebase.auth.currentUser
//                    user?.let {
//                        // Name
//                        val name = user.displayName
//                        //val uid = user.uid
//                    }
                    //firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseUser#public-abstract-string-getuid
                    //val sref = FirebaseStorage.getInstance().getReference();

                    val storageRef = FirebaseStorage.getInstance().getReference()
                    // Create a reference to "myImage.jpg"
                    val barcodeRef = storageRef.child("${user}.jpg")

                   // Create a reference to 'barcodes/myImage.jpg'
                    val barcodeImagesRef = storageRef.child("Barcodes/${user}.jpg")

                    // While the file names are the same, the references point to different files
                    barcodeRef.name == barcodeImagesRef.name // true
                    barcodeRef.path == barcodeImagesRef.path // false

                    var uploadTask = barcodeImagesRef.putBytes(byteArray)

                    uploadTask.addOnFailureListener{
                        Log.e("TAG","Couldn't upload an image")
                    }.addOnSuccessListener {
                        Log.e("TAG","Image uploaded successfully ${it.metadata}")
                    }
                    //FirebaseStorage().getReference("Barcodes/").child("user.jpeg").putString(imageB64,'base64')
//                    firebase.storage().ref('/Barcodes/').child(user)
//                        .putString(imageB64, ‘base64’, {contentType:’image/jpg’});
//                    val intent = Intent(this@GenerateQRCodeActivity, DashboardActivity::class.java)
//                    intent.putExtra("MyFirstBarcode", bmp)
//                    //startActivity(intent)

//                    // Create file metadata including the content type
//                    var metadata = storageMetadata {
//                        contentType = "image/jpg"
//                    }
//
//                    var storageRef = storage.reference
//
//                    // Upload the file and metadata
//                    var uploadTask =
//                        storageRef.child("Barcodes/first.jpg").putFile(imageB64, metadata)
                }catch (e: WriterException){
                    e.printStackTrace()
                }

//                val user = FirebaseAuth.getInstance().currentUser
//
//                // Create a storage reference from our app
//                var storageRef = storage.reference
//
//                // Points to the root reference
//                storageRef = storage.reference
//
//               // Points to "barcodes"
//                var barcodeRef = storageRef.child("Barcodes")
//
//// Points to "images/space.jpg"
//// Note that you can use variables to create child values
//                val fileName = "space.jpg"
//                spaceRef = imagesRef.child(fileName)
//
//             // File path is "images/space.jpg"
//                val path = spaceRef.path
//
//// File name is "space.jpg"
//                val name = spaceRef.name
//
//// Points to "images"
//                imagesRef = spaceRef.parent



            }







//            fun convertBitmapToFile(context: Context, bitmap: Bitmap): Uri {
//                val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + user)
//                file.createNewFile()
//                // Convert bitmap to byte array
//                val baos = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos) // It can be also saved it as JPEG
//                val bitmapdata = baos.toByteArray()
//
//                return ;
//            }
        }

//        sendToAdmin.setOnClickListener {
//
//            val data = idEdtdata.text.toString().trim()
//            val writer= QRCodeWriter()
//            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
//            val width = bitMatrix.width
//            val height = bitMatrix.height
//            var bmp = Bitmap.createBitmap(width, height,Bitmap.Config.RGB_565)
//            for(x in 0 until width){
//                for(y in 0 until height)
//                    bmp.setPixel(x,y, if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
//            }
//
//            //base64
//            val bao = ByteArrayOutputStream()
//            bmp.compress(
//                Bitmap.CompressFormat.PNG,
//                100,
//                bao
//            ) // bmp is bitmap from user image file
//
////                    bmp.recycle()
//            if (bmp != null && !bmp.isRecycled()) {
//                bmp.recycle();
//                bmp = null;
//            }
//            val byteArray = bao.toByteArray()
//            //val imageB64: String = Base64.encodeToString(byteArray, Base64.URL_SAFE)
//
//
//            //erase this code
//            val user = FirebaseAuth.getInstance().currentUser
//            //val sref = FirebaseStorage.getInstance().getReference();
//
//            val storageRef = FirebaseStorage.getInstance().getReference()
//
//            // Create a reference to "mountains.jpg"
//            val barcodeRef = storageRef.child("${user}.jpg")
//
//            // Create a reference to 'images/mountains.jpg'
//            val barcodeImagesRef = storageRef.child("Barcodes/${user}.jpg")
//
//            // While the file names are the same, the references point to different files
//            barcodeRef.name == barcodeImagesRef.name // true
//            barcodeRef.path == barcodeImagesRef.path // false
//
////                    val uploader : StorageReference
//            //val barcodeImagesRef = sref.child("Barcodes/")
//            // val stream = FileInputStream(File("Barcodes/"))
//            var uploadTask = barcodeImagesRef.putBytes(byteArray)
//
//            uploadTask.addOnFailureListener{
//                Log.e("TAG","Couldn't upload an image")
//            }.addOnSuccessListener {
//                Log.e("TAG","Image uploaded successfully ${it.metadata}")
//            }
//        }





//        val sref = FirebaseStorage.getInstance().getReference(); // please go to above link and setup firebase storage for android





//        goBack.setOnClickListener {
//            val intent = Intent(this@GenerateQRCodeActivity, DashboardActivity::class.java)
//            intent.flags =
//                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.putExtra("idForQrCodeImage", )
//            startActivity(intent)
//        }




    }
}