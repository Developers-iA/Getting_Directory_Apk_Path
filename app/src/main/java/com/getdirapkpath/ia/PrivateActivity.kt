package com.getdirapkpath.ia

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrivateActivity : AppCompatActivity() {

    private lateinit var result: TextView
    private lateinit var buttonBaseAPK: Button
    private lateinit var buttonNative: Button
    private lateinit var buttonUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private)
        result = findViewById(R.id.textview1)
        buttonBaseAPK = findViewById(R.id.button1)
        buttonBaseAPK.setOnClickListener(View.OnClickListener {
            result.setText(getApkBase(this@PrivateActivity))
            CopyToClipboard()
        })
        buttonNative = findViewById(R.id.button2)
        buttonNative.setOnClickListener(View.OnClickListener {
            result.setText(getMyNative(this@PrivateActivity))
            CopyToClipboard()
        })
        buttonUser = findViewById(R.id.button3)
        buttonUser.setOnClickListener(View.OnClickListener {
            result.setText(getUserDirectory(this@PrivateActivity))
            CopyToClipboard()
        })
    }

    fun CopyToClipboard() {
        result.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                this@PrivateActivity, """
     Copied to clipboard 
     ${result.getText()}
     """.trimIndent(), Toast.LENGTH_SHORT
            ).show()
            (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText(
                    "clipboard",
                    result.getText().toString()
                )
            )
        })
    }

    companion object {
        fun getApkBase(context: Context?): String? {
            val packageName = context?.getPackageName()
            val pm = context?.getPackageManager()
            try {
                val ai = packageName?.let { pm?.getApplicationInfo(it, 0) }
                return ai?.publicSourceDir
            } catch (ignored: Throwable) {
            }
            return null
        }

        fun getMyNative(context: Context?): String? {
            val packageName = context?.getPackageName()
            val pm = context?.getPackageManager()
            try {
                val ai = packageName?.let { pm?.getApplicationInfo(it, 0) }
                return ai?.nativeLibraryDir
            } catch (ignored: Throwable) {
            }
            return null
        }

        fun getUserDirectory(context: Context?): String? {
            val packageName = context?.getPackageName()
            val pm = context?.getPackageManager()
            try {
                val ai = packageName?.let { pm?.getApplicationInfo(it, 0) }
                return ai?.dataDir
            } catch (ignored: Throwable) {
            }
            return null
            /*
            ai.dataDir =  /data/user/0/YourPackageName/
            */
        }
    }
}