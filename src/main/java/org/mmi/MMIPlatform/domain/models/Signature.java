package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.mmi.MMIPlatform.infrastructure.dao.enums.JustificationEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.SignatureStatusEnum;

@Builder
@Getter
@Setter
public class Signature {
    private String id;
    @NonNull
    private SignatureStatusEnum sign;
    @NonNull
    private Student student;
    @NonNull
    private JustificationEnum justification;
}
