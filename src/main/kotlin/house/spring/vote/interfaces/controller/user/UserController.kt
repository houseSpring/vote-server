package house.spring.vote.interfaces.controller.user

import house.spring.vote.application.user.dto.command.AuthenticationCommand
import house.spring.vote.application.user.dto.command.DeviceJoinCommand
import house.spring.vote.application.user.service.AuthenticationService
import house.spring.vote.application.user.service.UserReadService
import house.spring.vote.application.user.service.UserWriteService
import house.spring.vote.domain.post.service.TokenProvider
import house.spring.vote.interfaces.controller.user.request.LoginRequestDto
import house.spring.vote.interfaces.controller.user.request.RegisterUserRequestDto
import house.spring.vote.interfaces.controller.user.response.GenerateTokenResponseDto
import house.spring.vote.interfaces.controller.user.response.GetUserInfoResponseDto
import house.spring.vote.util.annotation.SecureEndPoint
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
    private val tokenProvider: TokenProvider
) {
    @PostMapping("/device-users")
    fun createUser(@RequestBody dto: RegisterUserRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        userWriteService.join(DeviceJoinCommand(dto.nickname, dto.deviceId))
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
        return userReadService.getUserInfoById(userDetails.username.toLong())
    }
}