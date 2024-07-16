package house.spring.vote.user.controller

import house.spring.vote.common.controller.annotation.SecureEndPoint
import house.spring.vote.user.application.command.DeviceJoinCommand
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

@Tag(name = "User", description = "유저 API")
@RestController
class UserController(
    private val userWriteService: UserCommandService,
    private val userQueryService: UserQueryService,
) {
    @PostMapping("/device-users")
    fun createDeviceUser(@RequestBody dto: RegisterUserRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        userWriteService.createDeviceUser(DeviceJoinCommand(dto.nickname, dto.deviceId))
        // todo: generate token
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/auth/device")
    fun login(@RequestBody user: LoginRequestDto): ResponseEntity<GenerateTokenResponseDto> {
        // todo: generate token
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @SecureEndPoint
    @GetMapping("/users")
    fun getUserInfo(): ResponseEntity<GetUserInfoResponseDto> {
        // todo: get user info
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}