package house.spring.vote.interfaces.controller.user

import house.spring.vote.application.user.dto.command.JoinCommand
import house.spring.vote.application.user.dto.command.LoginCommand
import house.spring.vote.application.user.dto.query.GetUserInfoQuery
import house.spring.vote.application.user.service.UserReadService
import house.spring.vote.application.user.service.UserWriteService
import house.spring.vote.interfaces.controller.user.request.LoginRequestDto
import house.spring.vote.interfaces.controller.user.request.RegisterUserRequestDto
import house.spring.vote.interfaces.controller.user.response.GetUserInfoResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "유저 API")
@RestController
class UserController(private val userWriteService: UserWriteService, private val userReadService: UserReadService) {

    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    @PostMapping("/user")
    fun createUser(@RequestBody user: RegisterUserRequestDto): Long {
        val command = JoinCommand(user.nickname, user.deviceId)
        return userWriteService.join(command)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: LoginRequestDto): String {
        val command = LoginCommand(user.deviceId)
        return userWriteService.login(command)
    }

    @GetMapping("/users/:id")
    fun getUserInfo(id: Long): GetUserInfoResponseDto {
        val query = GetUserInfoQuery(id)
        return userReadService.getUserInfo(query)
    }
}