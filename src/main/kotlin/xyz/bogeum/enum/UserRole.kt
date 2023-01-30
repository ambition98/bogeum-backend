package xyz.bogeum.enum

enum class UserRole(val key: String, val desc: String) {
    GUEST("ANONYMOUS", "비로그인 사용자"),
    USER("USER", "로그인 사용자"),
    ADMIN("ADMIN", "관리자")
}