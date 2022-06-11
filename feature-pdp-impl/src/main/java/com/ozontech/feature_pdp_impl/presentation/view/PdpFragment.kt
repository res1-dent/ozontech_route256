package com.ozontech.feature_pdp_impl.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.ozontech.core_utils.R
import com.ozontech.core_utils.getComp
import com.ozontech.core_utils.inflate
import com.ozontech.core_utils.releaseComp
import com.ozontech.feature_pdp_impl.databinding.FragmentPdpBinding
import com.ozontech.feature_pdp_impl.di.FeaturePdpComponent
import com.ozontech.feature_pdp_impl.presentation.view_model.PdpViewModel
import com.ozontech.feature_pdp_impl.presentation.view_objects.ProductVO

class PdpFragment : Fragment() {

	private val binding by viewBinding(FragmentPdpBinding::bind)

	private val viewModel: PdpViewModel by viewModels {
		getComp(FeaturePdpComponent::class).fabric()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		init()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeViewModelState()
	}

	private fun init() {
		requireArguments().getString(KEY_GUID)?.let {
			viewModel.getProductByGuid(it)
		}
	}

	private fun observeViewModelState() {
		viewModel.productLiveData.observe(viewLifecycleOwner, ::updateProduct)
	}

	private fun updateProduct(product: ProductVO) {
		with(binding) {
			Glide.with(requireView()).load(product.images.first()).into(productImageView)
			nameTextView.text = product.name
			priceTextView.text = getString(R.string.price, product.price)
			ratingRatingBar.rating = product.rating
			descriptionTextView.text = product.description
			availableCountTextView.text =
				getString(R.string.available_count, product.availableCount)
			countTextView.text =
				getString(R.string.count_string, product.count)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return container?.inflate(FragmentPdpBinding::inflate)?.root
	}

	override fun onPause() {
		super.onPause()
		if (isRemoving){
			releaseComp(FeaturePdpComponent::class)
		}
	}

	companion object {
		private const val KEY_GUID = "guid"
		fun newInstance(guid: String) =
			PdpFragment().apply {
				arguments = Bundle().apply {
					putString(KEY_GUID, guid)
				}
			}
	}
}


