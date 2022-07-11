package com.ozontech.core_network_impl.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ozontech.core_network_impl.data.repositories.WorkerRepository

class ProductsWorker(
	context: Context,
	params: WorkerParameters,
	private val repository: WorkerRepository
) : CoroutineWorker(context, params) {

	override suspend fun doWork(): Result {
		return try {
			repository.getProducts()
			Result.success()
		} catch (e: Exception) {
			Result.failure()
		}
	}
}