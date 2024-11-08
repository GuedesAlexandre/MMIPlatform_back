package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Matrix;
import org.mmi.MMIPlatform.infrastructure.dao.MatrixDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PermissionsEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatrixDaoMapper {

    public Matrix matrixDaoToMatrix(MatrixDao matrixDao) {
        return Matrix.builder()
                .id(matrixDao.getId())
                .semester(matrixDao.getSemester())
                .promo(matrixDao.getPromo().toString())
                .build();
    }

    public MatrixDao matrixToMatrixDao(Matrix matrix){
        return MatrixDao.builder()
                .id(matrix.getId())
                .semester(matrix.getSemester())
                .promo(PromoEnum.valueOf(matrix.getPromo()))
                .build();
    }


}
