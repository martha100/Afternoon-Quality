package com.example.afternoonsqualite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIdNumber:EditText
    lateinit var mbtnsave:Button
    lateinit var btnview:Button
    lateinit var btndelete:Button
    lateinit var db:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtname)
        edtEmail = findViewById(R.id.medtemail)
        edtIdNumber = findViewById(R.id.mEdtid)
        mbtnsave = findViewById(R.id.mbtnsave)
        btnview = findViewById(R.id.mbtnview)
        btndelete = findViewById(R.id.mbtndelete)
        //Create a database called eMobilisDB
        db = openOrCreateDatabase("eMobilisDB", Context.MODE_PRIVATE,null)
        //create a table inside a database
        db.execSQL("CREATE IF NOT EXITS users(jina VARCHAR,arafa VARCHAR,kitambulisho VARCHAR)")
        //set onClickListener
        mbtnsave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtName.text.toString().trim()
            var idNumber= edtName.text.toString().trim()
            //check if the user is submitting empty fields
            if(name.isEmpty()||email.isEmpty()||idNumber.isEmpty()){
                //uswe the message function to display the message
                message("EMPTY FIELDS","please fill all inputs")
            }else{
                //Proceed to save data
                db.execSQL("INSERT INTO users VALUES('"+name+"','"+email+"','"+idNumber+"',)")
                clear()
                message("SUCCESS!!","User saved successfully!!!")
            }

        }
        btnview.setOnClickListener {
            var cursor = db.rawQuery("SELECT* FROM users",null)
            //check if there is any record in the database
            if (cursor.count == 0){
                message("NO RECORDS!!!","Sorry,no users found!!!")

            }else{
                //use a string buffer to append all users retrieved using a loop
                var buffer = StringBuffer()
                while(cursor.moveToNext()){
                    var retrievedName = cursor.getString(0)
                    var retrievedEmail = cursor.getString(1)
                    var retrievedIdNumber = cursor.getString(2)
                    buffer.append(retrievedName+"\n")
                    buffer.append(retrievedEmail+"\n")
                    buffer.append(retrievedIdNumber+"\n")
                }
                message("USERS",buffer.toString())
            }

        }
        btndelete.setOnClickListener {
            var idNumber = edtIdNumber.text.toString().trim()
            if(idNumber.isEmpty()){
                message("EMPTY FIELD!","Please fill the id field")
            }else{
                var cursor = db .rawQuery("SELECT * FROM USERS WHERE Kitambulisho'"+idNumber+"'",null)
                if (cursor.count == 0){
                    message("NO RECORD!","Sorry,no user found!!!")
                }else{
                    db.execSQL("DELETE FROM users WHERE kitambulisho='"+idNumber+"'")
                    clear()
                    message("SUCCESS!!!","User deleted successfully")
                }
            }


        }
    }
    fun message(tittle:String,message:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(tittle)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Cancel",null)
        alertDialog.create().show()
    }
    fun clear(){
        edtName.setText("")
        edtEmail.setText("")
        edtIdNumber.setText("")
    }
}