package kh.farrukh.arch_mvc.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 *Created by farrukh_kh on 4/3/22 10:38 PM
 *kh.farrukh.arch_mvc.utils
 **/
fun AppCompatActivity.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}