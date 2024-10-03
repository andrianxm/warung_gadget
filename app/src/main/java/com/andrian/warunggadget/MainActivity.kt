package com.andrian.warunggadget

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrian.warunggadget.ListGadgetAdapter
import com.andrian.warunggadget.databinding.ActivityMainBinding
import com.andrian.warunggadget.ListGadgetAdapter as ListGadgetAdapter1

class MainActivity : AppCompatActivity() {
    private lateinit var rvGadget: RecyclerView
    private val list = ArrayList<Gadget>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        splashScreen = installSplashScreen()
        setContentView(binding.root)
        splashScreen.setKeepOnScreenCondition { false }

        rvGadget = binding.rvGadget
        rvGadget.setHasFixedSize(true)
        list.addAll(getListGadget())
        showRecyclerList()
    }

    private fun getListGadget(): ArrayList<Gadget> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getStringArray(R.array.data_harga)
        val detailSpecification = resources.getStringArray(R.array.detail_spesification)
        val dataSpecification = resources.getStringArray(R.array.data_specification)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val linkShare = resources.getStringArray(R.array.link_share)
        val listGadget = ArrayList<Gadget>()
        for (i in dataName.indices) {
            val gadget = Gadget(
                dataName[i],
                dataPrice[i],
                dataPhoto[i],
                dataSpecification[i],
                detailSpecification[i],
                linkShare[i]
            )
            listGadget.add(gadget)
        }
        return listGadget
    }

//    mengatur data ditampilkan
    private fun showRecyclerList() {
        rvGadget.layoutManager = LinearLayoutManager(this)
        val listGadgetAdapter = ListGadgetAdapter1(list)
        rvGadget.adapter = listGadgetAdapter

        listGadgetAdapter.setOnItemClickCallback(object : ListGadgetAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Gadget) {
                showSelectedGadget(data)
            }
        })
    }

    private fun showSelectedGadget(gadget: Gadget) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("key_gadget", gadget)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                rvGadget.layoutManager = LinearLayoutManager(this)
            }

            R.id.action_grid -> {
                rvGadget.layoutManager = GridLayoutManager(this, 2)
                val listGadgetAdapter = ListGadgetAdapter(list)
                rvGadget.adapter = listGadgetAdapter
            }

            R.id.about_page -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}