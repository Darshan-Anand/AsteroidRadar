package com.ba.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ba.asteroidradar.Asteroid
import com.ba.asteroidradar.R
import com.ba.asteroidradar.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(requireActivity().application)).get(
            MainViewModel::class.java
        )
    }

    val adapter = AsteroidAdapter(AsteroidAdapter.OnAsteroidClickListener {
        viewModel.displayAsteroidDetails(it)
    })
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        binding.asteroidRecycler.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asteroidsLists.observe(viewLifecycleOwner, Observer<List<Asteroid>> {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsFinished()
            }
        })

        viewModel.showNoNetworkSnackbar.observe(viewLifecycleOwner, Observer {
            if (true == it) {
                val snack = Snackbar.make(
                    binding.mainFragmentConstraintLayout,
                    "Network unavailable",
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction("Refresh") {
                    viewModel.checkNetworkAndRefresh()
                }
                snack.show()
            }
            viewModel.connectedToNetwork.observe(viewLifecycleOwner) {
                if (it == true)
                    Toast.makeText(requireContext(), " Network is back", Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_asteroids -> viewModel.getAsteroidsByWeek()
            R.id.show_today_asteroid -> viewModel.getAsteroidsByToday()
            R.id.show_saved_asteroids -> viewModel.getSavedAsteroids()
            else -> viewModel.getAsteroidsByWeek()
        }
        return true
    }

}
