package com.example.scheduler_jpa.service;

import com.example.scheduler_jpa.dto.LoginRequestDto;
import com.example.scheduler_jpa.dto.UserRequestDto;
import com.example.scheduler_jpa.entity.User;
import com.example.scheduler_jpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User newUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User registerUser(UserRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());  // ✅ 비밀번호 저장 (암호화는 나중에 추가)

        return userRepository.save(user);
    }

    public void login(LoginRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByEmail(requestDto.getEmail());

        if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        User user = optionalUser.get();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);  // ✅ 세션에 유저 저장
    }

}

