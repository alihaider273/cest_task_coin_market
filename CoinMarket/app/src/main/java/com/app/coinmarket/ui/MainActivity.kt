package com.app.coinmarket.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.coinmarket.R
import com.app.coinmarket.databinding.ActivityMainBinding
import com.app.coinmarket.internal.base.Resource
import com.app.coinmarket.model.CryptoListData
import com.app.coinmarket.remote.coin.CoinFactory
import com.app.coinmarket.viewmodel.CryptoViewModel
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware, ItemClickCallback {

    private lateinit var binding: ActivityMainBinding
    private var coinViewModel: CryptoViewModel? = null

    private val coinFactory: CoinFactory by instance()
    override val kodein by closestKodein()
    private var context: Context? = null
    private var count = 0
    val map = mutableMapOf<String, String>()
    var cryptoList: ArrayList<CryptoListData?> = ArrayList()
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        context = this
        setContentView(binding.root)
        initViewModel()
        setFilterSpinner()

    }

    fun initViewModel() {
        coinViewModel = ViewModelProvider(this, coinFactory).get(CryptoViewModel::class.java)
        coinViewModel?.getCryptocurrencies()
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog!!.setMessage("Loading....")
        progressDialog!!.show()
        observer()

    }

    private fun observer() {
        coinViewModel?.cryptoData?.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    var list = it.data?.data ?: ArrayList()
                    cryptoList.addAll(list)
                    for (i in cryptoList.indices) {
                        cryptoList[i]?.let { it1 -> coinViewModel?.getLogo(it1.id) }
                    }
                }
                Resource.Status.ERROR -> {
                    progressDialog!!.dismiss()

                    Toast.makeText(context,"ou've exceeded your API Key's HTTP request rate limit. Rate limits reset every minute",Toast.LENGTH_LONG).show()
                }
            }
        }
        coinViewModel?.coinLogo?.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    val param: String = it.url?.queryParameter("id").toString()

                    map[param] = it.data?.data?.get(param)?.logo.toString()
                    count += 1
                    if (count == cryptoList.size) {
                        progressDialog!!.dismiss()
                        binding.recyclerview.adapter = ListAdapter(
                            context!!,
                            cryptoList,
                            map, this@MainActivity
                        )
                        cryptoList[0]?.let { it1 -> setCardData(it1) }
                    }
                }

                Resource.Status.ERROR -> {
                    progressDialog!!.dismiss()

//                    Toast.makeText(context,"ou've exceeded your API Key's HTTP request rate limit. Rate limits reset every minute",Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    @SuppressLint("DefaultLocale", "SetTextI18n")
    fun setCardData(model: CryptoListData) {
        var color: Int = R.color.red
        var s = ""
        if (model.quote!!.USD!!.volume_change_24h > 0) {
            color = R.color.green
            s = "+"
        }
        binding.tvVolumeChange.text =
            s + java.lang.String.format("%.2f", model.quote!!.USD!!.volume_change_24h) + ""
        binding.ivGraph.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context!!, color))
        binding.tvVolumeChange.setTextColor(ContextCompat.getColor(context!!, color))
        binding.tvName.text = model.name
        binding.tvSymbol.text = model.symbol
        binding.tvTotal.text =
            "$ " + java.lang.String.format("%.2f", model.quote!!.USD!!.price) + " USD"
        Picasso.get().load(map[java.lang.String.valueOf(model.id)])
            .placeholder(R.drawable.baseline_image_24).error(R.drawable.baseline_image_24)
            .into(binding.ivLogo)
    }

    override fun onClick(model: CryptoListData) {
        setCardData(model)
    }

    private fun setFilterSpinner() {
        val filterList: MutableList<String> = java.util.ArrayList()
        filterList.add("Filter")
        filterList.add("Price")
        filterList.add("Change")
        binding.spinner.adapter = ArrayAdapter(this@MainActivity, R.layout.tv_spinner, filterList)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val models: ArrayList<CryptoListData?> = arrayListOf()
                models.addAll(cryptoList)
                if (filterList[i] == "Filter") {
                    binding.recyclerview.adapter =
                        ListAdapter(context!!, models, map, this@MainActivity)
                } else {
                    if (filterList[i] == "Price") {
                        models.sortWith(Comparator.comparingDouble<CryptoListData>(CryptoListData::getPrice))
                    } else {
                        models.sortWith(Comparator.comparingDouble<CryptoListData>(CryptoListData::getVolumeChange))
                    }
                binding.recyclerview.adapter =
                    ListAdapter(context!!, models, map, this@MainActivity)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

}