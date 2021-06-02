package com.example.androidchallenge.view.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.example.androidchallenge.view.CurrencyOption

class CurrencyTypeAdapter (
    private val currencyType: List<CurrencyTypeModel>
): RecyclerView.Adapter<CurrencyTypeAdapter.CurrencyTypeViewHolder>() {

    lateinit var onclickListener: (CurrencyTypeModel) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyTypeViewHolder {
        return CurrencyTypeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_currency_type,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = currencyType.size

    override fun onBindViewHolder(holder: CurrencyTypeViewHolder, position: Int) {
        holder.bind(currencyType[position])
        val model = currencyType[position]
        holder.itemView.setOnClickListener{onclickListener(model)}
    }

    inner class CurrencyTypeViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val country = view.findViewById<AppCompatTextView>(R.id.tvCountry)
        private val rate = view.findViewById<AppCompatTextView>(R.id.tvCurrencyType)
        private val image = view.findViewById<AppCompatImageView>(R.id.ivCountryFlag)

        fun bind(model: CurrencyTypeModel){
            model.code?.let { image.setImageResource(getFlagCountryImage(it)) }
            country.text = model.description
            rate.text = model.rate.toString()
        }

        private fun getFlagCountryImage(code: String): Int {
            return when (code) {
                CurrencyOption.EUR.id -> {
                    R.drawable.flag_european
                }
                CurrencyOption.USD.id -> {
                    R.drawable.flag_eeuu
                }
                CurrencyOption.CAD.id -> {
                    R.drawable.flag_canada
                }
                CurrencyOption.JPY.id -> {
                    R.drawable.flag_japon
                }
                CurrencyOption.PEN.id -> {
                    R.drawable.flag_peru
                }
                CurrencyOption.CHF.id -> {
                    R.drawable.flag_switzerlang
                }
                CurrencyOption.GBP.id -> {
                    R.drawable.flag_united_kingdom
                }
                else -> R.drawable.flag_european
            }
        }
    }
}