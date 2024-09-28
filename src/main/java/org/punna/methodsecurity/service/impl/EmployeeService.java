package org.punna.methodsecurity.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @PreAuthorize("hasAnyRole({'ADMIN', 'HOD', 'BOD'})")
    public boolean isNominatedForPromotion(String username) {
        return true;
    }

    // in case of custom user model, you can access all your model properties using `principle.`
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and principal.username.equals(#username))")
    public String getDetailedUserProfile(String username) {
        return "Detailed user profile of: " + username;
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'BOD'}) or (isAuthenticated() and T(java.lang.Integer).parseInt(principal.JobLevel) >= 8)")
    public boolean isEligibleForFreeStay(String username) {
        return true;
    }

}
