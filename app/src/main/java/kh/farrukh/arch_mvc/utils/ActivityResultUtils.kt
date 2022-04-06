package kh.farrukh.arch_mvc.utils

import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/**
 *Created by farrukh_kh on 4/6/22 6:25 PM
 *kh.farrukh.arch_mvc.utils
 **/
fun AppCompatActivity.startActivityForResult(onResult: SingleBlock<ActivityResult?>) =
    registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult? -> onResult(result) }

fun ActivityResult.getString(key: String) = data?.getStringExtra(key)