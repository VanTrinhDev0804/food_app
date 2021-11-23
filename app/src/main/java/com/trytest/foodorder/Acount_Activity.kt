package com.trytest.foodorder

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.trytest.foodorder.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_acount.*


class Acount_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        var uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        saveBtn.setOnClickListener {
            showProgressBar()
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val bio = etBio.text.toString()

            val user = User(firstName, lastName, bio)
            if (uid != null) {
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        uploadProfilePic()
                    } else {
                        hideProgressBar()
                        Toast.makeText( this@Acount_Activity, "Failed to update profile", Toast.LENGTH_SHORT ).show()
                    }
                }
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.ic_baseline_account_circle_24}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText( this@Acount_Activity, "Profile successfuly update", Toast.LENGTH_SHORT ).show()
        }.addOnFailureListener{
            Toast.makeText( this@Acount_Activity, "Failed to upload the image", Toast.LENGTH_SHORT ).show()
        }
    }
    private fun showProgressBar(){
        dialog = Dialog(this@Acount_Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }
    private fun hideProgressBar(){
        dialog.dismiss();
    }
}