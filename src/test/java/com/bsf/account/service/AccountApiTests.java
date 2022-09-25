package com.bsf.account.service;

import com.bsf.account.service.common.model.AddAccountRequest;
import com.bsf.account.service.common.model.AddAccountResponse;
import com.bsf.account.service.common.model.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccountApiTests {
    @Autowired
    private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

    @Test
    void shouldCreateNewAccountWhenRequestIsValid() throws Exception {

		AddAccountRequest addAccountRequest = new AddAccountRequest("testTitle01", "test01@email.com", BigDecimal.TEN);

        ResultActions resultActions = mockMvc.perform(post("/account")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isCreated());

		MvcResult result = resultActions.andReturn();
		AddAccountResponse addAccountResponse = objectMapper.readValue(result.getResponse().getContentAsString(), AddAccountResponse.class);

		Assertions.assertNotNull(addAccountResponse.getId());
        Assertions.assertEquals(addAccountRequest.getEmail(), addAccountResponse.getEmail());
        Assertions.assertEquals(addAccountRequest.getTitle(), addAccountResponse.getTitle());
        Assertions.assertEquals(addAccountRequest.getBalance(), addAccountResponse.getBalance());
    }

    @Test
    void shouldReturnErrorIfTitleIsMissingFromAddAccountRequest() throws Exception {
        AddAccountRequest addAccountRequest = new AddAccountRequest(null, "test01@email.com", BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post("/account")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("title must not be empty"));
    }

    @Test
    void shouldReturnErrorIfEmailIsMissingFromAddAccountRequest() throws Exception {
        AddAccountRequest addAccountRequest = new AddAccountRequest("test title 01", null, BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post("/account")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("email must not be empty"));
    }

    @Test
    void shouldReturnErrorIfBalanceIsMissingFromAddAccountRequest() throws Exception {
        AddAccountRequest addAccountRequest = new AddAccountRequest("test title 01", "test01@email.com", null);

        MvcResult result = mockMvc.perform(post("/account")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("balance is required to create account"));
    }

    @Test
    void shouldReturnErrorIfBalanceIsNegativeInAddAccountRequest() throws Exception {
        AddAccountRequest addAccountRequest = new AddAccountRequest("test title 01", "test01@email.com", new BigDecimal("-12.45"));

        MvcResult result = mockMvc.perform(post("/account")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("balance can't not be less than 0"));
    }

    @Test
    void shouldReturnErrorIfBalanceIsInvalidInAddAccountRequest() throws Exception {
        AddAccountRequest addAccountRequest = new AddAccountRequest("test title 01", "test01@email.com", new BigDecimal("122.495"));

        MvcResult result = mockMvc.perform(post("/account")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("amount should be up to 15 digits and 2 decimal places"));
    }
}
