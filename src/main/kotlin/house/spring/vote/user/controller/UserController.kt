package house.spring.vote.user.controller

import house.spring.vote.common.controller.annotation.CurrentUser
import house.spring.vote.common.controller.annotation.SecureEndPoint
import house.spring.vote.user.application.command.DeviceJoinCommand
import house.spring.vote.user.application.command.DeviceLoginCommand
import house.spring.vote.user.application.port.`in`.UserCommandService
import house.spring.vote.user.application.port.`in`.UserQueryService
import house.spring.vote.user.controller.request.LoginRequestDto
import house.spring.vote.user.controller.request.RegisterUserRequestDto
import house.spring.vote.user.controller.response.GenerateTokenResponseDto
import house.spring.vote.user.controller.response.GetUserInfoResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import house.spring.vote.common.domain.CurrentUser as LoginUser

@Tag(name = "User", description = "유저 API")
@RestController
class UserController(
    private val userCommandService: UserCommandService,
    private val userQueryService: UserQueryService,
) {
    @PostMapping("/device-users")
    fun createDeviceUser(@RequestBody dto: RegisterUserRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        val token = userCommandService.createDeviceUser(DeviceJoinCommand(dto.nickname, dto.deviceId))
        return ResponseEntity.status(HttpStatus.CREATED).body(GenerateTokenResponseDto(token))
    }

    @PostMapping("/auth/device")
    fun login(@RequestBody dto: LoginRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        val token = userCommandService.loginDeviceUser(DeviceLoginCommand(deviceId = dto.deviceId))
        return ResponseEntity.status(HttpStatus.OK).body(GenerateTokenResponseDto(token))
    }

    @SecureEndPoint
    @GetMapping("/users")
    fun getUserInfo(
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GetUserInfoResponseDto> {
        println("user: $user")
        val response = userQueryService.getUserInfoById(user.id)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}