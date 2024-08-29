package com.github.axet.bookreader.dialog


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import com.github.axet.bookreader.keyboard_height.KeyboardHeightProvider
import com.github.axet.bookreader.util.Util
import org.geometerplus.zlibrary.ui.android.databinding.DialogPageBinding
import java.lang.NumberFormatException

class DialogPage(
  private val context: Context,
  private val activity: Activity,
  private val pageStart: Int,
  private val pageEnd: Int
) : Dialog(context){
  private lateinit var binding: DialogPageBinding
  var actionCancel: (() -> Unit)? = null
  var actionOk: ((Int) -> Unit)? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    setCancelable(false)
    val window = window
    window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding = DialogPageBinding.inflate(LayoutInflater.from(context))
    setContentView(binding.root)
    binding.navigationCancel.setOnClickListener {
      Util.closeKeyboard(context,binding.navigationEditText)
      actionCancel?.invoke()

    }
    binding.navigationOk.setOnClickListener {
      val text = binding.navigationEditText.text.toString()
      if (text.isEmpty() || !isTextDigital(text)) {
        return@setOnClickListener
      }
      try {
        val pageSelect = text.toInt()
        if (pageSelect < pageStart || pageSelect > pageEnd) {
          binding.navigationEditText.error = "Số trang không hợp lệ"
        } else {
          Util.closeKeyboard(context)
          actionOk?.invoke(pageSelect)
        }
      } catch (e: NumberFormatException) {
        binding.navigationEditText.error = "Số trang không hợp lệ"
      }
    }

    Handler(Looper.myLooper()!!).postDelayed({
      binding.navigationEditText.requestFocus()
      Util.showKeyboard(context,binding.navigationEditText)
    },100)

    binding.navigationEditText.hint = "Chuyển tới trang ($pageStart-$pageEnd)"
    super.onCreate(savedInstanceState)
  }

  private fun isTextDigital(text: String): Boolean {
    return text.matches("\\d+".toRegex())
  }

}