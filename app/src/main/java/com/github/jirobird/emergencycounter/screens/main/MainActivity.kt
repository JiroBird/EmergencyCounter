package com.github.jirobird.emergencycounter.screens.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.jirobird.emergencycounter.R
import com.github.jirobird.emergencycounter.screens.counter.EmergencyCounterView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity(),
    MainContract.IMainView {

    private val presenter = MainPresenter()
    private var emergencyView: EmergencyCounterView? = null

    private lateinit var tvInfo: TextView
    private lateinit var fabNewIncident: FloatingActionButton
    private lateinit var incidentListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInfo = findViewById(R.id.tv_info)
        fabNewIncident = findViewById(R.id.fab_new_incident)
        incidentListView = findViewById(R.id.rv_incident_list)
        incidentListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        incidentListView.visibility = View.INVISIBLE

        fabNewIncident.setOnClickListener {
            presenter.didNewIncidentButtonPressed()
        }

        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.viewIsReady()
    }

    private fun setFloatButtonStateIdle() {
        fabNewIncident.setImageResource(R.drawable.ic_emergency_alert)
    }

    private fun setFloatButtonStateEmergency(){
        fabNewIncident.setImageResource(R.drawable.ic_stop)
    }

    override fun showNewIncidentCounterScreen(filename:String?){
        incidentListView.visibility = View.INVISIBLE
        setFloatButtonStateEmergency()

        emergencyView = EmergencyCounterView()
        emergencyView?.setFileName(filename)
        emergencyView?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_container, it, EmergencyCounterView::class.java.simpleName)
                .commit()
        }
    }

    override fun dismissIncidentCounterScreen(){
        incidentListView.visibility = View.VISIBLE
        setFloatButtonStateIdle()

        val fragment = supportFragmentManager.findFragmentByTag(EmergencyCounterView::class.java.simpleName)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
    }

    override fun disableUIToContinue(){
        fabNewIncident.visibility = View.GONE
        incidentListView.visibility = View.GONE
        tvInfo.text = "Необходим доступ к файлам для записи, дайте доступ и начните заново"
    }


    override fun showFileList(files:List<String>?) {
        if (files != null && files.isNotEmpty()){
            incidentListView.adapter =
                FileListAdapter(files, clickHolder)
            incidentListView.adapter?.notifyDataSetChanged()
            incidentListView.visibility = View.VISIBLE
            tvInfo.visibility = View.INVISIBLE
        } else {
            incidentListView.adapter = null
            incidentListView.visibility = View.INVISIBLE
            tvInfo.visibility = View.VISIBLE
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1000 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenter.didPermissionGranted(true)
                } else {

                }
                return
            } else -> {

            }
        }
    }

    private val clickHolder = object :
        FileListAdapter.FileListAdapterClickListener {
        override fun onItemClicked(position: Int, fileName: String) {

            val dir = File("${this@MainActivity.filesDir.absolutePath}/rintd")
            val file = File(dir, fileName)

            if( file.exists()) {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                sendIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.absolutePath))
                sendIntent.type = "txt/*"
                startActivity(Intent.createChooser(sendIntent, "Send log via:"))
            }
        }
    }
}
