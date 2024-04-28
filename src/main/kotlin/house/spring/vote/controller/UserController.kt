package house.spring.vote.controller

import house.spring.vote.domain.user.dto.GetUserInfoResponseDto
import house.spring.vote.domain.user.dto.RegisterUserRequestDto
import house.spring.vote.domain.user.service.UserReadService
import house.spring.vote.domain.user.service.UserWriteService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userWriteService: UserWriteService, private val userReadService: UserReadService) {
    @PostMapping("/user")
    fun createUser(@RequestBody user: RegisterUserRequestDto): Long {
        return userWriteService.join(user.nickname, user.deviceId)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: RegisterUserRequestDto): String {
        return userWriteService.login(user.deviceId)
    }

    @GetMapping("/users/:id")
    fun getUserInfo(id: Long): GetUserInfoResponseDto {
        return userReadService.getUserInfo(id)
    }
}