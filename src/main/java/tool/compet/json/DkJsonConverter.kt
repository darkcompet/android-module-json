/*
 * Copyright (c) 2017-2021 DarkCompet. All rights reserved.
 */
package tool.compet.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import tool.compet.core.DkDateTimeConst
import tool.compet.core.DkLogs

/**
 * Dependency: com.google.code.gson:gson:2.8.6
 */
class DkJsonConverter private constructor() {
	private val gson: Gson

	/**
	 * Safe convert JSON to POJO of given type.
	 * For back-compatibility, this use try/catch for safe converting.
	 * So when caller got null-result, lets consider 2 cases: empty-data or invalid-json.
	 *
	 * @return POJO object if succeed. Otherwise returns null.
	 */
	fun <T> json2obj(json: String?, classOfT: Class<T>): T? {
		try {
			return gson.fromJson(json, classOfT)
		}
		catch (e: Exception) {
			DkLogs.error(this, e)
		}
		return null
	}

	/**
	 * Converts POJO to JSON.
	 */
	fun obj2json(obj: Any?): String {
		return gson.toJson(obj)
	}

	companion object {
		private var INS: DkJsonConverter? = null
		val ins: DkJsonConverter
			get() = if (INS != null) INS!! else DkJsonConverter().also { INS = it }
	}

	init {
		gson = GsonBuilder()
			.setDateFormat(DkDateTimeConst.DATETIME)
			.excludeFieldsWithoutExposeAnnotation()
			.setPrettyPrinting()
			.create()
	}
}
