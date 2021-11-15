package xyz.vkislitsin.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.vkislitsin.backend.controllers.exceptions.BadArgumentsException;
import xyz.vkislitsin.backend.controllers.request.IouAddRequest;
import xyz.vkislitsin.backend.controllers.request.UserAddRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebTests {

    @Autowired
    private MockMvc mockMvc;

    // checking if the main page is not able to request via get
    @Test
    public void homePageShouldNotBeOkViaGet() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andDo(print())
                .andExpect(status()
                        .isForbidden());
    }

    // checking if the main page is not able to request via post
    @Test
    public void homePageShouldNotBeOkViaPost() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/"))
                .andDo(print())
                .andExpect(status()
                        .isForbidden());
    }

    // checking if the right Object returns after saving User
    @Test
    public void getUserObjectAfterSavingUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/add")
                .content(asJsonString(new UserAddRequest("Stepan Razin")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Stepan Razin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(0));
    }

    // checking if the right Object returns after saving User
    @Test
    public void getErrorWhenAddingLoanToNonExistingUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/iou")
                .content(asJsonString(new IouAddRequest("Vladislav Pechkin", "Kot Matroskin", new BigDecimal(5.5))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> assertEquals("No such lender or borrower exists in DB!", result.getResolvedException().getMessage()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
