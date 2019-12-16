package net.kiss.demo.auth.service

import net.kiss.demo.auth.model.UserProfile

interface CurrentProfileService {
  fun getCurrentProfile(username: String): UserProfile
}
