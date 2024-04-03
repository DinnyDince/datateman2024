package com.example.datateman

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.LauncherActivity.ListItem
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class RecyclerViewAdaptor (private val dataTeman: ArrayList<data_teman>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>() {
    private val context: Context

    //view holder digunakan untuk menyimpan referensi dari view-view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView
        val Alamat: TextView
        val NoHP: TextView
        val ListItem: LinearLayout

        //menginisialisasi view yang terpasang pada layout RecyclerView
        init {
            Nama = itemView.findViewById(R.id.namax)
            Alamat = itemView.findViewById(R.id.alamatx)
            NoHP = itemView.findViewById(R.id.no_hpx)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    //membuat view untuk menyiapkan dan memasang layout yang digunakan pada Recycler View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.view_design, parent, false)
        return ViewHolder(V)
    }

    @SuppressLint("SetTextI18n")
    //mengambil nilai atau value pada Recycler View berdasarkan posisi tertentu
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val Nama: String? = dataTeman.get(position).nama
        val Alamat: String? = dataTeman.get(position).alamat
        val NoHP: String? = dataTeman.get(position).no_hp

    //masukkan nilai atau value ke dalam view
    holder.Nama.text = "Nama: $Nama"
    holder.Alamat.text = "Alamat: $Alamat"
    holder.NoHP.text = "NoHP: $NoHP"
    holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            //fungsi untuk edit dan delete
            holder.ListItem.setOnLongClickListener { view ->
                val action = arrayOf("Update", "Delete")
                val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                    when (i) {
                        0 -> {
                            //berpindah ke hal update data untuk ambil data pada listdata_teman
                            val bundle = Bundle()
                            bundle.putString("dataNama", dataTeman[position].nama)
                            bundle.putString("dataAlamat", dataTeman[position].alamat)
                            bundle.putString("dataNoHP", dataTeman[position].no_hp)
                            bundle.putString("getPrimaryKey", dataTeman[position].key)
                            val intent = Intent(view.context, UpdateData::class.java)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }
                        1 -> {}
                    }
                })
                alert.create()
                alert.show()
                true
            }
            return true
        }
    })
    }

    //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
    override fun getItemCount(): Int {
        return dataTeman.size
    }

    //Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.context = context
    }
}