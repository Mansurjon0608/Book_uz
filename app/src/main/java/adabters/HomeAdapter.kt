package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.AdiblarFragment
import com.example.myapplication.SaqlanganlarFragment
import com.example.myapplication.SozlamalarFragment


class HomeAdapter(fragmentActivity: FragmentManager, lifecycle:Lifecycle): FragmentStateAdapter(fragmentActivity, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

       // val adiblarListFragment = AdiblarFragment()
        return   when(position){

            0->{
                AdiblarFragment()
            }
            1->{
                SaqlanganlarFragment()
            }
            2->{
                SozlamalarFragment()
            }

            else->{
              AdiblarFragment()
            }

        }
    }
    }


