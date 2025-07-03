package com.niit.service.impl;

import com.niit.entity.Teacher;
import com.niit.repository.TeacherRepository;
import com.niit.service.TeacherProfileService;
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
            teacher.setUserId(userId);
        }
        teacher.setEducation(education);
        teacher.setSubjects(subject);
        teacher.setExperience(introduction);
        teacher.setScore(rating != null ? rating : null);
        // avatar字段在Teacher实体中有
        teacher.setAvatar(avatar);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher completeProfile(Integer userId, String realName, String gender, String idCard, String province, String city, String avatarUrl, String subject, String education, String introduction) {
        Teacher teacher = teacherRepository.findByUser_Id(userId);
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setUserId(userId);
        }
        teacher.setEducation(education);
        teacher.setSubjects(subject);
        teacher.setExperience(introduction);
        teacher.setAvatar(avatarUrl);
        // 其它参数如realName、gender、idCard、province、city应在User实体中设置
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