package com.example.hashtag_text

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText

class HashtagText constructor(
    context: Context, attrs: AttributeSet?
): AppCompatEditText(context, attrs) {

    private val tagList: ArrayList<String>? = null
    init {
        attrs?.let {(it) }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text != null) {
            hashText(text)
        }

        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }
    private fun hashText(text:CharSequence) {

        val size: Int = text.length
        val regex = Regex("^#\\s$")

        val matchResult: Sequence<MatchResult> = regex.findAll("text")
        Log.d("ww", "hashText: $matchResult")
//        Log.d("ee", "hashText: ${ text.indexOf('#')}")


//        if(size>0){
//            for (i: Int in 0 until size) {
////                Log.d("eee", "hashText: $i")
//                if(text[i] == '#'){
//                    val test :String = "sodded"
//                    test.
//                    Log.d("dddd", "hashText: ${text[i]}")
//                }
//            }
//        }

    }


}
