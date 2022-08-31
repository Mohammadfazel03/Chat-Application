package com.yandroid.chat.view.signup

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandroid.chat.R
import com.yandroid.chat.databinding.FragmentSignupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val signupViewModel: SignupViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.loginButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.createAccountButton.setOnClickListener {
            signupViewModel.signup(
                binding.usernameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
            )
        }

        signupViewModel.user.observe(viewLifecycleOwner) {
            if (it != null && signupViewModel.errorMessage.value.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
            } else if (it == null && !signupViewModel.errorMessage.value.isNullOrEmpty()) {
                signupViewModel.errorMessage.value = "some thing went wrong"
            }
        }

        signupViewModel.isProgress.observe(viewLifecycleOwner) {
            if (it) {
                binding.createAccountText.visibility = View.INVISIBLE
                binding.createAccountProgress.visibility = View.VISIBLE
            } else {
                binding.createAccountText.visibility = View.VISIBLE
                binding.createAccountProgress.visibility = View.INVISIBLE
            }

            binding.createAccountButton.isClickable = !it
        }

        signupViewModel.errorMessage.observe(viewLifecycleOwner) {

            if (!it.isNullOrEmpty()) {
                val dialog = MaterialAlertDialogBuilder(
                    requireContext(),
                    com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                )
                    .setTitle("Register Fail")
                    .setMessage(it)
                    .setNeutralButton("Ok") { dialog, _ ->
                        dialog.cancel()
                    }
                    .setCancelable(true)
                    .create()

                dialog.show()

                val button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                button.setTextColor(Color.parseColor("#1976D2"))
                val buttonLayoutParams: LinearLayout.LayoutParams =
                    button.layoutParams as (LinearLayout.LayoutParams)
                buttonLayoutParams.gravity = Gravity.CENTER
                buttonLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                button.layoutParams = buttonLayoutParams

            }
        }
    }
}