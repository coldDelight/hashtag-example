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
        //전체 # 개수
        val targetIndexList = indexOfAll(text.toString(), '#')
        //연속된 # 제거 찐 # index
        val realIndexList = findRealIndex(targetIndexList,text.toString().length)
        //해시태그 끝나는곳 index
        val hashIndexList = findHashIndex(text.toString(), realIndexList)
        //최대 해시태그 길이
        val maxTextLen = 5


        if (realIndexList.isNotEmpty()) {
            for (i in 0 until realIndexList.size) {
                if (realIndexList.size == hashIndexList.size) {
                    val diff =  hashIndexList[i] - realIndexList[i]
                    if(diff>maxTextLen){
                        this@HashtagText.text?.setSpan(
                            ForegroundColorSpan(Color.GREEN),
                            realIndexList[i],
                            realIndexList[i]+maxTextLen,// 최대크기 까지만
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }else{
                        this@HashtagText.text?.setSpan(
                            ForegroundColorSpan(Color.GREEN),
                            realIndexList[i],
                            hashIndexList[i]+1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

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

    private fun findRealIndex(targetIndexList:MutableList<Int>,textSize:Int):MutableList<Int>{
        val realIndexList = mutableListOf<Int>()
        val size = targetIndexList.size

        var check:Int=-1
        var i:Int = 0

        while (i<size){
             check = targetIndexList[i]
            for(j in i+1 until size){// i 다음값 부터 끝까지
                if(check+1==targetIndexList[j]){
                    i += 1
                    check = targetIndexList[j]
                }else{
                    realIndexList.add(targetIndexList[j-1])
                    break
                }
            }
            i++
        }
        realIndexList.add(check)
        return realIndexList
//        for(i in 0 until size-1){// 0부터 마지막 값은 비교 X
//            Log.d("$i", "IIIIII: ${targetIndexList[i]}")
//
//            var check = targetIndexList[i]
////            Log.d("iiiii", "findRealIndex: ${i}")
//            for(j in i+1 until size){// i 다음값 부터 끝까지
//                Log.d("$j", "JJJJJJ: ${targetIndexList[j]}")
//
//                if(check+1==targetIndexList[j]){
//                    i += 1
//                    check = targetIndexList[j]
//                }else{
//                    Log.d("jjj", "findRealIndex: ${targetIndexList[j-1]}")
//                    break
//                }
//            }
//        }
    }

    private fun findHashIndex(text: String, realIndexList: MutableList<Int>): MutableList<Int> {
        val hashIndexList = mutableListOf<Int>()
        val size = realIndexList.size

        for (i in 0 until size) {
            if(realIndexList[i]!=-1){// #이 하나도 없을때는 X
                for (j in realIndexList[i] + 1 until text.length) {//#시작부터 텍스트 끝까지
                    if (text[j] == ' ') {//공백 나오면 추가후 끝
                        hashIndexList.add(i, j)
                        break
                    } else if (text[j] == '#') {//#나와도 추가후 끝
                        hashIndexList.add(i, j)
                        break
                    } else {
                        if (j==text.length-1){// 하나 씩 증가
                            hashIndexList.add(i, j)
                        }
                    }
                }
            }
        }
        return hashIndexList
    }



}
