package com.devryan.course;

import com.devryan.course.dto.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
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
	void userDTOValidation_ShouldFailWhenFieldsAreBlankOrInvalid() {
		UserDTO dto = new UserDTO();
		dto.setName("");
		dto.setEmail("invalid-email");
		dto.setPhone("");
		dto.setPassword("123");

		Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty(), "Violations should not be empty for invalid UserDTO");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
	}

	@Test
	void userDTOValidation_ShouldPassWhenFieldsAreValid() {
		UserDTO dto = new UserDTO();
		dto.setName("John Doe");
		dto.setEmail("john@example.com");
		dto.setPhone("999999999");
		dto.setPassword("123456");

		Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid UserDTO");
	}

	@Test
	void productDTOValidation_ShouldFailWhenNameIsBlankOrPriceInvalid() {
		ProductDTO dto = new ProductDTO();
		dto.setName("");
		dto.setPrice(-5.0);

		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty(), "Violations should not be empty for invalid ProductDTO");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
	}

	@Test
	void productDTOValidation_ShouldPassWhenFieldsAreValid() {
		ProductDTO dto = new ProductDTO();
		dto.setName("Notebook");
		dto.setDescription("High performance");
		dto.setPrice(3500.0);

		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Violations should be empty for valid ProductDTO");
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
	void orderDTOValidation_ShouldFailWhenOrderStatusIsNull() {
		OrderDTO dto = new OrderDTO();
		dto.setOrderStatus(null);

		Set<ConstraintViolation<OrderDTO>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty(), "Violations should not be empty for null orderStatus");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("orderStatus")));
	}

	@Test
	void orderItemDTOValidation_ShouldFailWhenQuantityOrPriceInvalid() {
		OrderItemDTO dto = new OrderItemDTO();
		dto.setName("");
		dto.setQuantity(-1);
		dto.setPrice(0.0);

		Set<ConstraintViolation<OrderItemDTO>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty(), "Violations should not be empty for invalid OrderItemDTO");
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
	}
}
