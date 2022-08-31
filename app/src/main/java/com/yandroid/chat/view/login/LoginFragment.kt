package com.yandroid.chat.view.login

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
import com.yandroid.chat.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        binding.loginButton.setOnClickListener {
            loginViewModel.login(
                username = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
        }

        loginViewModel.user.observe(viewLifecycleOwner) {
            if (it != null && loginViewModel.errorMessage.value.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else if (it == null && !loginViewModel.errorMessage.value.isNullOrEmpty()) {
                loginViewModel.errorMessage.value = "some thing went wrong"
            }
        }

        loginViewModel.isProgress.observe(viewLifecycleOwner) {
            if (it) {
                binding.loginText.visibility = View.INVISIBLE
                binding.loginProgress.visibility = View.VISIBLE
            } else {
                binding.loginText.visibility = View.VISIBLE
                binding.loginProgress.visibility = View.INVISIBLE
            }

            binding.createAccountButton.isClickable = !it
        }

        loginViewModel.errorMessage.observe(viewLifecycleOwner) {

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