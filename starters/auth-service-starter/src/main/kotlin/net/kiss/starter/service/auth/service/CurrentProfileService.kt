package net.kiss.starter.service.auth.service

import net.kiss.starter.service.auth.model.UserProfile

interface CurrentProfileService {
  fun getCurrentProfile(username: String): UserProfile
}
