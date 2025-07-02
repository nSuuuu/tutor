package com.niit.userservice.service;

import com.niit.common.entity.Student;
import com.niit.common.entity.Teacher;
import com.niit.common.entity.User;
import com.niit.common.entity.User.Gender;
import com.niit.common.util.BusinessException;
import com.niit.userservice.repository.StudentRepository;
import com.niit.userservice.repository.TeacherRepository;
import com.niit.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // ... existing code ...

    public User login(String phone, String password) throws BusinessException {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new BusinessException("手机号未注册");
        }
        // 直接比较明文密码
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return user;
    }

    @Transactional
    public User register(User user, Integer roleType, String extraInfo) throws BusinessException {
        // 验证手机号是否已存在
        if (userRepository.countByPhone(user.getPhone()) > 0) {
            throw new BusinessException("手机号已被注册");
        }
        // 验证用户名是否已存在
        if (userRepository.countByUsername(user.getUsername()) > 0) {
            throw new BusinessException("用户名已被使用");
        }
        // 设置默认值
        user.setGender(Gender.男); // 默认性别
        user.setIdCard(null);      // 默认空身份证
        user.setRealName(null);    // 真实姓名默认为空，后续完善
        // 存储明文密码
        user.setPassword(user.getPassword());
        // 插入用户
        user = userRepository.save(user);
        // 根据角色插入额外信息
        if (roleType == 1) { // 老师
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setRating(5.0); // 默认评分5分
            teacherRepository.save(teacher);
        } else if (roleType == 2) { // 学生
            Student student = new Student();
            student.setUser(user);
            student.setGrade(extraInfo); // 使用extraInfo作为年级
            studentRepository.save(student);
        }
        return user;
    }
} 