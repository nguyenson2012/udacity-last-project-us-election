package com.udacity.election.election
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.udacity.election.BaseFragment
import com.udacity.election.R
import com.udacity.election.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : BaseFragment() {
    override val viewModel: VoterInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val arguments = VoterInfoFragmentArgs.fromBundle(requireArguments())
        viewModel.refresh(arguments.election)

        binding.tvStateLocations.setOnClickListener {
            val urlStr = viewModel.voterInfo.value?.votingLocationUrl
            urlStr?.run {
                startActivityUrl(this)
            }
        }
        binding.tvStateBallot.setOnClickListener {
            val urlStr = viewModel.voterInfo.value?.ballotInformationUrl
            urlStr?.run {
                startActivityUrl(this)
            }
        }
        return binding.root
    }

    private fun startActivityUrl(urlStr: String) {
        val uri: Uri = Uri.parse(urlStr)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        try {
            intent.setPackage("com.android.chrome")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            try {
                intent.setPackage(null)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                if (view != null) {
                    val snack = Snackbar.make(
                        requireView(),
                        getString(R.string.no_any_browser_msg),
                        Snackbar.LENGTH_LONG)
                    snack.show()
                }
            }
        }
    }
}