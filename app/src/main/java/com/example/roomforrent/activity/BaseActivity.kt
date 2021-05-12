/**
 *
 * BaseActivity
 *
 * 2021/03/19 YHZ Create New
 *
 * Common Function
 */
package com.example.roomforrent.activity

import android.app.AlertDialog
import android.app.Dialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.dialog_progress.*


open class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun checkConnection() : Boolean {
        val conMgr: ConnectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        Log.i("CheckInternet", netInfo.toString())
        if (netInfo == null) {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle(resources.getString(R.string.connectionError))
                setMessage(resources.getString(R.string.checkConnection))
                setPositiveButton(resources.getString(R.string.ok), null)
            }

            builder.show()
            return  false
        }else return true
    }
}