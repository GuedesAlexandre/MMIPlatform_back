package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class SignatureSheet {
    private String id;
    @NonNull
    private String promo;
    @NonNull
    private String moduleName;
    @NonNull
    private Date createdAt;
    @NonNull
    private Date finishAt;
    @NonNull
    private List<Signature> signatures;
    @NonNull
    private List<Student> students;
}
