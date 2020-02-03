package com.yaroslav.testzimad.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.yaroslav.testzimad.Constant
import com.yaroslav.testzimad.R
import com.yaroslav.testzimad.ui.fragment.AnimalFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener{

    private lateinit var animalSpecies: Array<String>

    private lateinit var fragmentCat: AnimalFragment
    private lateinit var fragmentDog: AnimalFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animalSpecies = resources.getStringArray(R.array.animal_species)

        if (savedInstanceState == null){
            fragmentCat = AnimalFragment().newInstance(animalSpecies[0])
            fragmentDog = AnimalFragment().newInstance(animalSpecies[1])
        } else {
            fragmentCat = if (supportFragmentManager.getFragment(savedInstanceState, Constant.CAT_SPECIES) != null)
                supportFragmentManager.getFragment(savedInstanceState, Constant.CAT_SPECIES) as AnimalFragment
            else
                AnimalFragment().newInstance(animalSpecies[0])

            fragmentDog = if (supportFragmentManager.getFragment(savedInstanceState, Constant.DOG_SPECIES) != null)
                supportFragmentManager.getFragment(savedInstanceState, Constant.DOG_SPECIES) as AnimalFragment
            else
                AnimalFragment().newInstance(animalSpecies[1])
        }
        tabLayout.addOnTabSelectedListener(this)

        addContentTab()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(Constant.POSITION_TAB, tabLayout.selectedTabPosition)
        if (fragmentCat.isAdded)
            supportFragmentManager.putFragment(outState, Constant.CAT_SPECIES, fragmentCat)
        if (fragmentDog.isAdded)
            supportFragmentManager.putFragment(outState, Constant.DOG_SPECIES, fragmentDog)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(Constant.POSITION_TAB)){
            val tab = tabLayout.getTabAt(savedInstanceState.getInt(Constant.POSITION_TAB))
            tab?.select()
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        when(p0!!.position){
            0 -> commitFragment(fragmentCat)
            1 -> commitFragment(fragmentDog)
        }
    }

    private fun addContentTab(){
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_cat))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_dog))
    }

    private fun commitFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        if (fragment.isAdded){
            if (fragment == fragmentDog) {
                fragmentTransaction.show(fragment)
                fragmentTransaction.hide(fragmentCat)
            } else if (fragment == fragmentCat) {
                fragmentTransaction.show(fragment)
                fragmentTransaction.hide(fragmentDog)
            }
        } else {
            fragmentTransaction.add(R.id.frame_container, fragment, Constant.KEY_FRAGMENT)
        }

        fragmentTransaction.commit()

    }
}