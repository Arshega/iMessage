package com.example.pc.imessage

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.example.pc.imessage.AdapterHolder.AdapterHolder
import com.example.pc.imessage.FirestoreDatabase.firestoreAction
import com.example.pc.imessage.FragmentHolder.Fragment_Contact
import com.example.pc.imessage.FragmentHolder.Fragment_Message
import kotlinx.android.synthetic.main.activity_home.*

class ContactList : AppCompatActivity() {
    var isFragmentOneLoaded = true
    var id = ""
    var fullname = ""
    val bundle = Bundle()
    var adapter: AdapterHolder? = null
    var arrayList: ArrayList<Contacts> = arrayListOf()
    val manager = supportFragmentManager
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.messaging -> {
           showFragmentMessage()
                return@OnNavigationItemSelectedListener true
            }
            R.id.contacs -> {
            showFragmentParent()

                return@OnNavigationItemSelectedListener true

            }

        }
        false
    }


    var arrofContact :ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val iin = intent
        var b: Bundle = iin.extras

        showFragmentParent()
        bundle.putString("myemail",b.getString("email"))
        bundle.putString("value", b.getString("id"))
        bundle.putString("name",b.getString("name"))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.removeShiftMode(navigation)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_contact, menu)
        val myActionMenuItem = menu.findItem(R.id.search)
        var searchView: SearchView = MenuItemCompat.getActionView(myActionMenuItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                var text = s.toLowerCase()
                var newList: ArrayList<Contacts> = arrayListOf()
                for (contacts: Contacts in arrayList){
                    var name: String = contacts.myname.toLowerCase()
                    if (name.contains(text))
                        newList.add(contacts)
                }
                adapter?.setfilter(newList)
                return true
            }
        })
        return true
    }

    internal object BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        fun removeShiftMode(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (i in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShiftingMode(false)
                    // set once again checked value, so view will be updated
                    item.setChecked(item.itemData.isChecked)
                }
            } catch (e: NoSuchFieldException) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field")
            } catch (e: IllegalAccessException) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode")
            }

        }
    }


    fun showFragmentParent() {

        val transaction = manager.beginTransaction()
        val fragment = Fragment_Contact()
        fragment.arguments = bundle
        transaction.replace(R.id.frag, fragment ,"tagname")
        transaction.addToBackStack("tagname")
        transaction.commit()


    }

    fun showFragmentMessage(){

        val transaction = manager.beginTransaction()
        val fragment = Fragment_Message()
        fragment.arguments = bundle
        transaction.replace(R.id.frag, fragment ,"tagname")
        transaction.addToBackStack("tagname")
        transaction.commit()

    }


}
