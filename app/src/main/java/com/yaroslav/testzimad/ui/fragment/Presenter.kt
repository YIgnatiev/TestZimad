package com.yaroslav.testzimad.ui.fragment

import android.annotation.SuppressLint
import com.yaroslav.testzimad.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Presenter(var view: AnimalView) {

    private val apiInterface = ApiInterface.create()

    @SuppressLint("CheckResult")
    fun getListAnimal(species: String){
        apiInterface.getAnimalList(species)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccessful && it.body() != null){
                    view.setListAnimal(it.body()!!.data)
                }
            }
    }

}