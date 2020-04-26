package com.theapache64.topcorn.ui.activities.test

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.topcorn.R
import com.theapache64.topcorn.databinding.ActivityTestBinding
import com.theapache64.topcorn.databinding.ItemDataBinding
import com.theapache64.topcorn.databinding.ItemItemBinding
import com.theapache64.twinkill.utils.extensions.bindContentView
import com.theapache64.twinkill.utils.extensions.toast


class Data(
    val title: String,
    val list: List<String>
)

class ItemsAdapter(
    context: Context,
    private val items: List<String>,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.data = item
    }

    inner class ViewHolder(val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}

class DataAdapter(
    val context: Context,
    private val data: List<Data>,
    private val callback: (position: Int) -> Unit,
    private val nestedCallback: (parentPosition: Int, position: Int) -> Unit
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDataBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]
        holder.binding.bData.text = data.title
        val itemsAdapter = ItemsAdapter(context, data.list) {
            nestedCallback(position, it)
        }
        holder.binding.rvNestedData.adapter = itemsAdapter

    }

    inner class ViewHolder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityTestBinding>(R.layout.activity_test)

        val data = mutableListOf<Data>().apply {
            repeat(1) {
                val items = mutableListOf<String>().apply {
                    repeat(8) { x ->
                        add("Item $x of $it ")
                    }
                }
                add(Data("Category $it", items))
            }
        }

        binding.rvData.adapter = DataAdapter(
            this, data,
            {
                toast("Clicked on $it")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://testapp.com/$it"))
                startActivity(intent)
            },
            { x, y ->
                toast("Clicked on child $x and $y")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://testapp.com/$x/$y"))
                startActivity(intent)
            }
        )
    }
}