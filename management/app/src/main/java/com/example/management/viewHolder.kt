package com.example.management

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView

class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val assetName: TextView = itemView.findViewById(R.id.assetName)
    val departmentName: TextView = itemView.findViewById(R.id.departmentName)
    val assetSN: TextView = itemView.findViewById(R.id.assetSN)
    val edit1: AppCompatImageButton = itemView.findViewById(R.id.ic_edit1)
    val edit2: AppCompatImageButton = itemView.findViewById(R.id.ic_edit2)
    val edit3: AppCompatImageButton = itemView.findViewById(R.id.ic_edit3)
}

class ChangedAssetHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val assetName: TextView = itemView.findViewById(R.id.assetName)
    val assetSN: TextView = itemView.findViewById(R.id.assetSN)
    val createButton: AppCompatImageButton = itemView.findViewById(R.id.create)
}

class ImageHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val pictureName: TextView = itemView.findViewById(R.id.pictureName)
}

class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val relocation: TextView = itemView.findViewById(R.id.relocation)
    val center: TextView = itemView.findViewById(R.id.center)
    val yelabuga: TextView = itemView.findViewById(R.id.yelabuga)
}