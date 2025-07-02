package com.niit.userservice.controller;

import com.niit.common.entity.User;
import com.niit.common.util.Result;
import com.niit.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.niit.api.feign.AppointmentFeignClient;
import com.niit.api.feign.StudentFeignClient;
import com.niit.api.feign.OrderFeignClient;
import com.niit.common.entity.Appointment;
import com.niit.common.entity.Student;
import com.niit.common.entity.Order;
import com.niit.api.feign.TeacherFeignClient;
import com.niit.common.entity.Teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentFeignClient appointmentFeignClient;
    @Autowired
    private StudentFeignClient studentFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Autowired
    private TeacherFeignClient teacherFeignClient;

    // 获取所有用户
    @GetMapping("/list")
    public Result<List<User>> listUsers() {
        return Result.success(userService.getAllUsers());
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }

    // 添加用户
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody User user) {
        Map<String, String> errors = validateUser(user, true);
        if (!errors.isEmpty()) {
            return Result.error(400, "数据验证失败");
        }
        User savedUser = userService.saveUser(user);
        return Result.success("用户添加成功", savedUser);
    }

    // 更新用户
    @PutMapping("/{id}")
    public Result<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        Map<String, String> errors = validateUser(user, false);
        if (!errors.isEmpty()) {
            return Result.error(400, "数据验证失败");
        }
        User savedUser = userService.saveUser(user);
        return Result.success("用户更新成功", savedUser);
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success("用户删除成功");
    }

    // 检查用户名是否存在
    @GetMapping("/check-username")
    public Result<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.isUsernameExists(username);
        return Result.success(exists);
    }

    // 检查手机号是否存在
    @GetMapping("/check-phone")
    public Result<Boolean> checkPhone(@RequestParam String phone) {
        boolean exists = userService.isPhoneExists(phone);
        return Result.success(exists);
    }

    // 验证用户数据
    private Map<String, String> validateUser(User user, boolean isNew) {
        Map<String, String> errors = new HashMap<>();

        if (isNew) {
            // 验证用户名是否已存在
            if (userService.isUsernameExists(user.getUsername())) {
                errors.put("username", "用户名已存在");
            }

            // 验证手机号是否已存在
            if (userService.isPhoneExists(user.getPhone())) {
                errors.put("phone", "手机号已存在");
            }
        }

        // 验证真实姓名
        if (!isValidChineseName(user.getRealName())) {
            errors.put("realName", "请输入有效的中文姓名");
        }

        // 验证身份证号
        if (user.getIdCard() != null && !user.getIdCard().isEmpty() &&
                !isValidIdCard(user.getIdCard())) {
            errors.put("idCard", "请输入有效的身份证号码");
        }

        // 验证手机号格式
        if (!isValidPhone(user.getPhone())) {
            errors.put("phone", "请输入有效的手机号码");
        }

        return errors;
    }

    private boolean isValidChineseName(String name) {
        return name.matches("^[\\u4e00-\\u9fa5]{2,10}$");
    }

    private boolean isValidIdCard(String idCard) {
        // 简单的身份证号验证，可根据需求完善
        return idCard.matches("^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^1[3-9]\\d{9}$");
    }

    @GetMapping("/teacher/center")
    public String teacherCenter(Model model) {
        // TODO: 获取当前登录老师ID，示例用1
        Integer teacherId = 1;
        // 获取预约数据
        List<Appointment> appointments = new java.util.ArrayList<>();
        try {
            appointments = appointmentFeignClient.getAppointmentsByTeacherId(teacherId).getData();
        } catch (Exception e) {
            // 打印日志，页面显示空列表
            System.err.println("远程调用预约服务失败: " + e.getMessage());
        }
        // 获取所有学生
        List<Student> students = new java.util.ArrayList<>();
        try {
            students = studentFeignClient.getAllStudents().getData();
        } catch (Exception e) {
            System.err.println("远程调用学生服务失败: " + e.getMessage());
        }
        // 获取老师所有订单
        List<Order> orders = new java.util.ArrayList<>();
        try {
            orders = orderFeignClient.getOrdersByTeacherId(teacherId).getData();
        } catch (Exception e) {
            System.err.println("远程调用订单服务失败: " + e.getMessage());
        }
        // 组装studentMap
        Map<Integer, Student> studentMap = new HashMap<>();
        for (Student s : students) {
            studentMap.put(s.getId(), s);
        }
        // 组装paidOrderMap（预约ID->是否已支付）
        Map<Integer, Boolean> paidOrderMap = new HashMap<>();
        for (Order o : orders) {
            if (o.getAppointmentId() != null && o.getOrderStatus() != null && o.getOrderStatus().toString().equals("PAID")) {
                paidOrderMap.put(o.getAppointmentId(), true);
            }
        }
        model.addAttribute("appointments", appointments);
        model.addAttribute("studentMap", studentMap);
        model.addAttribute("paidOrderMap", paidOrderMap);
        return "teacher_center";
    }

    @GetMapping("/select")
    public String select(
        @RequestParam(required = false) String gradeLevel,
        @RequestParam(required = false) String subject,
        @RequestParam(required = false) String province,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        Model model) {
        List<Teacher> teachers = null;
        try {
            teachers = teacherFeignClient.searchTeachers(
                gradeLevel, subject, province, city, minPrice, maxPrice
            ).getData();
        } catch (Exception e) {
            teachers = new java.util.ArrayList<>();
        }
        if (teachers == null) teachers = new java.util.ArrayList<>();
        model.addAttribute("teachers", teachers);
        model.addAttribute("gradeLevel", gradeLevel);
        model.addAttribute("subject", subject);
        model.addAttribute("province", province);
        model.addAttribute("city", city);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "select";
    }

    @GetMapping("/student/center")
    public String studentCenter(Model model) {
        // TODO: 获取当前登录学生ID，示例用1
        Integer studentId = 1;
        // 获取预约数据
        List<Appointment> appointments = new java.util.ArrayList<>();
        try {
            appointments = appointmentFeignClient.getAppointmentsByStudentId(studentId).getData();
        } catch (Exception e) {
            System.err.println("远程调用预约服务失败: " + e.getMessage());
        }
        // 获取所有老师
        List<Teacher> teachers = new java.util.ArrayList<>();
        try {
            teachers = teacherFeignClient.getAllTeachers().getData();
        } catch (Exception e) {
            System.err.println("远程调用老师服务失败: " + e.getMessage());
        }
        // 获取学生所有订单
        List<Order> orders = new java.util.ArrayList<>();
        try {
            orders = orderFeignClient.getOrdersByStudentId(studentId).getData();
        } catch (Exception e) {
            System.err.println("远程调用订单服务失败: " + e.getMessage());
        }
        // 组装teacherMap
        Map<Integer, Teacher> teacherMap = new HashMap<>();
        for (Teacher t : teachers) {
            teacherMap.put(t.getId(), t);
        }
        // 组装paidOrderMap（预约ID->是否已支付）
        Map<Integer, Boolean> paidOrderMap = new HashMap<>();
        for (Order o : orders) {
            if (o.getAppointmentId() != null && o.getOrderStatus() != null && o.getOrderStatus().toString().equals("PAID")) {
                paidOrderMap.put(o.getAppointmentId(), true);
            }
        }
        model.addAttribute("appointments", appointments);
        model.addAttribute("teacherMap", teacherMap);
        model.addAttribute("paidOrderMap", paidOrderMap);
        return "student_center";
    }

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {}

    @RequestMapping(value = "/appointments/book", method = {RequestMethod.GET, RequestMethod.POST})
    public String appointmentsBook(
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam(value = "teacherId", required = false) Integer teacherId,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "startTime", required = false) String startTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr,
            @RequestParam(value = "status", required = false) String status,
            Model model,
            javax.servlet.http.HttpServletRequest request
    ) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            com.niit.common.entity.Appointment appointment = new com.niit.common.entity.Appointment();
            // 强制从session获取当前登录学生ID
            javax.servlet.http.HttpSession session = request.getSession();
            Integer sessionStudentId = (Integer) session.getAttribute("userId");
            if (sessionStudentId != null) {
                appointment.setStudentId(sessionStudentId);
            } else {
                appointment.setStudentId(studentId); // 兼容老逻辑
            }
            // teacherId判空校验
            if (teacherId == null) {
                model.addAttribute("error", "请选择老师后再预约！");
                return "select";
            }
            appointment.setTeacherId(teacherId);
            appointment.setSubject(subject);
            // 省略时间和status字段，简化预约
            try {
                appointmentFeignClient.createAppointment(appointment);
            } catch (Exception e) {
                System.err.println("保存预约失败: " + e.getMessage());
            }
            // 跳转到我的预约页面
            return "redirect:/appointments/student";
        }
        // GET请求，渲染页面
        return "select";
    }

    @GetMapping("/appointments/student")
    public String appointmentsStudent(Model model, javax.servlet.http.HttpSession session) {
        Integer studentId = 1; // 实际应从session获取
        java.util.List<Appointment> appointments = new java.util.ArrayList<>();
        try {
            appointments = appointmentFeignClient.getAppointmentsByStudentId(studentId).getData();
        } catch (Exception e) {
            System.err.println("远程调用预约服务失败: " + e.getMessage());
        }
        model.addAttribute("appointments", appointments);
        return "appointments_student";
    }

    @GetMapping("/orders/my")
    public String myOrders(Model model, javax.servlet.http.HttpSession session) {
        // TODO: 获取当前登录学生ID，示例用1
        Integer studentId = 1;
        java.util.List<Order> orders = new java.util.ArrayList<>();
        try {
            orders = orderFeignClient.getOrdersByStudentId(studentId).getData();
        } catch (Exception e) {
            System.err.println("远程调用订单服务失败: " + e.getMessage());
        }
        model.addAttribute("orders", orders);
        return "my_orders";
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
        model.addAttribute("orders", new java.util.ArrayList<>());
        model.addAttribute("courseDates", new java.util.ArrayList<>());
        return "schedule";
    }

    @RequestMapping(value = "/student/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public String studentProfile(Model model) {
        model.addAttribute("user", new com.niit.common.entity.User());
        return "student_profile";
    }

    @GetMapping("/teacher/profile")
    public String teacherProfile() {
        return "teacher_profile";
    }
} 