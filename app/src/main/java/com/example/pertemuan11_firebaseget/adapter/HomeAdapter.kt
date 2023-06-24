package com.example.pertemuan11_firebaseget.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan11_firebaseget.R
import com.example.pertemuan11_firebaseget.databinding.UserListBinding
import com.example.pertemuan11_firebaseget.model.Mahasiswa
import com.google.firebase.database.FirebaseDatabase

class HomeAdapter (private val dataMhs : ArrayList<Mahasiswa>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder (val binding : UserListBinding) : RecyclerView.ViewHolder(binding.root)
    private val databaseRef = FirebaseDatabase.getInstance().getReference("mahasiswa")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataMhs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtNama.text = dataMhs[position].nama
        holder.binding.txtNim.text = dataMhs[position].nim
        holder.binding.txtTelepon.text = dataMhs[position].telepon

        holder.binding.cardView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString ("id", dataMhs[position].id)
            bundle.putString("nim", dataMhs[position].nim)
            bundle.putString("nama", dataMhs[position].nama)
            bundle.putString("telepon", dataMhs[position].telepon)
            findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment,bundle)
        }

        holder.binding.btnDellete.setOnClickListener{
            val mhsId = dataMhs[position].id
            databaseRef.child(mhsId).removeValue()
            Toast.makeText(it.context,"Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        }

        holder.binding.btnEdit.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("id", dataMhs[position].id)
            bundle.putString("nim", dataMhs[position].nim)
            bundle.putString("nama", dataMhs[position].nama)
            bundle.putString("telepon", dataMhs[position].telepon)
            findNavController(it).navigate(R.id.action_homeFragment_to_updateFragment,bundle)
        }

    }

    fun setData(newData: ArrayList<Mahasiswa>) {
        dataMhs.clear()
        dataMhs.addAll(newData)
        notifyDataSetChanged()
    }
}