//package xyz.bogeum.web.service
//
//import org.springframework.stereotype.Service
//import xyz.bogeum.web.entity.LoginPlatformEntity
//import xyz.bogeum.web.model.LoginPlatform
//import xyz.bogeum.web.repository.LoginPlatformRepo
//
//@Service
//class LoginPlatformServ(
//    private val loginPlatformRepo: LoginPlatformRepo
//) {
//    fun getAll(): List<LoginPlatform> {
//        val modelList = ArrayList<LoginPlatform>()
//        val entityList = loginPlatformRepo.findAll()
//        entityList.forEach { modelList.add(convert(it)) }
//        return modelList
//    }
//
//    private fun convert(entity: LoginPlatformEntity) = LoginPlatform(entity.id, entity.name)
//}