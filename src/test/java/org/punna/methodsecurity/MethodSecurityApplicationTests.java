package org.punna.methodsecurity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.punna.methodsecurity.repository.UserRepository;
import org.punna.methodsecurity.service.CustomUserDetailsService;
import org.punna.methodsecurity.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MethodSecurityApplicationTests.Config.class})
class MethodSecurityApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @MockBean
    UserRepository userRepository;


    // only allow HOD, BOD, ADMIN to know whether an Employee got nominated for promotion or not.
    @Test
    @WithMockUser(username = "ramesh", roles = {"ADMIN"})
    void isNominatedForPromotion() {
        boolean nominatedForPromotion = employeeService.isNominatedForPromotion("ramesh");
        assertTrue(nominatedForPromotion);
    }

    // throw error for roles other than HOD, BOD, ADMIN.
    @Test
    @WithMockUser(roles = {"DUMMY"})
    void isNominatedForPromotion_error() {
        Assertions.assertThrowsExactly(AuthorizationDeniedException.class,
                () -> employeeService.isNominatedForPromotion("ramesh"));
    }

    // throw error for Anonymous users.
    @Test
    @WithAnonymousUser
    void isNominatedForPromotion_error_anonymous() {
        Assertions.assertThrowsExactly(AuthorizationDeniedException.class,
                () -> employeeService.isNominatedForPromotion("ramesh"));
    }

    // only the user or ADMIN can see the detailed profile of a user
    @Test
    @WithMockUser(username = "ramesh")
    void detailedUserProfileWithSameUsername() {
        String userprofile = employeeService.getDetailedUserProfile("ramesh");
        assertEquals(userprofile, "Detailed user profile of: ramesh");
    }

    // only the user or ADMIN can see the detailed profile of a user
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void detailedUserProfileWithAdmin() {
        String userprofile = employeeService.getDetailedUserProfile("ramesh");
        assertEquals(userprofile, "Detailed user profile of: ramesh");
    }

    // throw error with anonymous or other users.
    @Test
    @WithMockUser(username = "Dummy")
    void detailedUserProfileWithOtherUsername() {
        Assertions.assertThrowsExactly(AuthorizationDeniedException.class,
                () -> employeeService.getDetailedUserProfile("ramesh"));
    }

    // throw error with anonymous or other users.
    @Test
    @WithAnonymousUser
    void detailedUserProfileWithAnonymousUser() {
        Assertions.assertThrowsExactly(AuthorizationDeniedException.class,
                () -> employeeService.getDetailedUserProfile("ramesh"));
    }

    // using custom user model for testing method security annotations, in this case user-mahesh has Role HOD which gets him into the function
    @Test
    @WithUserDetails("mahesh")
    void isNominatedForPromotion_withCustomUser() {
        boolean nominatedForPromotion = employeeService.isNominatedForPromotion("mahesh");
        assertTrue(nominatedForPromotion);
    }


    // only roles admin, BOD and JobLevel 8 and above employees are eligible
    @Test
    @WithUserDetails("mahesh")
    void isEligibleForFreeStay_withCustomUser() {
        assertThrowsExactly(AuthorizationDeniedException.class, () -> employeeService.isEligibleForFreeStay("mahesh"));

    }

    @Test
    @WithUserDetails("pawan")
    void isEligibleForFreeStay_withCustomUser_eligible() {
        boolean isEligible = employeeService.isEligibleForFreeStay("pawan");
        assertTrue(isEligible);
    }




    @Configuration
    @EnableMethodSecurity
    @ComponentScan(basePackageClasses = {EmployeeService.class, CustomUserDetailsService.class})
    public static class Config {

    }
}
