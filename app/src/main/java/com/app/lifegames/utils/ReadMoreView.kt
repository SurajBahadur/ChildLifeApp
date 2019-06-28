package com.app.lifegames.utils.views

import android.animation.LayoutTransition
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.lifegames.views.TextLengthType

class ReadMoreView private constructor(builder: Builder) {

    private val textLength: Int
    private val textLengthType: TextLengthType
    private val moreLabel: String
    private val lessLabel: String
    private val moreLabelColor: Int
    private val lessLabelColor: Int
    private val labelUnderLine: Boolean
    private val expandAnimation: Boolean

    init {
        this.textLength = builder.textLength
        this.textLengthType = builder.textLengthType
        this.moreLabel = builder.moreLabel
        this.lessLabel = builder.lessLabel
        this.moreLabelColor = builder.moreLabelColor
        this.lessLabelColor = builder.lessLabelColor
        this.labelUnderLine = builder.labelUnderLine
        this.expandAnimation = builder.expandAnimation
    }

    fun addReadMoreTo(textView: TextView, text: String) {

        if (textLengthType == TextLengthType.TYPE_CHARACTER) {
            if (text.length <= textLength) {
                textView.text = text
                return
            }
        } else {
            // If TYPE_LINE
            textView.setLines(textLength)
            textView.text = text
        }

        textView.post(Runnable {
            var textLengthNew = textLength

            if (textLengthType == TextLengthType.TYPE_LINE) {
                if (textView.layout.lineCount <= textLength) {
                    textView.text = text
                    return@Runnable
                }

                val lp = textView.layoutParams as ViewGroup.MarginLayoutParams
                val subString = text.substring(textView.layout.getLineStart(0),
                        textView.layout.getLineEnd(textLength - 1))
                textLengthNew = subString.length - (moreLabel.length + 4 + lp.rightMargin / 6)
            }

            val ss = SpannableString(text.substring(0, textLengthNew) + "... " + moreLabel)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    addReadLess(textView, text)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = labelUnderLine
                    ds.color = moreLabelColor
                }
            }
            ss.setSpan(clickableSpan, ss.length - moreLabel.length, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && expandAnimation) {
                val layoutTransition = LayoutTransition()
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                (textView.parent as ViewGroup).layoutTransition = layoutTransition
            }

            textView.text = ss
            textView.movementMethod = LinkMovementMethod.getInstance()
        })
    }

    private fun addReadLess(textView: TextView, text: String) {
        textView.maxLines = Integer.MAX_VALUE
        val ss = SpannableString("$text $lessLabel")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Handler().post { addReadMoreTo(textView, text) }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = labelUnderLine
                ds.color = lessLabelColor
            }
        }
        ss.setSpan(clickableSpan, ss.length - lessLabel.length, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    class Builder {
        var textLength = 100
        var textLengthType = TextLengthType.TYPE_CHARACTER
        var moreLabel = "read more"
        var lessLabel = "read less"
        var moreLabelColor = Color.parseColor("#ff00ff")
        var lessLabelColor = Color.parseColor("#ff00ff")
        var labelUnderLine = false
        var expandAnimation = false

        /**
         * default length is 100 with character TYPE_CHARACTERS
         */
        fun textLength(length: Int, textLengthType: TextLengthType): Builder {
            this.textLength = length
            this.textLengthType = textLengthType
            return this
        }

        fun moreLabel(moreLabel: String): Builder {
            this.moreLabel = moreLabel
            return this
        }

        fun lessLabel(lessLabel: String): Builder {
            this.lessLabel = lessLabel
            return this
        }

        fun moreLabelColor(moreLabelColor: Int): Builder {
            this.moreLabelColor = moreLabelColor
            return this
        }

        fun lessLabelColor(lessLabelColor: Int): Builder {
            this.lessLabelColor = lessLabelColor
            return this
        }

        fun labelUnderLine(labelUnderLine: Boolean): Builder {
            this.labelUnderLine = labelUnderLine
            return this
        }

        /**
         * @param expandAnimation either true to enable animation on expand or false to disable animation
         * - default is false
         * @return Builder obj
         */
        fun expandAnimation(expandAnimation: Boolean): Builder {
            this.expandAnimation = expandAnimation
            return this
        }

        fun build(): ReadMoreView {
            return ReadMoreView(this)
        }
    }
}