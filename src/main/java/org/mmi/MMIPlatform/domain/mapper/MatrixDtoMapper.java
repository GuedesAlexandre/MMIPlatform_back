package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.MatrixDto;
import org.mmi.MMIPlatform.domain.models.Matrix;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatrixDtoMapper {
    private final UserDtoMapper userDtoMapper;
    private final StudentDtoMapper studentDtoMapper;
    private final UEDtoMapper ueDtoMapper;

    public MatrixDto matrixToMatrixDto(Matrix matrix){
        return MatrixDto.builder()
                .semester(matrix.getSemester())
                .promo(matrix.getPromo())
                .students(this.studentDtoMapper.studentListToStudentDtoList(matrix.getStudents()))
                .ueDtoList(this.ueDtoMapper.ueListToUeDtoList(matrix.getUeList()))
                .user(this.userDtoMapper.userToUserDto(matrix.getUser()))
                .build();
    }

    public List<MatrixDto> matrixListToMatrixDtoList(List<Matrix> matrixList){
        return matrixList.stream().map(this::matrixToMatrixDto).toList();
    }

    public Matrix matrixDtoToMatrix(MatrixDto matrixDto){
        return Matrix.builder()
                .semester(matrixDto.getSemester())
                .promo(matrixDto.getPromo())
                .students(this.studentDtoMapper.studentDtoListToStudent(matrixDto.getStudents()))
                .ueList(this.ueDtoMapper.ueDtoListToUeList(matrixDto.getUeDtoList()))
                .user(this.userDtoMapper.userDtoToUser(matrixDto.getUser()))
                .build();
    }

    public List<Matrix>  matrixDtoListToMatrixList(List<MatrixDto> matrixDtoList){
        return matrixDtoList.stream().map(this::matrixDtoToMatrix).toList();
    }
}
