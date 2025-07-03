package com.niit.service;

import com.niit.entity.Student;

public interface StudentProfileService {
    Student getProfile(Integer userId);
    Student updateProfile(Integer userId, String realName, String gender, String idCard, String province, String city, String grade, String needs);
} 