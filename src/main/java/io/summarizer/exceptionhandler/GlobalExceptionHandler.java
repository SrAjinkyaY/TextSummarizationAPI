package io.summarizer.exceptionhandler;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.genai.errors.ClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.append(errorMessage).append("; ");
		});
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("error", "Validation Error");
		errorResponse.put("message", errors.toString());
		errorResponse.put("code", 400);
		LOGGER.error("Exception Details: " + errorResponse);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Map<String, Object>> handleGoogleClientException(ClientException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		/*
		 * // 1. Extract the Status Code
		 * // The Google SDK usually exposes the status code directly via
		 * .getStatusCode()
		 * // or inside the message.
		 * // Note: Check if your specific version has .getStatusCode(), otherwise we
		 * parse
		 * // it.
		 * int statusCode = 500; // Default
		 * 
		 * // Attempt to get code directly if the SDK supports it (common in Google
		 * libs)
		 * // If the method ex.getStatusCode() doesn't exist in your version, use the
		 * // parsing logic below.
		 * if (ex.getMessage().contains("404")) {
		 * statusCode = 404;
		 * } else if (ex.getMessage().contains("400")) {
		 * statusCode = 400;
		 * } else if (ex.getMessage().contains("401")) {
		 * statusCode = 401;
		 * } else if (ex.getMessage().contains("429")) {
		 * statusCode = 429;
		 * }
		 * 
		 */
		int statusCode = extractHttpStatus(ex.getMessage());
		errorResponse.put("error", "AI Provider Error");
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("provider_code", statusCode);
		LOGGER.error("Exception Details: " + errorResponse);
		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
	}

	/**
	 * Catches RuntimeExceptions (like the one Spring AI throws) and checks if they
	 * are caused by Google GenAI errors.
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {

		// 1. Check if the "cause" is the Google ClientException
		if (ex.getCause() instanceof ClientException clientEx) {
			return handleGoogleClientException(clientEx);
		}
		LOGGER.error("Exception Details: Internal Server Error!");
		// 2. Handle generic RuntimeExceptions (Optional: or let them bubble up)
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "Internal Server Error", "message", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
		// Log the exception details (you can use a logging framework here)
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("error", "Internal Server Error");
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("code", 500);
		LOGGER.error("Exception Details: " + errorResponse);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private int extractHttpStatus(String message) {
		if (message.contains("404"))
			return HttpStatus.NOT_FOUND.value();
		if (message.contains("400"))
			return HttpStatus.BAD_REQUEST.value();
		if (message.contains("401"))
			return HttpStatus.UNAUTHORIZED.value();
		if (message.contains("429"))
			return HttpStatus.TOO_MANY_REQUESTS.value();
		return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

}
