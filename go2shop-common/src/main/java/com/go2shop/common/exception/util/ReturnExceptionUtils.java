package com.go2shop.common.exception.util;

import java.util.HashMap;
import java.util.Map;

import com.go2shop.common.exception.BusinessException;

public class ReturnExceptionUtils {

	private static final String ERROR_CODE = "errCode";
	private static final String ERROR_MSG = "errMsg";

	private ReturnExceptionUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static Map<String, Object> build(BusinessException ex) {
		Map<String, Object> responseData = new HashMap<>();
		responseData.put(ERROR_CODE, ex.getErrCode());
		responseData.put(ERROR_MSG, ex.getErrMsg());
		return responseData;
	}

}
