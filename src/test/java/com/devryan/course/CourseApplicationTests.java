package com.devryan.course;

import com.devryan.course.dto.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class CourseApplicationTests {

	private static Validator validator;

	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void userRequestValidation_ShouldFailWhenFieldsAreBlankOrInvalid() {
		UserRequest request = new UserRequest("", "invalid-email", "", "123");

		Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
		assertFalse(violations.isEmpty(), "Violations should not be empty for invalid UserRequest");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
	}

	@Test
	void userRequestValidation_ShouldPassWhenFieldsAreValid() {
		UserRequest request = new UserRequest("John Doe", "john@example.com", "999999999", "123456");

		Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid UserRequest");
	}

	@Test
	void productRequestValidation_ShouldFailWhenNameIsBlankOrPriceInvalid() {
		ProductRequest request = new ProductRequest("", "Description", -5.0, "img.jpg");

		Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
		assertFalse(violations.isEmpty(), "Violations should not be empty for invalid ProductRequest");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
	}

	@Test
	void productRequestValidation_ShouldPassWhenFieldsAreValid() {
		ProductRequest request = new ProductRequest("Notebook", "High performance", 3500.0, "img.jpg");

		Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid ProductRequest");
	}

	@Test
	void categoryDTOValidation_ShouldFailWhenNameIsBlank() {
		CategoryDTO dto = new CategoryDTO(null, "");

		Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty(), "Violations should not be empty for blank category name");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
	}

	@Test
	void categoryDTOValidation_ShouldPassWhenNameIsValid() {
		CategoryDTO dto = new CategoryDTO(null, "Electronics");

		Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid CategoryDTO");
	}

	@Test
	void orderRequestValidation_ShouldFailWhenStatusOrClientIsNull() {
		OrderRequest request = new OrderRequest(Instant.now(), null, null);

		Set<ConstraintViolation<OrderRequest>> violations = validator.validate(request);
		assertFalse(violations.isEmpty(), "Violations should not be empty for null orderStatus and client");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("orderStatus")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("client")));
	}

	@Test
	void orderRequestValidation_ShouldPassWhenFieldsAreValid() {
		UserResponse client = new UserResponse(1L, "Maria Brown", "maria@gmail.com", "988888888");
		OrderRequest request = new OrderRequest(Instant.now(), 1, client);

		Set<ConstraintViolation<OrderRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid OrderRequest");
	}

	@Test
	void orderItemRequestValidation_ShouldFailWhenQuantityOrPriceInvalid() {
		OrderItemRequest request = new OrderItemRequest("", -1, 0.0);

		Set<ConstraintViolation<OrderItemRequest>> violations = validator.validate(request);
		assertFalse(violations.isEmpty(), "Violations should not be empty for invalid OrderItemRequest");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
	}

	@Test
	void orderItemRequestValidation_ShouldPassWhenFieldsAreValid() {
		OrderItemRequest request = new OrderItemRequest("Teclado Mecânico", 2, 250.0);

		Set<ConstraintViolation<OrderItemRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid OrderItemRequest");
	}
}
