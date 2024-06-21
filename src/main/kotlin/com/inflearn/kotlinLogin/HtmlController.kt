package com.inflearn.kotlinLogin

import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.MessageDigest

@Controller
class HtmlController {

    @Autowired
    lateinit var memberRepository:MemberRepository

    @GetMapping("/")
    fun index(model: Model):String{
        model.addAttribute("title", "HOME")
        return "index"
    }

    fun crypto(pw:String):String{
        val sha=MessageDigest.getInstance("SHA-256") //java security
        val hexa=sha.digest(pw.toByteArray())
        val crypto_str=hexa.fold("",{str, it->str+"%02x".format(it)})
        return crypto_str
    }

//    @GetMapping("/sign")
//    fun htmlSignForm(model:Model):String{
//        return "sign"
//    }
//
//    @GetMapping("/login")
//    fun htmlLoginForm(model:Model):String{
//        return "login"
//    }

    @GetMapping("/{formType}")
    fun htmlLoginForm(model:Model, @PathVariable formType:String):String {
        var response:String=""

        if(formType.equals("sign")) response = "sign" else if(formType.equals("login")) response = "login"
        model.addAttribute("title", response)

        return response
    }

    @PostMapping("/sign")
    fun postSign(model: Model,
                 @RequestParam(value="id") userId:String,
                 @RequestParam(value="password") password:String): String{
        try {
            var crptoPw = crypto(password)
            val user=memberRepository.save(Member(userId,crptoPw))
            println(user.toString())
        } catch (e:Exception){
            e.printStackTrace()
        }
        model.addAttribute("title", "HOME")
        return "index"
    }

    @PostMapping("/login")
    fun login(model: Model,
              session:HttpSession,
              @RequestParam(value="userId")userId:String,
              @RequestParam(value="password") password:String):String{
        var pagename:String=""
        try {
            val cryptoPass=crypto(password)
            val db_user=memberRepository.findByUserId(userId)

            if(userId!=null){
                val db_pw = db_user.password
                if(cryptoPass.equals(db_pw)){
                    session.setAttribute("userId", db_user.userId)
                    model.addAttribute("title", "WELCOM")
                    model.addAttribute("userId",userId)
                    pagename =  "welcome"
                } else{
                    model.addAttribute("title", "login")
                    pagename = "login"
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return pagename
    }

    @GetMapping("/post/{num}")
    fun post(model:Model, @PathVariable num:Int) {
        println("num:\t${num}")
    }
}