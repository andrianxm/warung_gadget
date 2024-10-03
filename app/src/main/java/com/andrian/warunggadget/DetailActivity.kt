package com.andrian.warunggadget

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val KEY_GADGET = "key_gadget"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail"

        val dataGadget = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_GADGET, Gadget::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra(KEY_GADGET)
        }

        val tvDetailName: TextView = findViewById(R.id.tv_item_name)
        val tvDetailPrice: TextView = findViewById(R.id.tv_item_price)
        val ivDetailPhoto: ImageView = findViewById(R.id.img_item_photo)
        val tvDataSpecification: TextView = findViewById(R.id.data_specification)
        val tvDetailSpecification: TextView = findViewById(R.id.detail_specification)


        tvDetailName.text = dataGadget?.name
        tvDetailPrice.text = dataGadget?.price
        val imageUrl = dataGadget?.photo
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(ivDetailPhoto)
        } else {
            ivDetailPhoto.setImageResource(R.drawable.not_found_img)
        }
        tvDataSpecification.text = dataGadget?.dataSpecification
        tvDetailSpecification.text = dataGadget?.detailSpecification

        val position = intent.getIntExtra("position", -1)
        if (position != -1) {
            val links = resources.getStringArray(R.array.link_share)
            val linkToShare = links[position]
            val shareButton: Button = findViewById(R.id.action_share)
            shareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, linkToShare)
                }
                startActivity(Intent.createChooser(shareIntent, "Bagikan link melalui"))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_back -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        val buyButton: Button = findViewById(R.id.btn_buy)
        buyButton.setOnClickListener(this)
        when (v) {
            buyButton -> {
                buyButton.setOnClickListener {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Apakah anda yakin?")
                    builder.setMessage("Pesanan Anda akan dikirimkan langsunf")
                        .setPositiveButton("Ya") { dialog, _ ->
                            dialog.dismiss()
                        }.setNegativeButton("Tidak") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
                }
            }
        }

    }
}
