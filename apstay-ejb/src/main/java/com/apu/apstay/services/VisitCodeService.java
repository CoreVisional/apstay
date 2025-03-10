package com.apu.apstay.services;

import com.apu.apstay.facades.VisitCodeFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitCodeService {
    
    @EJB
    private VisitCodeFacade visitCodeFacade;
    
    public String getCodeByVisitRequestId(Long visitRequestId) {
        var visitCode = visitCodeFacade.findByVisitRequestId(visitRequestId);
        if (visitCode == null) {
            return null;
        }
        return visitCode.getCode();
    }
    
    public Long getByCode(String code) {
        return verifyCode(code).visitRequestId;
    }
    
    public boolean isCodeValid(String code) {
        return verifyCode(code).success;
    }
    
    public String getVerificationMessage(String code) {
        return verifyCode(code).message;
    }
    
    public boolean deactivateCode(Long visitRequestId) {
        var visitCode = visitCodeFacade.findByVisitRequestId(visitRequestId);
        if (visitCode == null) {
            return false;
        }
        
        visitCode.setActive(false);
        visitCodeFacade.edit(visitCode);
        return true;
    }
    
    private VerificationResult verifyCode(String code) {
        
        if (code == null || code.length() != 6 || !code.matches("\\d{6}")) {
            return new VerificationResult(false, null, "Invalid verification code!");
        }

        var visitCode = visitCodeFacade.findByCode(code);

        if (visitCode == null) {
            return new VerificationResult(false, null, "Invalid verification code!");
        }

        if (!visitCode.isActive()) {
            var visitRequestId = visitCodeFacade.getVisitRequestId(code);
            return new VerificationResult(false, visitRequestId, "This code is no longer valid!");
        }

        var visitRequestId = visitCodeFacade.getVisitRequestId(code);

        return new VerificationResult(true, visitRequestId, "Verification successful.");
    }
    
    private record VerificationResult(boolean success, Long visitRequestId, String message) {}
}
