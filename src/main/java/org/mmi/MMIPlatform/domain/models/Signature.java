package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Signature {
    private String id;
    private byte @NonNull [] sign;
    @NonNull
    private UserStudent userStudent;
}
