package com.niit.service.impl;

import com.niit.entity.Student;
import com.niit.entity.User;
import com.niit.repository.StudentRepository;
import com.niit.repository.UserRepository;
import com.niit.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Student getProfile(Integer userId) {
        return studentRepository.findByUserId(userId);
    }

    @Override
    public Student updateProfile(Integer userId, String realName, String gender, String idCard, String province, String city, String grade, String needs) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setRealName(realName);
        user.setGender(User.Gender.valueOf(gender));
        user.setIdCard(idCard);
        user.setProvince(province);
        user.setCity(city);
        userRepository.save(user);
        Student student = studentRepository.findByUserId(userId);
        if (student == null) {
            student = new Student();
            student.setUser(user);
        }
        student.setGrade(grade);
        student.setNeeds(needs);
        return studentRepository.save(student);
    }
} 