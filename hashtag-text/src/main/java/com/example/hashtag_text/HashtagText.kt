package com.example.hashtag_text

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import java.util.regex.Pattern

class HashtagText constructor(
    context: Context, attrs: AttributeSet?
): AppCompatEditText(context, attrs) {
    init {
        attrs?.let { (it) }
    }
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        val targetIndexList = indexOfAll(text.toString(), '#')
        val hashIndexList = findHashIndex(text.toString(), targetIndexList)
        if(targetIndexList.isNotEmpty()) {
            for (i in 0 until targetIndexList.size) {
                if (targetIndexList.size == hashIndexList.size) {
                    this@HashtagText.text?.setSpan(
                        ForegroundColorSpan(Color.GREEN),
                        targetIndexList[i],
                        hashIndexList[i],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    private fun indexOfAll(text: String, target: Char): MutableList<Int> {
        val targetIndexList = mutableListOf<Int>()
        println(text)
        for (i in text.indices) {
            if (text[i] == target) {
                targetIndexList.add(i)
            }
        }
        return targetIndexList
    }

    private fun findHashIndex(text: String, targetIndexList: MutableList<Int>): MutableList<Int> {
        val hashIndexList = mutableListOf<Int>()
        for (i in targetIndexList) {
            for (j in i + 1 until text.length) {
                if (text[j] == ' ') {
                    hashIndexList.add(j)
                    break
                } else if (text[j] == '#') {
                    break
                }
            }
        }
        return hashIndexList
    }


}
