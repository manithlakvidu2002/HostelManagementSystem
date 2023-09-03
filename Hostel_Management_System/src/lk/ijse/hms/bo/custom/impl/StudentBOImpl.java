

package lk.ijse.hms.bo.custom.impl;

import lk.ijse.hms.bo.custom.StudentBO;
import lk.ijse.hms.dao.FactoryDAO;
import lk.ijse.hms.dao.custom.StudentDAO;
import lk.ijse.hms.dto.StudentDTO;
import lk.ijse.hms.entity.Student;

import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.STUDENT);

    public Boolean addStudent(StudentDTO studentDTO) {
        Student student = new Student(
                studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getContact_no(),
                studentDTO.getDob(),
                studentDTO.getGender());

        return studentDAO.add(student);
    }

    public Boolean deleteStudent(StudentDTO studentDTO) {
        return studentDAO.delete(studentDTO.getId());
    }

    public ArrayList<StudentDTO> getStudentData() {
        ArrayList<StudentDTO> StudentDTOs = new ArrayList<>();
        ArrayList<Student> studentData = studentDAO.getData();

        for (Student std : studentData) {
            StudentDTOs.add(new StudentDTO(std.getId(),
                    std.getName(), std.getAddress(),
                    std.getContact_no(),
                    std.getDob(),
                    std.getGender()));
        }
        return StudentDTOs;
    }

    public String getCurrentID() {
        return studentDAO.getCurrentID();
    }

    @Override
    public Boolean updateStudent(StudentDTO studentDTO) {
        Student student = new Student(
                studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getContact_no(),
                studentDTO.getDob(),
                studentDTO.getGender());

        return studentDAO.update(student);
    }
}
