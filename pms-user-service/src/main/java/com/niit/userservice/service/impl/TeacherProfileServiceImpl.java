package com.niit.userservice.service.impl;

import com.niit.common.entity.Teacher;
import com.niit.userservice.repository.TeacherRepository;
import com.niit.userservice.service.TeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherProfileServiceImpl implements TeacherProfileService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher completeProfile(Integer userId, String avatar, String subject, String education, String introduction, Float rating) {
        Teacher teacher = teacherRepository.findByUser_Id(userId);
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setId(userId);
        }
        teacher.setEducation(education);
        teacher.setSubject(subject);
        teacher.setIntroduction(introduction);
        teacher.setRating(rating != null ? rating.doubleValue() : null);
        // avatar字段在Teacher实体中没有，若需设置头像请在User实体中设置
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher completeProfile(Integer userId, String realName, String gender, String idCard, String province, String city, String avatarUrl, String subject, String education, String introduction) {
        Teacher teacher = teacherRepository.findByUser_Id(userId);
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setId(userId);
        }
        teacher.setEducation(education);
        teacher.setSubject(subject);
        teacher.setIntroduction(introduction);
        // 其它参数如realName、gender、idCard、province、city、avatarUrl应在User实体中设置
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getProfile(Integer userId) {
        return teacherRepository.findByUser_Id(userId);
    }

    @Override
    public Teacher findByUser_Id(Integer userId) {
        return teacherRepository.findByUser_Id(userId);
    }
} 