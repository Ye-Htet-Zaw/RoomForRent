/**
 *
 * BaseFragment
 *
 * 2021/04/21 KMMN Create New
 *
 * Common Fragment Function
 */
package com.example.roomforrent.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.dialog_progress.*


open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog = context?.let { Dialog(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }
    fun showProgressDialog(text: String) {
        mProgressDialog = context?.let { Dialog(it) }!!

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
        val conMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        Log.i("CheckInternet", netInfo.toString())
        if (netInfo == null) {
            val builder = AlertDialog.Builder(context)
            with(builder) {
                setTitle(resources.getString(R.string.connectionError))
                setMessage(resources.getString(R.string.checkConnection))
                setPositiveButton(resources.getString(R.string.ok), null)
            }

            builder.show()
            return  true
        }else return false
    }
}