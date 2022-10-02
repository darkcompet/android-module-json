/*
 * Copyright (c) 2017-2021 DarkCompet. All rights reserved.
 */
package tool.compet.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import tool.compet.core.DkDateTimeConst
import tool.compet.core.DkLogs

/**
 * Json converter.
 */
class DkJsons private constructor() {
	companion object {
		private val gson: Gson

		init {
			gson = GsonBuilder()
				.setDateFormat(DkDateTimeConst.DATETIME)
				.excludeFieldsWithoutExposeAnnotation()
				.setPrettyPrinting()
				.create()
		}

		/**
		 * Safe convert JSON to POJO of given type.
		 * For back-compatibility, this use try/catch for safe converting.
		 * So when caller got null-result, lets consider 2 cases: empty-data or invalid-json.
		 *
		 * @return POJO object if succeed. Otherwise returns null.
		 */
		fun <T> toObj(json: String?, classOfT: Class<T>): T? {
			try {
				return gson.fromJson(json, classOfT)
			}
			catch (e: Exception) {
				DkLogs.error(this, e)
			}
			return null
		}

		/**
		 * Converts Expose-annotated POJO to JSON.
		 */
		fun toJson(obj: Any?): String {
			return gson.toJson(obj)
		}
	}
}
