package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Matrix;
import org.mmi.MMIPlatform.infrastructure.dao.MatrixDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatrixDaoMapper {

    private final StudentDaoMapper studentDaoMapper;
    private final UEDaoMapper ueDaoMapper;

    public Matrix matrixDaoToMatrix(MatrixDao matrixDao) {
        return Matrix.builder()
                .id(matrixDao.getId())
                .semester(matrixDao.getSemester())
                .promo(matrixDao.getPromo().toString())
                .students(this.studentDaoMapper.studentsDaostoStudents(matrixDao.getStudentDaos()))
                .ueList(this.ueDaoMapper.UeDaosListToUeList(matrixDao.getUeDaos()))
                .build();
    }

    public List<Matrix> matrixDaoListToMatrixList(List<MatrixDao> matrixDaoList){
        return matrixDaoList.stream().map(this::matrixDaoToMatrix).toList();
    }

    public MatrixDao matrixToMatrixDao(Matrix matrix){
        return MatrixDao.builder()
                .id(matrix.getId())
                .semester(matrix.getSemester())
                .promo(PromoEnum.valueOf(matrix.getPromo()))
                .studentDaos(this.studentDaoMapper.studentListToStudentDaoList(matrix.getStudents()))
                .ueDaos(this.ueDaoMapper.UeListtoUeDaoList(matrix.getUeList()))
                .build();
    }

    public List<MatrixDao> matrixListToMatrixDaoList(List<Matrix> matrixList){
        return  matrixList.stream().map(this::matrixToMatrixDao).toList();
    }


}
