package com.yaroslav.testzimad.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yaroslav.testzimad.Constant
import com.yaroslav.testzimad.R
import com.yaroslav.testzimad.adapter.AnimalListAdapter
import com.yaroslav.testzimad.callback.CallbackOpenDetail
import com.yaroslav.testzimad.response.Data
import com.yaroslav.testzimad.ui.DetailActivity
import java.util.concurrent.CopyOnWriteArrayList

class AnimalFragment() : Fragment(), AnimalView, CallbackOpenDetail {

    private val presenter = Presenter(this)
    private val listDataAnimal: MutableList<Data> = ArrayList()

    private lateinit var listAnimal: RecyclerView

    private lateinit var adapter: AnimalListAdapter

    private var species = Constant.CAT_SPECIES

    override fun setListAnimal(list: List<Data>) {
        listDataAnimal.clear()

        listDataAnimal.addAll(list)

        adapter.notifyDataSetChanged()
    }

    override fun openDetail(animal: Data, position: Int) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(Constant.DETAIL_DATA, animal)
        intent.putExtra(Constant.POSITION_ANIMAL, position)
        startActivity(intent)
    }

    fun newInstance(species: String): AnimalFragment{
        val args = Bundle()
        args.putString(Constant.KEY_SPECIES, species)
        val fragment = AnimalFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        listAnimal = root.findViewById(R.id.list_animal)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            species = it.getString(Constant.KEY_SPECIES) ?: Constant.CAT_SPECIES
        }

        initRvList()

        if (savedInstanceState != null){
            val parcelable = savedInstanceState.getParcelable<Parcelable>(Constant.STATE_LIST)
            val list = savedInstanceState.getParcelableArrayList<Parcelable>(Constant.STATE_LIST_DATA)
            Log.d("MyLog", "$list")
            listAnimal.layoutManager!!.onRestoreInstanceState(parcelable)
            listDataAnimal.addAll(list as List<Data>)
            adapter.notifyDataSetChanged()
        } else {

            presenter.getListAnimal(species)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Constant.STATE_LIST, listAnimal.layoutManager!!.onSaveInstanceState())
        outState.putParcelableArrayList(Constant.STATE_LIST_DATA, ArrayList(adapter.list))
        super.onSaveInstanceState(outState)
    }



    private fun initRvList(){
        listAnimal.layoutManager = LinearLayoutManager(context)
        adapter = AnimalListAdapter(listDataAnimal, this)
        listAnimal.adapter = adapter
    }
}