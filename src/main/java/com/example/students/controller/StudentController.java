package com.example.students.controller;

import com.example.students.model.Student;
import com.example.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
//        return studentRepository.findById(id)
//                .map(student -> {
//                    studentRepository.delete(student);
//                    return ResponseEntity.ok().build();
//                }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        }
    }

    //    @PostMapping ("/{id}")
//    public Student changeStudent(@RequestBody @Valid Student student) {
//        return studentRepository.save(student);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id,
                                              @RequestBody @Valid Student student) {
        return studentRepository.findById(id).map(studentFromDB -> {
            studentFromDB.setFirstName(student.getFirstName());
            studentFromDB.setLastName(student.getLastName());
            studentFromDB.setEmail(student.getEmail());
            studentRepository.save(studentFromDB);
            return ResponseEntity.ok().body(studentFromDB);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id,
                                                @RequestBody Student student) {
        return studentRepository.findById(id)
                .map(studentFromDB -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDB.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDB.setLastName(student.getLastName());
                    }
                    if (!StringUtils.isEmpty(student.getEmail())) {
                        studentFromDB.setEmail(student.getEmail());
                    }
                    studentRepository.save(studentFromDB);
                    return ResponseEntity.ok().body(studentFromDB);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}