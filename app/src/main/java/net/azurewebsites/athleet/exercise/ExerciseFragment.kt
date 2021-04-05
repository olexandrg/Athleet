package net.azurewebsites.athleet.exercise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import net.azurewebsites.athleet.databinding.FragmentExerciseBinding
import net.azurewebsites.athleet.models.Exercise


class ExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentExerciseBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val exercise: Exercise =  ExerciseFragmentArgs.fromBundle(requireArguments()).selectedExercise
        val viewModelFactory = ExerciseViewModelFactory(exercise, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(ExerciseViewModel::class.java)

       val vm = binding.viewModel


        var edit = false
        isEditing(edit, binding)

        binding.fabEditExercise.setOnClickListener {
            edit = true
            isEditing(edit, binding)
            binding.btnCancelEdit.visibility = View.VISIBLE
            binding.btnSaveEdit.visibility = View.VISIBLE
            binding.fabEditExercise.visibility = View.GONE

            markFields(binding)
        }

        binding.btnCancelEdit.setOnClickListener {
            unmarkAllFields(binding)

            vm?.reset()

            binding.btnCancelEdit.visibility = View.GONE
            binding.btnSaveEdit.visibility = View.GONE
            binding.fabEditExercise.visibility = View.VISIBLE

            edit = false
            isEditing(edit, binding)
        }

        binding.btnSaveEdit.setOnClickListener {
            unmarkAllFields(binding)

            vm?.updateExercise(
                binding.exerciseDetailName.text.toString(),
                binding.exerciseDetailDesc.text.toString(),
                binding.exerciseDetailUnit.text.toString(),
                binding.exerciseDetailReps.text.toString().toInt(),
                binding.exerciseDetailSets.text.toString().toInt(),
                binding.exerciseUnitCount.text.toString().toInt()
            )

            binding.btnCancelEdit.visibility = View.GONE
            binding.btnSaveEdit.visibility = View.GONE
            binding.fabEditExercise.visibility = View.VISIBLE

            edit = false
            isEditing(edit, binding)
        }

        binding.btnSaveEdit.isEnabled = false
        val editTexts = listOf(
            binding.exerciseDetailSets,
            binding.exerciseDetailName,
            binding.exerciseDetailDesc,
            binding.exerciseDetailReps,
            binding.exerciseDetailUnit,
            binding.exerciseUnitCount
        )

        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    var et1 = binding.exerciseDetailSets.text.toString().trim()
                    var et2 = binding.exerciseDetailName.text.toString().trim()
                    var et3 = binding.exerciseDetailDesc.text.toString().trim()
                    var et4 = binding.exerciseDetailReps.text.toString().trim()
                    var et5 = binding.exerciseDetailUnit.text.toString().trim()
                    var et6 = binding.exerciseUnitCount.text.toString().trim()

                    binding.btnSaveEdit.isEnabled = et1.isNotEmpty()
                            && et2.isNotEmpty()
                            && et3.isNotEmpty()
                            && et4.isNotEmpty()
                            && et5.isNotEmpty()
                            && et6.isNotEmpty()
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int
                ) {
                }

                override fun afterTextChanged(
                    s: Editable
                ) {
                    checkValues(editText)
                }
            })
        }

        return binding.root
    }

    private fun isEditing(edit: Boolean, binding: FragmentExerciseBinding) {
        if (!edit) {
            binding.exerciseDetailSets.isEnabled = false
            binding.exerciseDetailName.isEnabled = false
            binding.exerciseDetailDesc.isEnabled = false
            binding.exerciseDetailReps.isEnabled = false
            binding.exerciseDetailUnit.isEnabled = false
            binding.exerciseUnitCount.isEnabled = false
        }
        else {
            binding.exerciseDetailSets.isEnabled = true
            binding.exerciseDetailName.isEnabled = true
            binding.exerciseDetailDesc.isEnabled = true
            binding.exerciseDetailReps.isEnabled = true
            binding.exerciseDetailUnit.isEnabled = true
            binding.exerciseUnitCount.isEnabled = true
        }
    }


    private fun checkValues(view: TextInputEditText) {
        val textfield: TextInputEditText = view
        val containingLayout = textfield.parent as ViewGroup
        val textInputLayout = containingLayout.parent as TextInputLayout

        if (textfield.text.isNullOrEmpty()){
            textInputLayout.error = "Required field"
        } else textInputLayout.setError(null)
    }

    private fun markFields(binding: FragmentExerciseBinding){
        checkValues(binding.exerciseDetailSets)
        checkValues(binding.exerciseDetailName)
        checkValues(binding.exerciseDetailDesc)
        checkValues(binding.exerciseDetailReps)
        checkValues(binding.exerciseDetailUnit)
        checkValues(binding.exerciseUnitCount)
    }

    private fun unmarkAllFields(binding: FragmentExerciseBinding) {
        binding.tilDescription.setError(null)
        binding.tilUnitName.setError(null)
        binding.tilName.setError(null)
        binding.tilQuantity.setError(null)
        binding.tilReps.setError(null)
        binding.tilSets.setError(null)
    }

    private fun somehting() {

    }

}