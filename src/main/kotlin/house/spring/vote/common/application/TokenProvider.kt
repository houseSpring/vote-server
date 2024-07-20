package house.spring.vote.common.application

import house.spring.vote.common.domain.CurrentUser

interface TokenProvider {
    fun generateToken(currnetUser: CurrentUser): String
    fun validateToken(token: String): CurrentUser
}