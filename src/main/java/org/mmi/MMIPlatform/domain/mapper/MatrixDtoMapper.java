package org.mmi.MMIPlatform.domain.mapper;

import org.mmi.MMIPlatform.application.dto.MatrixDto;
import org.mmi.MMIPlatform.domain.models.Matrix;
import org.springframework.stereotype.Service;

@Service
public class MatrixDtoMapper {

    public MatrixDto matrixToMatrixDto(Matrix matrix){
        return MatrixDto.builder()
                .semester(matrix.getSemester())
                .promo(matrix.getPromo())
                .build();
    }

    public Matrix userDtoToUser(MatrixDto matrixDto){
        return Matrix.builder()
                .semester(matrixDto.getSemester())
                .promo(matrixDto.getPromo())
                .build();
    }

}
