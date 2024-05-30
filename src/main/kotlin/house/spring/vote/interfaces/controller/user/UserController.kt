package house.spring.vote.interfaces.controller.user

import house.spring.vote.application.user.dto.command.JoinCommand
import house.spring.vote.application.user.dto.command.LoginCommand
import house.spring.vote.application.user.service.UserReadService
import house.spring.vote.application.user.service.UserWriteService
import house.spring.vote.interfaces.controller.user.request.LoginRequestDto
import house.spring.vote.interfaces.controller.user.request.RegisterUserRequestDto
import house.spring.vote.interfaces.controller.user.response.GenerateTokenResponseDto
import house.spring.vote.interfaces.controller.user.response.GetUserInfoResponseDto
import house.spring.vote.util.annotation.SecureEndPoint
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "유저 API")
@RestController
class UserController(private val userWriteService: UserWriteService, private val userReadService: UserReadService) {
// TODO: 전략패턴 구현
    @PostMapping("/user")
    fun createUser(@RequestBody user: RegisterUserRequestDto): GenerateTokenResponseDto {
        // TODO: Implement createUser
//        val command = JoinCommand(user.nickname, user.deviceId)
//        val userId = userWriteService.join(command)

    }

    @PostMapping("/login")
    fun login(@RequestBody user: LoginRequestDto): GenerateTokenResponseDto {
        // TODO: Implement login
//        val command = LoginCommand(user.deviceId)
//        return userWriteService.login(command)
    }

    @SecureEndPoint
    @GetMapping("/users")
    fun getUserInfo(@AuthenticationPrincipal userDetails: UserDetails): GetUserInfoResponseDto {
        return userReadService.getUserInfoById(userDetails.username.toLong())
    }
}