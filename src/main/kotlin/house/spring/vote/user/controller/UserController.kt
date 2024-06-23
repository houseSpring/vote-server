package house.spring.vote.user.controller

import house.spring.vote.common.controller.annotation.SecureEndPoint
import house.spring.vote.post.application.port.TokenProvider
import house.spring.vote.user.application.command.AuthenticationCommand
import house.spring.vote.user.application.command.DeviceJoinCommand
import house.spring.vote.user.application.service.AuthenticationService
import house.spring.vote.user.application.service.UserReadService
import house.spring.vote.user.application.service.UserWriteService
import house.spring.vote.user.controller.request.LoginRequestDto
import house.spring.vote.user.controller.request.RegisterUserRequestDto
import house.spring.vote.user.controller.response.GenerateTokenResponseDto
import house.spring.vote.user.controller.response.GetUserInfoResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "유저 API")
@RestController
class UserController(
    private val userWriteService: UserWriteService,
    private val userReadService: UserReadService,
    private val authenticationService: AuthenticationService,
    private val tokenProvider: TokenProvider,
) {
    @PostMapping("/device-users")
    fun createDeviceUser(@RequestBody dto: RegisterUserRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        userWriteService.createDeviceUser(DeviceJoinCommand(dto.nickname, dto.deviceId))
        val userDetails = authenticationService.authenticate(AuthenticationCommand(dto.deviceId))
        val token = tokenProvider.generateToken(userDetails)
        return ResponseEntity.status(HttpStatus.CREATED).body(GenerateTokenResponseDto(token))
    }

    @PostMapping("/auth/device")
    fun login(@RequestBody user: LoginRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        val userDetails = authenticationService.authenticate(AuthenticationCommand(user.deviceId))
        val token = tokenProvider.generateToken(userDetails)
        return ResponseEntity.ok(GenerateTokenResponseDto(token))
    }

    @SecureEndPoint
    @GetMapping("/users")
    fun getUserInfo(@AuthenticationPrincipal userDetails: UserDetails): GetUserInfoResponseDto {
        return userReadService.getUserInfoById(userDetails.username)
    }
}