package com.bsf.account.service;

import com.bsf.account.service.common.model.*;
import com.bsf.account.service.entities.AccountEntity;
import com.bsf.account.service.repositories.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FundsTransferApiTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;
    private AddAccountResponse account01;
    private AddAccountResponse account02;

    @BeforeAll
    void createAccounts() throws Exception {
        AddAccountRequest testAccount01 = new AddAccountRequest("test account 01", "ta01@email.com", new BigDecimal("97.54"));
        AddAccountRequest testAccount02 = new AddAccountRequest("test account 02", "ta02@email.com", new BigDecimal("2.46"));
        account01 = createAccount(testAccount01);
        account02 = createAccount(testAccount02);
    }

    @RepeatedTest(9)
    @Execution(ExecutionMode.CONCURRENT)
    void shouldTransferFundAndCreateTransactionsInDb() throws Exception {

        BigDecimal account01InitialBalance = getAccountBalance(account01.getId());
        BigDecimal account02InitialBalance = getAccountBalance(account02.getId());

        TransferFundsRequest transferFundsRequest = new TransferFundsRequest("test", BigDecimal.TEN, account02.getId());

        MvcResult mvcResult = mockMvc.perform(post("/account/" + account01.getId() + "/transferFunds")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFundsRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        TransferFundsResponse transferFundsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransferFundsResponse.class);

        BigDecimal account01FinalBalance = getAccountBalance(account01.getId());
        BigDecimal account02FinalBalance = getAccountBalance(account02.getId());

        Assertions.assertEquals(account01InitialBalance.subtract(BigDecimal.TEN), account01FinalBalance);
        Assertions.assertEquals(account02InitialBalance.add(BigDecimal.TEN), account02FinalBalance);

        Assertions.assertEquals(account01InitialBalance, transferFundsResponse.getStartingBalance());
        Assertions.assertEquals(account01FinalBalance, transferFundsResponse.getEndingBalance());
        Assertions.assertEquals(BigDecimal.TEN, transferFundsResponse.getAmount());
        Assertions.assertNotNull(transferFundsResponse.getReference());
    }

    @Test
    void shouldReturnErrorIfAmountIsInvalidInTransferFundsRequest() throws Exception {
        TransferFundsRequest transferFundsRequest = new TransferFundsRequest("test", new BigDecimal("123.456"), account02.getId());

        MvcResult result = mockMvc.perform(post("/account/" + account01.getId() + "/transferFunds")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFundsRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("amount should be up to 15 digits and 2 decimal places"));
    }

    @Test
    void shouldReturnErrorIfAmountIsNegativeInTransferFundsRequest() throws Exception {
        TransferFundsRequest transferFundsRequest = new TransferFundsRequest("test", new BigDecimal("-12.45"), account02.getId());

        MvcResult result = mockMvc.perform(post("/account/" + account01.getId() + "/transferFunds")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFundsRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        Assertions.assertNotNull(apiError);
        Assertions.assertTrue(apiError.getErrors().contains("amount can't not be less than 1"));
    }

    private AddAccountResponse createAccount(AddAccountRequest addAccountRequest) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/account")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountRequest)))
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), AddAccountResponse.class);
    }

    private BigDecimal getAccountBalance(Long accountId) {
        return accountRepository.findById(accountId).map(AccountEntity::getBalance).orElseThrow();
    }
}
