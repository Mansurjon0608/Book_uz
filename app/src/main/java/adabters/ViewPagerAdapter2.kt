package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.BoshqaAdabiyotFragment
import com.example.myapplication.JahonAdabiyotiFragment
import com.example.myapplication.UzbekAdabiyotiFragment

class ViewPagerAdapter2(fragmentManager1 : FragmentManager?): FragmentPagerAdapter(fragmentManager1!!) {
    override fun getCount(): Int = 3

    override fun getItem(position:Int): Fragment {
        return when(position){
            0 -> UzbekAdabiyotiFragment()
            1 -> JahonAdabiyotiFragment()
            2 -> BoshqaAdabiyotFragment()
            else -> UzbekAdabiyotiFragment()
        }
    }
}