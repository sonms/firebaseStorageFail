package com.example.firebase_storage

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.example.firebase_storage.databinding.ActivityStorageBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class StorageActivity : AppCompatActivity() {
    lateinit var binding: ActivityStorageBinding
    lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //LayoutInflater는 XML에 정의된 Resource를 View 객체로 반환해주는 역할을 한다.
        //우리가 매번 사용하는 onCreate() 메서드에 있는setContentView(R.layout.activity_main) 또한 Inflater 역할을 한다.
        //(이 함수의 내부에서 layout inflater가 실행되어 view들을 객체화한다.)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_storage)

        binding.selectBtn.setOnClickListener {
            selectImage()
        }
        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("uploadFile ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = "IMAGE_${SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date())}_.png"
       
        val storageReference = FirebaseStorage.getInstance()
        val imageRef = storageReference!!.reference.child("images/").child(fileName)

       .putFile(uri).addOnSuccessListener {
            binding.firebaseimage.setImageURI(uri)
            Toast.makeText(this@StorageActivity, "success upload", Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener{
            if (progressDialog.isShowing) progressDialog.dismiss()

        Toast.makeText(this@StorageActivity, "Failed",Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            uri = data?.data!!
            binding.firebaseimage.setImageURI(uri)
        }
    }
}
