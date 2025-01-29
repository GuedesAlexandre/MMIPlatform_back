package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class SignatureSheet {
    private String id;
    private String promo;
    private String moduleName;
    private Date createdAt;
    private Date finishAt;
    private List<Signature> Signatures;
}
